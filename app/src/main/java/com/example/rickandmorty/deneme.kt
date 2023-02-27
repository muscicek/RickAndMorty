package com.example.rickandmorty

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.CharacterModel.Result
import com.example.rickandmorty.adapter.adapter
import com.example.rickandmorty.databinding.FragmentDenemeBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class deneme : Fragment() {


    private var job: Job? = null
    private lateinit var recyc: adapter
    private lateinit var newCharacterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    private lateinit var characterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>

    private lateinit var binding: FragmentDenemeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDenemeBinding.inflate(inflater, container, false)
        binding.grid.layoutManager = GridLayoutManager(activity, 2)
        val api = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(helper::class.java)

        characterArray = ArrayList<Result>()
        newCharacterArray = ArrayList<com.example.rickandmorty.CharacterModel.Result>()

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

            withContext(Dispatchers.Main) {
                recyc = adapter(characterArray)
                binding.grid.adapter = recyc
                binding.progressBar.visibility = View.INVISIBLE

            }
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

