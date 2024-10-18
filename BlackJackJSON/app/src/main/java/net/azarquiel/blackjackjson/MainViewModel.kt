package net.azarquiel.blackjackjson

import android.os.SystemClock
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.blackjackjson.model.Carta
import kotlin.random.Random

class MainViewModel(mainActivity: MainActivity) : ViewModel() {
    private val mainActivity = mainActivity

    private val _cartas = mutableStateListOf<Carta>()
    val cartas: SnapshotStateList<Carta> = _cartas

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo






    //mazo con 40 cartas vacias
    val mazo = Array(40){ Carta() }
    //array de palos
    val palos = arrayOf("clubs", "diamonds", "hearts", "spades")
    val random = Random(System.currentTimeMillis())
    var posmazo = 0
    var jugador = 0
    val puntos = Array(2){0}


    init {
        crearMazo()
        sacaCarta()
        sacaCarta()
    }



    fun crearMazo(){
        var i=0
        for (palo in 0 until 4){
            for (numero in 1 .. 10){
                mazo[i] = Carta(numero,palo)
                i++
            }
        }
        mazo.shuffle(random)
    }
    fun sacaCarta() {
        val carta = mazo[posmazo]
        posmazo++
        _cartas.add(0,carta)

        puntos[jugador] += if (carta.numero >7) 10 else carta.numero
        //_titulo.value = "P1 (${puntos[0]})- P2 (${puntos[1]})"
        _titulo.value = "P$jugador: ${puntos[jugador]}"


        if (puntos[jugador] > 21){
            GlobalScope.launch {
                SystemClock.sleep(1000)
                launch(Main) {
                    nextPlayer()
                }
            }

            nextPlayer()
        }
    }

    fun nextPlayer() {
        if (jugador == 0){
            jugador = 1
            _cartas.clear()
            sacaCarta()
            sacaCarta()
        } else {
            checkWinner()
        }
    }

    private fun checkWinner() {
        if (puntos[0] > 21 && puntos[1] > 21) {
            _msg.value = "Tablas"
        } else if (puntos[0] > 21 && puntos[1] <= 21) {
            _msg.value = "Gana el jugador 2 \n\n P1 (${puntos[0]})- P2 (${puntos[1]})"
        } else if (puntos[0] <= 21 && puntos[1] > 21) {
            _msg.value = "Gana el jugador 1 \n\n P1 (${puntos[0]})- P2 (${puntos[1]})"
        } else if (puntos[0] > puntos[1]) {
            _msg.value = "Gana el jugador 1 \n\n P1 (${puntos[0]})- P2 (${puntos[1]})"
        } else if (puntos[0] < puntos[1]) {
            _msg.value = "Gana el jugador 2 \n\n P1 (${puntos[0]})- P2 (${puntos[1]})"
        } else {
            _msg.value = "Tablas"
        }
        Toast.makeText(mainActivity.baseContext, msg.value, Toast.LENGTH_SHORT).show()
       // _openDialog.value = true
    }



}
