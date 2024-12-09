package net.azarquiel.acbroomjpc.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.acbroomjpc.model.JugadorWE
import net.azarquiel.acbroomjpc.view.MainActivity
import net.azarquiel.alltricks.util.Util

class MainViewModel(mainActivity: MainActivity) : ViewModel() {
    private val _jugadores: MutableLiveData<List<JugadorWE>> = MutableLiveData()
    val jugadores: LiveData<List<JugadorWE>> = _jugadores
    init {
        Util.inyecta(mainActivity, "acb.sqlite")
        val jugadorViewModel = ViewModelProvider(mainActivity).get(JugadorViewModel::class.java)

        jugadorViewModel.getJugadores().observe(mainActivity, Observer { jugadores ->
            _jugadores.value = jugadores
        })

    }
}