package net.azarquiel.andalusiabeaches.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "costa")
data class Coast(@PrimaryKey
                 var id : Int=0,
                 var nombre : String="",
                 var imagen : String="",
                 var descripcion : String=""):Serializable


@Entity(tableName = "playa",
    foreignKeys = [ForeignKey(entity= Coast::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("costa"))])
data class Beach(@PrimaryKey
                 var id : Int=0,
                 var costa : Int=0,
                 var azul : Int=0,
                 var nombre : String="",
                 var imagen : String=""):Serializable


data class CoastWithBeaches(
    @Embedded val coast: Coast,
    @Relation(
        parentColumn = "id",
        entityColumn = "costa"
    )
    val beaches: List<Beach>
):Serializable
