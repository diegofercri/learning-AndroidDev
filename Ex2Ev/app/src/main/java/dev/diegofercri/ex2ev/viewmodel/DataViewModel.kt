package dev.diegofercri.ex2ev.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import dev.diegofercri.ex2ev.api.MainRepository
import dev.diegofercri.ex2ev.model.Comentario
import dev.diegofercri.ex2ev.model.Gafa
import dev.diegofercri.ex2ev.model.Marca
import dev.diegofercri.ex2ev.model.Usuario

class DataViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getMarcas(): MutableLiveData<List<Marca>> {
        val marcas = MutableLiveData<List<Marca>>()
        GlobalScope.launch(Main) {
            marcas.value = repository.getMarcas()
        }
        return marcas
    }

    fun getGafas(idmarca:Int): MutableLiveData<List<Gafa>> {
        val gafas = MutableLiveData<List<Gafa>>()
        GlobalScope.launch(Main) {
            gafas.value = repository.getGafas(idmarca)
        }
        return gafas
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

    fun getComentarios(idgafa:Int): MutableLiveData<List<Comentario>> {
        val comentarios = MutableLiveData<List<Comentario>>()
        GlobalScope.launch(Main) {
            comentarios.value = repository.getComentarios(idgafa)
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

    fun saveComentario(idgafa: Int, comentario: Comentario): MutableLiveData<Comentario?> {
        val nuevoComentario = MutableLiveData<Comentario?>()
        GlobalScope.launch(Main) {
            nuevoComentario.value = repository.saveComentario(idgafa, comentario)
        }
        return nuevoComentario
    }
}
