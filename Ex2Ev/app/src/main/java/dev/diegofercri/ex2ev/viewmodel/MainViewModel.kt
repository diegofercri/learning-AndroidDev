package dev.diegofercri.ex2ev.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import dev.diegofercri.ex2ev.model.Comentario
import dev.diegofercri.ex2ev.model.Gafa
import dev.diegofercri.ex2ev.model.Marca
import dev.diegofercri.ex2ev.model.Usuario
import dev.diegofercri.ex2ev.view.MainActivity

class MainViewModel(mainActivity: MainActivity): ViewModel()  {
    private lateinit var userSH: SharedPreferences

    private val mainActivity by lazy { mainActivity }

    private var dataviewModel: DataViewModel=ViewModelProvider(mainActivity)[DataViewModel::class.java]

    private val _usuario: MutableLiveData<Usuario?> = MutableLiveData()
    val usuario: LiveData<Usuario?> = _usuario

    private val _openDialogLogin = MutableLiveData(false)
    val openDialogLogin: LiveData<Boolean> = _openDialogLogin

    private val _openDialogComentario = MutableLiveData(false)
    val openDialogComentario: LiveData<Boolean> = _openDialogComentario

    private val _marcas: MutableLiveData<List<Marca>> = MutableLiveData()
    val marcas: LiveData<List<Marca>> = _marcas

    private val _gafas: MutableLiveData<List<Gafa>> = MutableLiveData()
    val gafas: LiveData<List<Gafa>> = _gafas

    private val _comentarios: MutableLiveData<List<Comentario>> = MutableLiveData()
    val comentarios: LiveData<List<Comentario>> = _comentarios

    init {
        userSH = mainActivity.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        _usuario.value = getUser()
        dataviewModel.getMarcas().observe(mainActivity) {
            it?.let {
                _marcas.value = it
            }
        }
    }

    fun setDialogLogin(open: Boolean) {
        _openDialogLogin.value = open
    }
    fun setDialogComentario(open: Boolean) {
        _openDialogComentario.value = open
    }
    private fun addUser(usuario: Usuario) {
        val userJson = Gson().toJson(usuario)
        val editor = userSH.edit()
        editor.putString("usuario_logueado", userJson)
        editor.apply()
    }

    private fun getUser(): Usuario? {
        val jsonUsuario = userSH.getString("usuario_logueado", null)
        return if (jsonUsuario != null) {
            Gson().fromJson(jsonUsuario, Usuario::class.java)
        } else {
            null
        }
    }
    fun login(nick: String, pass: String) {
        dataviewModel.getDataUsuarioPorNickPass(nick, pass).observe(mainActivity) { userlogin ->
            if (userlogin != null) {
                _usuario.value = userlogin
                addUser(userlogin)
                Toast.makeText(mainActivity, "$nick Login exitoso...", Toast.LENGTH_SHORT).show()
            } else {
                dataviewModel.saveUsuario(Usuario((-1), nick, pass)).observe(mainActivity) { userregister ->
                    _usuario.value = userregister
                    if (userregister != null) {
                        addUser(userregister)
                    }
                    Toast.makeText(mainActivity, "$nick Registrado con éxito...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun logout() {
        _usuario.value = null
        val editor = userSH.edit()
        editor.remove("usuario_logueado")
        editor.apply()
        Toast.makeText(mainActivity, "Logout exitoso...", Toast.LENGTH_SHORT).show()
    }

    fun getGafas(idmarca: Int) {
        dataviewModel.getGafas(idmarca).observe(mainActivity) {
            it?.let {
                _gafas.value = it
            }
        }
    }
    fun getComentarios(idgafa: Int): LiveData<List<Comentario>> {
        dataviewModel.getComentarios(idgafa).observe(mainActivity) {
            it?.let {
                _comentarios.value = it
            }
        }
        return comentarios
    }
    fun saveComentario(idgafa: Int, comentario: Comentario) {
        dataviewModel.saveComentario(idgafa, comentario).observe(mainActivity) {
            it?.let {
                getComentarios(idgafa)
                Toast.makeText(mainActivity, "Comentario insertado con éxito...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
