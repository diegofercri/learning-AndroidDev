package net.azarquiel.andalusiabeaches.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import net.azarquiel.andalusiabeaches.model.Coast
import net.azarquiel.andalusiabeaches.model.CoastRepository

class CoastViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CoastRepository(application)
    fun getCoasts(): LiveData<List<Coast>> {
        return repository.getCoasts()
    }
}