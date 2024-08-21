package com.example.retrofitkotlin

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.data.DataAdapter
import com.example.retrofitkotlin.data.DataModel
import com.example.retrofitkotlin.internet.CheckInternet
import com.example.retrofitkotlin.services.ApiService
import com.example.retrofitkotlin.services.ServiceBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.time.Duration

class MainActivity : AppCompatActivity() {
    private lateinit var dataList:ArrayList<DataModel>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.button)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        button.setOnClickListener {

            if(CheckInternet().isOnline(this)){
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Kotlin Progress Bar")
                progressDialog.setMessage("Application is loading, please wait")
                progressDialog.show()

                val apiService: ApiService = ServiceBuilder.buildService(ApiService::class.java)
                val requestCall : Call<JsonObject> = apiService.getData()

                requestCall.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(p0: Call<JsonObject>, p1: Response<JsonObject>) {

                        if (p1.isSuccessful) {
                            progressDialog.dismiss()
                            dataList = arrayListOf<DataModel>()
                            var jsonObj = JSONObject(p1.body().toString())
                            var products : JSONArray = jsonObj.getJSONArray("product")
                            Log.d("api response", products.toString())

                            for (i in 0 until products.length()) {
                                val item = products.getJSONObject(i)
                                val sl : String = item.getInt("sl").toString()
                                val p_code : String = item.getString("P_CODE").toString()
                                val p_desc : String = item.getString("P_DESC").toString()
                                val pack_size : String = item.getString("PACK_SIZE").toString()
                                val comm_tp : String = item.getString("COMM_TP").toString()
                                val comm_vp : String = item.getString("COMM_VP").toString()
                                val comm_mrp : String = item.getString("COMM_MRP").toString()

                                Log.d("product ${sl}: ","$p_code,$p_desc,$pack_size,$comm_tp,$comm_vp,$comm_mrp")
                                dataList.add(DataModel(sl,p_code,p_desc,pack_size,comm_tp,comm_vp,comm_mrp))
                            }
                            recyclerView.adapter = DataAdapter(dataList)
                        }
                    }

                    override fun onFailure(p0: Call<JsonObject>, p1: Throwable) {
                        Log.d("api response", p1.toString())
                    }

                })
            }else{
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            }
        }


/*
        button.setOnClickListener {

            if(CheckInternet().isOnline(this)){
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Kotlin Progress Bar")
                progressDialog.setMessage("Application is loading, please wait")
                progressDialog.show()

                val filter = HashMap<String,String>()
                for(i in 1..50){
                    filter["sl${i}"] = "sl$i"
                    filter["p_code${i}"] = "p_code$i"
                    filter["p_desc${i}"] = "p_desc$i"
                    filter["pack_size${i}"] = "pack_size$i"
                    filter["comm_tp${i}"] = "comm_tp$i"
                    filter["comm_vp${i}"] = "comm_vp$i"
                    filter["comm_mrp${i}"] = "comm_mrp$i"
                }
                filter["no_of_item"]="50"
                Log.d("message",filter.toString())
                val apiService: ApiService = ServiceBuilder.buildService(ApiService::class.java)
                val requestCall : Call<JsonObject> = apiService.getDatas(filter)

                requestCall.enqueue(object : Callback<JsonObject> {
                    override fun onResponse(p0: Call<JsonObject>, p1: Response<JsonObject>) {
                        if (p1.isSuccessful) {
                            progressDialog.dismiss()
                            dataList = arrayListOf<DataModel>()
                            var jsonObj = JSONObject(p1.body().toString())
                            var products : JSONArray = jsonObj.getJSONArray("product")

                            for (i in 0 until products.length()) {
                                val item = products.getJSONObject(i)
                                val sl : String = item.getInt("sl").toString()
                                val p_code : String = item.getString("P_CODE").toString()
                                val p_desc : String = item.getString("P_DESC").toString()
                                val pack_size : String = item.getString("PACK_SIZE").toString()
                                val comm_tp : String = item.getString("COMM_TP").toString()
                                val comm_vp : String = item.getString("COMM_VP").toString()
                                val comm_mrp : String = item.getString("COMM_MRP").toString()

                                Log.d("product ${sl}: ","$p_code,$p_desc,$pack_size,$comm_tp,$comm_vp,$comm_mrp")
                                dataList.add(DataModel(sl,p_code,p_desc,pack_size,comm_tp,comm_vp,comm_mrp))
                            }
                            recyclerView.adapter = DataAdapter(dataList)
                        }
                    }

                    override fun onFailure(p0: Call<JsonObject>, p1: Throwable) {
                        Log.d("api response", p1.toString())
                    }

                })
            }else{
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            }
        }

 */
    }
}