package com.example.coroutinecryptoapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.coroutinecryptoapi.Model.CryptoModel
import com.example.coroutinecryptoapi.R
import com.example.coroutinecryptoapi.service.serviceAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels:ArrayList<CryptoModel>? = null
    //private var recyclerViewAdapter : RecyclerViewAdapter? = nul
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Working ${Thread.currentThread().name}")

        GlobalScope.launch {
            println("Working ${Thread.currentThread().name}")
            loadData()
        }

    }

    suspend fun loadData(){
        println("Working ${Thread.currentThread().name}")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val services =retrofit.create(serviceAPI::class.java)
        val call = services.getData()

        call.enqueue(object : Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if(response.isSuccessful){
                    response.body().let {
                        cryptoModels = ArrayList(it)
                        for (cryptoModel:CryptoModel in cryptoModels!!){
                            println(cryptoModel.currency)
                            //println(cryptoModel.price)

                        }
                    }
                }

            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                println("onFail")
            }

        })


    }


}