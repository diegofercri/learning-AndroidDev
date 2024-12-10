package com.example.pueblosbonitos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pueblosbonitos.model.Comunidad
import com.example.pueblosbonitos.model.ComunidadRepository

class ComunidadViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ComunidadRepository(application)
    fun getComunidades(): LiveData<List<Comunidad>> {
        return repository.getComunidades()
    }
}