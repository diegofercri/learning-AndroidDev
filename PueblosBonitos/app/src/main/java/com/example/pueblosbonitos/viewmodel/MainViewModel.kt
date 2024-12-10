package com.example.pueblosbonitos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pueblosbonitos.R
import com.example.pueblosbonitos.model.Comunidad
import com.example.pueblosbonitos.model.Pueblo
import com.example.pueblosbonitos.model.PuebloWp
import com.example.pueblosbonitos.util.Util
import com.example.pueblosbonitos.view.MainActivity

class MainViewModel (mainActivity: MainActivity): ViewModel(){
    private var pueblosAll: List<PuebloWp> = emptyList()
    private var pueblosFav: List<PuebloWp> = emptyList()
    private val mainActivity: MainActivity by lazy { mainActivity }
    private var puebloViewModel: PuebloViewModel
    private var isFav:Boolean = false

    private val _colorFav: MutableLiveData<Int> = MutableLiveData()
    val colorFav: LiveData<Int> = _colorFav

    private val _comunidades: MutableLiveData<List<Comunidad>> = MutableLiveData()
    val comunidades: LiveData<List<Comunidad>> = _comunidades

    private val _pueblos: MutableLiveData<List<PuebloWp>> = MutableLiveData()
    val pueblos: LiveData<List<PuebloWp>> = _pueblos

    init {
        Util.inyecta(mainActivity, "pueblosbonitos.sqlite")
        puebloViewModel = ViewModelProvider(mainActivity).get(PuebloViewModel::class.java)
        val comunidadViewModel = ViewModelProvider(mainActivity).get(ComunidadViewModel::class.java)
        comunidadViewModel.getComunidades().observe(mainActivity, Observer { comunidades ->
            _comunidades.value = comunidades
        })
        isFav = false
        _colorFav.value = R.color.gris
    }
    fun getPueblosAll(comunidad:Int) {
        puebloViewModel.getPueblosByComunidad(comunidad).observe(mainActivity, Observer { pueblos ->
            pueblosAll= pueblos
        })
    }

    fun getPueblosFav(comunidad:Int) {
        puebloViewModel.getPueblosFavByComunidad(comunidad).observe(mainActivity, Observer { pueblos ->
            pueblosFav = pueblos
        })
    }

    fun getPueblos(comunidad: Int) {
        getPueblosFav(comunidad)
        getPueblosAll(comunidad)
        _pueblos.value = pueblosAll
        isFav = false
    }

    fun changeFav() {
        isFav = !isFav
        _pueblos.value = if (isFav) pueblosFav else pueblosAll
        _colorFav.value = if (isFav) R.color.amarillo else R.color.gris
    }

    fun changeFavPueblo(pueblo: Pueblo) {
        pueblo.fav = if (pueblo.fav == 0) 1 else 0
        puebloViewModel.update(pueblo)
    }
}