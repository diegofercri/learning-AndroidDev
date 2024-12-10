package com.example.pueblosbonitos.model

import android.app.Application
import androidx.lifecycle.LiveData

class ComunidadRepository (application: Application) {
    val comunidadDAO = PueblosBonitosDB.getDatabase(application).comunidadDAO()
    fun getComunidades() : LiveData<List<Comunidad>> {
        return comunidadDAO.getComunidades()
    }
}

class PuebloRepository (application: Application) {
    val puebloDAO = PueblosBonitosDB.getDatabase(application).puebloDAO()

    fun  getPueblosByComunidad(comunidad:Int) : LiveData<List<PuebloWp>> {
        return puebloDAO.getPueblosByComunidad(comunidad)
    }
    fun  getPueblosFavByComunidad(comunidad:Int) : LiveData<List<PuebloWp>> {
        return puebloDAO.getPueblosFavByComunidad(comunidad)
    }
    fun update(pueblo: Pueblo) {
        puebloDAO.update(pueblo)
    }
}