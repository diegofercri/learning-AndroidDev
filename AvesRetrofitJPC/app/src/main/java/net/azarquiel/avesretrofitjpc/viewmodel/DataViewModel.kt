package net.azarquiel.avesretrofitjpc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.avesretrofitjpc.api.MainRepository
import net.azarquiel.avesretrofitjpc.model.Comentario
import net.azarquiel.avesretrofitjpc.model.Recurso
import net.azarquiel.avesretrofitjpc.model.Usuario
import net.azarquiel.avesretrofitjpc.model.Zona

class DataViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getZonas(): MutableLiveData<List<Zona>> {
        val zonas = MutableLiveData<List<Zona>>()
        GlobalScope.launch(Main) {
            zonas.value = repository.getZonas()
        }
        return zonas
    }
    fun getRecursos(idzona:Int): MutableLiveData<List<Recurso>> {
        val recursos = MutableLiveData<List<Recurso>>()
        GlobalScope.launch(Main) {
            recursos.value = repository.getRecursos(idzona)
        }
        return recursos
    }
    fun getUsuarios(): MutableLiveData<List<Usuario>> {
        val usuarios = MutableLiveData<List<Usuario>>()
        GlobalScope.launch(Main) {
            usuarios.value = repository.getUsuarios()
        }
        return usuarios
    }
    fun getDataUsuarioPorNickPass(nick:String, pass:String): MutableLiveData<Usuario> {
        val usuario = MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usuario.value = repository.getDataUsuarioPorNickPass(nick, pass)
        }
        return usuario
    }
    fun getComentarios(idrecurso:Int): MutableLiveData<List<Comentario>> {
        val comentarios = MutableLiveData<List<Comentario>>()
        GlobalScope.launch(Main) {
            comentarios.value = repository.getComentarios(idrecurso)
        }
        return comentarios
    }

    fun saveUsuario(usuario: Usuario):MutableLiveData<Usuario> {
        val usurioResponse= MutableLiveData<Usuario>()
        GlobalScope.launch(Main) {
            usurioResponse.value = repository.saveUsuario(usuario)
        }
        return usurioResponse
    }
    fun saveComentario(idrecurso: Int, comentario: Comentario): MutableLiveData<Comentario?> {
        val nuevoComentario = MutableLiveData<Comentario?>()
        GlobalScope.launch(Main) {
            nuevoComentario.value = repository.saveComentario(idrecurso, comentario)
        }
        return nuevoComentario
    }
}
