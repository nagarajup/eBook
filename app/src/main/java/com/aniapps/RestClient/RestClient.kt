package com.aniapps.utils.RestClient

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.aniapps.ebook.EBookApp
import com.aniapps.ebook.HomeActivity
import com.aniapps.ebook.R
import com.aniapps.utils.Models.Stories
import com.aniapps.utils.Progress
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient :HomeActivity(){
    private var retrofit: Retrofit? = null
    private var apiService: ApiService? = null
    private var context: Context? = null
    internal var loading_progress: Progress? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        private val mInstance: RestClient = RestClient()

        @Synchronized
        fun getIn(): RestClient {
            return mInstance
        }
    }


    private fun getClient(): Retrofit? {
        if (retrofit == null) {
            val okHttpClient: OkHttpClient? = OkHttpClient.Builder()
                    .readTimeout(90, TimeUnit.SECONDS)
                    .connectTimeout(90, TimeUnit.SECONDS)
                    .build()
            retrofit = Retrofit.Builder()
                    .baseUrl(EBookApp.instance.resources.getString(R.string.api_live))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient!!)
                    .build()
        }
        return retrofit
    }



    fun getResults(api_res: ApiResults) {
        val apiService: ApiService
      //  loading_progress = Progress(EBookApp.instance)
        //loading_progress!!.show()
        apiService = getClient()!!.create(ApiService::class.java)
        apiService.getApiResult().enqueue(object : Callback<Stories> {
            override fun onResponse(call: Call<Stories>, res: Response<Stories>) {
                if (res.isSuccessful) {
                  /* try {
                       runOnUiThread(Runnable { loading_progress!!.dismiss() })
                   } catch (e: Exception) {
                       e.printStackTrace()
                   }*/
                    try {
                        if (null != res.body()) {
                            api_res.onSuccess(res.body())
                            Log.e("###", "NoCryptRes" + res.body()!!)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    api_res.onFailure("Error Code" + String.format(res.code().toString()))
                }
            }
            override fun onFailure(call: Call<Stories>, t: Throwable) {
                /* try {
                      runOnUiThread(Runnable { CTEProgress.getInstance().dismiss(context as Activity) })
                  } catch (e: Exception) {
                      e.printStackTrace()
                  }*/
                retrofit = null
            }
        })
    }

    /*  val apiService: ApiService
          apiService = getClient()!!.create(ApiService::class.java)
          val call = apiService.getStories();
          call.enqueue(object : Callback<List<StoriesItem>> {
              override fun onResponse(call: Call<List<StoriesItem>>?, response: Response<List<StoriesItem>>?) {
                  if (response!!.isSuccessful) {
                      // stories=response.body();
                      *//* mystories = response.body();
                     val items = arrayOfNulls<String>(mystories!!.size)
                     for (i in mystories!!.indices) {
                         items[i] = mystories!![i].title;
                     }
                     rvStories.adapter = StroiesAdapter(mystories, this@MainActivity)*//*
                    Log.e("Response", "RES" + response.body())
                    // Log.e("Items", "###" + items)
                } else {
                    Log.e("Error Code", String.format(response.code().toString()));
                    Log.e("Error Body", response.errorBody().toString());
                }

            }


            override fun onFailure(call: Call<List<StoriesItem>>?, t: Throwable?) {
            }


        })*/
}