package net.azarquiel.nbateams

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
import net.azarquiel.nbateams.adapter.CustomAdapter
import net.azarquiel.nbateams.model.Team
import java.net.URL
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

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
            val jsontxt = URL("http://www.ies-azarquiel.es/paco/apinba/teams").readText(Charsets.UTF_8)
            launch(Dispatchers.Main) {
                val result = Gson().fromJson(jsontxt, Array<Team>::class.java)
                val teams = result.toList()
                adapter.setTeams(teams)
            }
        }
    }

    private fun initRV() {
        val rvTeam = findViewById<RecyclerView>(R.id.rvTeams)
        adapter = CustomAdapter(this, R.layout.rowteam)
        rvTeam.adapter = adapter
        rvTeam.layoutManager = LinearLayoutManager(this)
    }

}