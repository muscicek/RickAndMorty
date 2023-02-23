package com.example.rickandmorty

import com.example.rickandmorty.CharacterModel.root
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//https://rickandmortyapi.com/api
interface helper {

    @GET("/api/character")
    suspend fun getId(@Query("page")page:Int ): Response<root>

}