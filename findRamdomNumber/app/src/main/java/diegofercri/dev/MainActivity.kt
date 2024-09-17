package diegofercri.dev

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var azar: Int = 0
    private var intentos: Int=0
    private var n: Int=50
    private lateinit var lhbuttons: LinearLayout
    private lateinit var tvnumber: TextView
    private lateinit var tvtries: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Generar número aleatorio entre 1 y 100 (ambos incluidos)
        val random = Random(System.currentTimeMillis())
        azar = (1..100).random(random)

        // Mostrar el número aleatorio en el log
        Log.d("ramdom number: ", azar.toString())

        // Obtener referencias a los elementos de la interfaz de usuario
        tvtries = findViewById<TextView>(R.id.tvtries)
        tvnumber = findViewById<TextView>(R.id.tvnumber)
        lhbuttons = findViewById<LinearLayout>(R.id.lhbuttons)

        // Recorremos el LinearLayout padre de los botones y establecemos un OnClickListener para cada uno de ellos
        for (i in 0 until lhbuttons.childCount) {
            var button = lhbuttons.getChildAt(i) as Button
            // Cuando se hace clic en el botón, se llama al método onClickBoton mediante una expresión lambda
            button.setOnClickListener { v-> onClickBoton(v) }
        }
    }

    private fun onClickBoton(v: View?) {
        // Guardamos el boton presionado en una variable
        val presedButton = v as Button
        // Obtenemos el número del botón presionado
        val i = (presedButton.tag as String).toInt()

        // Actualizamos el número de intentos
        intentos++
        tvtries.text = "Intentos: ${intentos}"

        // Dependiendo del número del botón presionado, actualizamos el número del usuario
        when (i) {
            0 -> {
                n-=10
            }
            1 -> {
                n-=5
            }
            2 -> {
                n-=1
            }
            3 -> {
                n+=1
            }
            4 -> {
                n+=5
            }
            5 -> {
                n+=10
            }
        }
        // Mostramos el número del usuario en el log
        Log.d("numero del usuario: ", n.toString())
        // Mostramos el número del usuario en la pantalla
        tvnumber.text = "$n"
        // Comprobamos si el número del usuario es igual al número aleatorio
        checkNumber()
    }

    // Función para comprobar y mostrat si el número del usuario es igual al número aleatorio
    private fun checkNumber() {
        if (azar > n) {
            msg("El número es mayor")
        } else if (azar < n) {
            msg("El número es menor")
        } else {
            msg("Has acertado en ${intentos} intentos")
        }
    }

    // Función para mostrar un mensaje en la pantalla
    private fun msg(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}