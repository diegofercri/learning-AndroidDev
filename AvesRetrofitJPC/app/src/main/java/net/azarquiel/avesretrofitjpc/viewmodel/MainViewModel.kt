package net.azarquiel.avesretrofitjpc.viewmodel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.azarquiel.avesretrofitjpc.model.Comentario
import net.azarquiel.avesretrofitjpc.model.Recurso
import net.azarquiel.avesretrofitjpc.model.Usuario
import net.azarquiel.avesretrofitjpc.model.Zona
import net.azarquiel.avesretrofitjpc.view.MainActivity

class MainViewModel(mainActivity: MainActivity): ViewModel()  {

    private val mainActivity by lazy { mainActivity }

    private var dataviewModel: DataViewModel=ViewModelProvider(mainActivity)[DataViewModel::class.java]

    private val _usuario: MutableLiveData<Usuario?> = MutableLiveData()
    val usuario: LiveData<Usuario?> = _usuario

    private val _openDialogLogin = MutableLiveData(false)
    val openDialogLogin: LiveData<Boolean> = _openDialogLogin

    private val _openDialogComentario = MutableLiveData(false)
    val openDialogComentario: LiveData<Boolean> = _openDialogComentario

    private val _zonas: MutableLiveData<List<Zona>> = MutableLiveData()
    val zonas: LiveData<List<Zona>> = _zonas

    private val _recursos: MutableLiveData<List<Recurso>> = MutableLiveData()
    val recursos: LiveData<List<Recurso>> = _recursos

    private val _comentarios: MutableLiveData<List<Comentario>> = MutableLiveData()
    val comentarios: LiveData<List<Comentario>> = _comentarios

    init {
        dataviewModel.getZonas().observe(mainActivity) {
            it?.let {
                _zonas.value = it
            }
        }
    }

    fun setDialogLogin(open: Boolean) {
        _openDialogLogin.value = open
    }
    fun setDialogComentario(open: Boolean) {
        _openDialogComentario.value = open
    }

    fun login(nick: String, pass: String) {
        dataviewModel.getDataUsuarioPorNickPass(nick, pass).observe(mainActivity) { userlogin ->
            if (userlogin != null) {
                _usuario.value = userlogin
                Toast.makeText(mainActivity, "$nick Login ok...", Toast.LENGTH_SHORT).show()
            }
            else {
                dataviewModel.saveUsuario(Usuario(-1, nick, pass)).observe(mainActivity) { userregister ->
                    _usuario.value = userregister
                    Toast.makeText(mainActivity, "$nick Registed...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun logout() {
        _usuario.value = null
        Toast.makeText(mainActivity, "Logout exitoso", Toast.LENGTH_SHORT).show()
    }

    fun getRecursos(idzona: Int) {
        dataviewModel.getRecursos(idzona).observe(mainActivity) {
            it?.let {
                _recursos.value = it
            }
        }
    }
    fun getComentarios(idrecurso: Int): LiveData<List<Comentario>> {
        dataviewModel.getComentarios(idrecurso).observe(mainActivity) {
            it?.let {
                _comentarios.value = it
            }
        }
        return comentarios
    }
    fun saveComentario(idrecurso: Int, comentario: Comentario) {
        dataviewModel.saveComentario(idrecurso, comentario).observe(mainActivity) {
            it?.let {
                getComentarios(idrecurso)
                Toast.makeText(mainActivity, "Comentario insertado con éxito...", Toast.LENGTH_SHORT).show()
            }
        }

    }

}