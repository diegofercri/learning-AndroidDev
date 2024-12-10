package com.example.pueblosbonitos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pueblosbonitos.model.Pueblo
import com.example.pueblosbonitos.model.PuebloRepository
import com.example.pueblosbonitos.model.PuebloWp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PuebloViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: PuebloRepository = PuebloRepository(application)

    fun getPueblosByComunidad(comunidad:Int): LiveData<List<PuebloWp>>{
        return repository.getPueblosByComunidad(comunidad)
    }
    fun getPueblosFavByComunidad(comunidad:Int): LiveData<List<PuebloWp>>{
        return repository.getPueblosFavByComunidad(comunidad)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun update(pueblo: Pueblo) {
        GlobalScope.launch() {
            repository.update(pueblo)
            launch(Dispatchers.Main) {
            }
        }
    }
}

