package diegofercri.dev

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var azar: Int = 0
    private var intentos: Int=0
    private var n: Int=50
    private lateinit var lhbotones: LinearLayout
    private lateinit var tvnumber: TextView
    private lateinit var tvintentos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}