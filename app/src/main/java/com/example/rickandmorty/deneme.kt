package com.example.rickandmorty

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.CharacterModel.Result
import com.example.rickandmorty.adapter.adapter
import com.example.rickandmorty.databinding.FragmentDenemeBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


open class deneme : Fragment() {


    private var job: Job? = null
    private lateinit var recyc: adapter
    private lateinit var tempArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    private lateinit var newCharacterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    private lateinit var characterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    private lateinit var binding: FragmentDenemeBinding
    private lateinit var shared: SharedPreferences
    private lateinit var edit: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Message", "create")
        tempArray = ArrayList<Result>()
        shared = this.requireActivity().getSharedPreferences("status", Context.MODE_PRIVATE)
        edit = shared.edit()
        edit.putBoolean("stat", false)
        edit.apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDenemeBinding.inflate(inflater, container, false)

        Log.d("Message", "createview")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.grid.layoutManager = GridLayoutManager(activity, 2)
        Log.d("Message", "viewCreated")

        if (shared.getBoolean("stat", false) == false) {
            newCharacterArray = ArrayList<com.example.rickandmorty.CharacterModel.Result>()
            characterArray = ArrayList<Result>()
            val api = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(helper::class.java)
            //Fetch data
            job = CoroutineScope(Dispatchers.IO).launch {

                //Loop for each page
                for (i in 1..42) {
                    val response = api.getId(i)
                    response.body()?.let {
                        for (j in 0..it.results.size - 1) {
                            characterArray.add(it.results.get(j))

                        }
                    }
                }
                tempArray = characterArray
                edit.putBoolean("stat", true)
                edit.apply()

                withContext(Dispatchers.Main) {
                    recyc = adapter(characterArray)
                    binding.grid.adapter = recyc
                    binding.progressBar.visibility = View.INVISIBLE

                }
            }
        } else {
            recyc = adapter(tempArray)
            binding.grid.adapter = recyc
            binding.progressBar.visibility = View.INVISIBLE

        }
        //search
        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                newCharacterArray.clear()
                for (i in characterArray) {

                    if (i.name.lowercase().contains(s.toString().lowercase())) {
                        newCharacterArray.add(i)
                    }
                }
                if (s.toString() == "") {
                    recyc.getFilter(characterArray)
                }
                if (newCharacterArray.isNotEmpty()) {
                    recyc.getFilter(newCharacterArray)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

    }
}

