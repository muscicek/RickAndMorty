package com.example.rickandmorty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.CharacterModel.Result
import com.example.rickandmorty.adapter.adapter
import com.example.rickandmorty.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var job: Job? = null
    private lateinit var recyc: adapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var newCharacterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    private lateinit var characterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.grid.layoutManager = GridLayoutManager(this, 2)
        val api = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(helper::class.java)

        characterArray = ArrayList<Result>()


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


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        newCharacterArray = ArrayList<com.example.rickandmorty.CharacterModel.Result>()

        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newCharacterArray.clear()
                if (newText != null) {
                    for (i in characterArray) {

                        if (i.name.lowercase().contains(newText.lowercase())) {
                            newCharacterArray.add(i)
                        }
                    }
                }
                if (newText == "") {
                    recyc.getFilter(characterArray)
                }
                if (newCharacterArray.isNotEmpty()) {
                    recyc.getFilter(newCharacterArray)
                }
                return false

            }


        })
        return true
    }


}