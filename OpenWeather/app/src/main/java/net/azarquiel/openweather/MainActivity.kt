package net.azarquiel.openweather

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.CustomAdapter
import net.azarquiel.openweather.model.Day
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initRV()
        getData()
    }

    private fun getData() {
        GlobalScope.launch() {
        val jsontxt = URL("http://api.openweathermap.org/data/2.5/forecast?q=Toledo,es&units=metric&APPID=601c9db344b44f9774ef76a4c07979b1&lang=sp").readText(Charsets.UTF_8)
        launch(Dispatchers.Main) {
            val result = Gson().fromJson(jsontxt, Array<Day>::class.java)
            val days = result.toList()
            adapter.setDays(days)
        }
    }
    }

    private fun initRV() {
        val rvDay = findViewById<RecyclerView>(R.id.rvDays)
        adapter = CustomAdapter(this, R.layout.rowweather)
        rvDay.adapter = adapter
        rvDay.layoutManager = LinearLayoutManager(this)
    }
}