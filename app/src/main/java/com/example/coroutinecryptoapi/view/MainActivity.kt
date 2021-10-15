package com.example.coroutinecryptoapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutinecryptoapi.Model.CryptoModel
import com.example.coroutinecryptoapi.R
import com.example.coroutinecryptoapi.adapter.adapter
import com.example.coroutinecryptoapi.service.serviceAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), adapter.Listener {
    private val BASE_URL = "https://api.nomics.com/v1/"
    private var cryptoModels:ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter : adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Working ${Thread.currentThread().name}")
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        GlobalScope.launch(Dispatchers.Default) {
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
                            println(cryptoModel.price)
                        }
                        recyclerViewAdapter = adapter(cryptoModels!!,this@MainActivity)
                        recyclerView.adapter = recyclerViewAdapter
                    }
                }

            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                println("onFail")
            }

        })
    }

    override fun OnItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Clicked : ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

}