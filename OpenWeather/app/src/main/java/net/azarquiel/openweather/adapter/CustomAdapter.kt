package net.azarquiel

import net.azarquiel.openweather.R
import net.azarquiel.openweather.model.Day
import net.azarquiel.openweather.model.Result
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.semantics.text
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlin.io.path.name

class CharacterAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val statusTextView: TextView = view.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        holder.nameTextView.text = character.name
        holder.statusTextView.text = character.status
    }

    override fun getItemCount() = characters.size
}

class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Day> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setDays(days: List<Day>) {
        this.dataList = days
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Day){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivWeather = itemView.findViewById(R.id.ivWeather) as ImageView
            val tvDate = itemView.findViewById(R.id.tvDate) as TextView
            val tvDescription = itemView.findViewById(R.id.tvDescription) as TextView
            val tvTempMax = itemView.findViewById(R.id.tvTempMax) as TextView
            val tvTempMin = itemView.findViewById(R.id.tvTempMin) as TextView
            val tvPop = itemView.findViewById(R.id.tvPop) as TextView

            //....

            tvDate.text = dataItem.dt_txt
            tvDescription.text = dataItem.weather[0].description
            tvTempMax.text = dataItem.main.temp_max.toString()
            tvTempMin.text = dataItem.main.temp_min.toString()
            tvPop.text = dataItem.pop.toString()

            //....

            // foto de internet a traves de Picasso
            Picasso.get().load("http://openweathermap.org/img/w/${dataItem.weather[0].icon}.png").into(ivWeather)

            itemView.tag = dataItem

        }

    }
}