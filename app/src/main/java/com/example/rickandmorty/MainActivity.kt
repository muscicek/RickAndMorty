package com.example.rickandmorty

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmorty.adapter.adapter
import com.example.rickandmorty.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var job: Job? = null
    private lateinit var recyc: adapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterArray: ArrayList<com.example.rickandmorty.CharacterModel.Result>
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.grid.layoutManager = GridLayoutManager(this, 2)


        val api = Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(helper::class.java)

        characterArray = ArrayList<com.example.rickandmorty.CharacterModel.Result>()


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
}