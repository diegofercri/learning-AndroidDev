package net.azarquiel.carrojpc

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import net.azarquiel.carrojpc.model.Producto


class MainViewModel(mainActivity: MainActivity): ViewModel() {
    private lateinit var carroSH: SharedPreferences
    val MainActivity by lazy { mainActivity }

    private var _openDialog = MutableLiveData(false)
    val openDialog: MutableLiveData<Boolean> = _openDialog

    //private var _productos = MutableLiveData(ArrayList<Producto>())
    //val productos: LiveData<ArrayList<Producto>> = _productos

    var productos = mutableStateListOf<Producto>()

    init {
        carroSH = mainActivity.getSharedPreferences("carro", Context.MODE_PRIVATE)
        getProductos()
    }

    fun setDialog(value: Boolean) {
        _openDialog.value = value
    }

    fun addProducto(producto: Producto) {//Meter clave en sharepreferences
        producto.id = carroSH.all.size + 1
        val productoJson = Gson().toJson(producto)
        val editor = carroSH.edit()
        editor.putString(producto.id.toString(), productoJson)
        editor.apply()
        productos.add(0,producto)

    }
    fun getProductos(){ //devuelve con el livedata
        val carroAll = carroSH.all
        productos.clear()
        for ((key, value) in carroAll) {
            val jsonProdcuto = value.toString()
            val producto = Gson().fromJson(jsonProdcuto, Producto::class.java)
            productos.add(producto)
        }
    }

   /* fun getProducto(id: Int): Producto? {
        var producto:Producto? = null
       val productoJson = carroSH.getString(id.toString(),null)
        if (productoJson != null){
            producto = Gson().fromJson(productoJson, Producto::class.java)
        }

        return producto
    }
     */


}
