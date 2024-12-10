package net.azarquiel.andalusiabeaches.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.andalusiabeaches.R
import net.azarquiel.andalusiabeaches.model.Coast
import net.azarquiel.andalusiabeaches.model.Beach
import net.azarquiel.andalusiabeaches.util.Util
import net.azarquiel.andalusiabeaches.view.MainActivity

class MainViewModel (mainActivity: MainActivity): ViewModel(){
    private var allBeaches: List<Beach> = emptyList()
    private var blueBeaches: List<Beach> = emptyList()
    private val mainActivity: MainActivity by lazy { mainActivity }
    private var beachViewModel: BeachViewModel
    private var isBlue:Boolean = false

    private val _blue: MutableLiveData<Int> = MutableLiveData()
    val blue: LiveData<Int> = _blue

    private val _coasts: MutableLiveData<List<Coast>> = MutableLiveData()
    val coasts: LiveData<List<Coast>> = _coasts

    private val _beaches: MutableLiveData<List<Beach>> = MutableLiveData()
    val beaches: LiveData<List<Beach>> = _beaches

    init {
        Util.inyecta(mainActivity, "playasandalu.db")
        beachViewModel = ViewModelProvider(mainActivity).get(BeachViewModel::class.java)
        val coastViewModel = ViewModelProvider(mainActivity).get(CoastViewModel::class.java)
        coastViewModel.getCoasts().observe(mainActivity, Observer { coasts ->
            _coasts.value = coasts
        })
        isBlue = false
        _blue.value = R.color.gris
    }

    fun getAllBeaches(coast:Int) {
        beachViewModel.getBeachesByCoast(coast).observe(mainActivity, Observer { beaches ->
            allBeaches= beaches
        })
    }

    fun getBlueBeaches(coast:Int) {
        beachViewModel.getBlueBeachesByCoast(coast).observe(mainActivity, Observer { beaches ->
            blueBeaches = beaches
        })
    }

    fun getBeaches(coast: Int) {
        getBlueBeaches(coast)
        getAllBeaches(coast)
        _beaches.value = allBeaches
        isBlue = false
    }

    fun changeBlue() {
        isBlue = !isBlue
        _beaches.value = if (isBlue) blueBeaches else allBeaches
        _blue.value = if (isBlue) R.color.amarillo else R.color.gris
    }
}