package com.example.coroutinecryptoapi.service

import com.example.coroutinecryptoapi.Model.CryptoModel
import com.squareup.okhttp.Call
import retrofit2.http.GET

interface serviceAPI {
    //GET,POST,UPDATE,DELETE
    @GET("prices?key=938ff15e014535452724ff64885ce77db54602a3")
    fun getData(): retrofit2.Call<List<CryptoModel>>//Asynronic veri indirme işlemi için tanımlanır.
}