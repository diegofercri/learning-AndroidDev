package net.azarquiel.metroroomjpc

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.alltricks.util.Util
import net.azarquiel.metroroomjpc.model.Linea
import net.azarquiel.metroroomjpc.model.LineaWithEstaciones
import net.azarquiel.metroroomjpc.viewmodel.LineaViewModel

class MainViewModel(mainActivity: MainActivity): ViewModel()  {
    private val _lineas: MutableLiveData<List<LineaWithEstaciones>> = MutableLiveData()
    val lineas: LiveData<List<LineaWithEstaciones>> = _lineas
    init {
        val lineaViewModel = ViewModelProvider(mainActivity).get(LineaViewModel::class.java)
        Util.inyecta(mainActivity, "MetroDB.db")
        lineaViewModel.getAllLineas().observe(mainActivity, Observer { lineas ->
            lineas.forEach{
                Log.d("paco", it.toString())
            }
            _lineas.value = lineas
        })
    }
}
