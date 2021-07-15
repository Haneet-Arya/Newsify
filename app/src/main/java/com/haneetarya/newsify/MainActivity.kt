package com.haneetarya.newsify

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsViewClicked {

    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager= LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter=mAdapter





    }
    private fun fetchData(){
        val URL: String = "https://gnews.io/api/v4/top-headlines?country=in&token=88c94e649d978ca79b4fe555c87cc3ed&lang=en"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,


            {


                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val src :JSONObject = newsJsonObject.getJSONObject("source")
                    val news= News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("image"),
                        src.getString("name"),
                        newsJsonObject.getString("url")
                    )
                    newsArray.add(news)

                }
                mAdapter.updateNews(newsArray)

            },
            {
                Toast.makeText(this,"Problem Occured",Toast.LENGTH_LONG).show()

            }



        )

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent= builder.build()
        customTabsIntent.launchUrl(this,Uri.parse(item.link))
    }


}