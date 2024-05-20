package com.example.mylistview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var sa: JSONArray
    private val tab_id = ArrayList<Int>()
    private val tab_name = ArrayList<String>()
    private val tab_price = ArrayList<Double>()
    private val tab_img = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val url = "https://run.mocky.io/v3/669cdb43-71a7-46ef-b8d9-1bc2619c1c58"
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val joRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                sa = response
                try {
                    for (i in 0 until sa.length()) {
                        tab_id.add(sa.getJSONObject(i).getInt("id"))
                        tab_name.add(sa.getJSONObject(i).getString("name"))
                        tab_price.add(sa.getJSONObject(i).getDouble("price"))
                        tab_img.add(sa.getJSONObject(i).getString("image"))
                    }
                    val lv = findViewById<ListView>(R.id.lv1)
                    lv.adapter = Ressources_cli(tab_id,tab_name,tab_price,tab_img)
                    lv.setOnItemClickListener { adapterView, view, i, l ->
                        val tv = findViewById<TextView>(R.id.tv)
                        val selectedName = tab_name[i]
                        val selectedPrice = tab_price[i]
                        //Toast.makeText(this, "You selected: $selectedName - $selectedPrice MAD", Toast.LENGTH_SHORT).show()
                        tv.setText("You selected: $selectedName - $selectedPrice MAD")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
            }
        )
        requestQueue.add(joRequest)
        //JSONArray data = ;
        }
    }

class Ressources_cli(private val tab_id: ArrayList<Int>,
                     private val tab_name: ArrayList<String>,
                     private val tab_price: ArrayList<Double>,
                     private val tab_img: ArrayList<String>) : BaseAdapter() {


    override fun getCount(): Int {
        return tab_img.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    //
    
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        val v: View
        v = LayoutInflater.from(viewGroup!!.context).inflate(R.layout.row, null)
        // row est représenté par v.Afin d'accéder à tout les composants de row .
        val t1 = v.findViewById<TextView>(R.id.nomFilm)
        val t2 = v.findViewById<TextView>(R.id.priceFilm)
        //Affectation
        t1.text = tab_name[i].toString()
        t2.text = "${tab_price[i]} MAD"

        return v
        }


    

    }

