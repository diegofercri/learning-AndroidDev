package net.azarquiel.andalusiabeaches.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity(tableName = "coast")
data class Coast(@PrimaryKey
                 var id : Int=0,
                 var name : String="",
                 var image : String="",
                 var description : String=""):Serializable


@Entity(tableName = "beach",
    foreignKeys = [ForeignKey(entity= Coast::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("coast"))])
data class Beach(@PrimaryKey
                 var id : Int=0,
                 var coast : Int=0,
                 var blue : Int=0,
                 var name : String="",
                 var image : String="",
                 var description : String=""):Serializable


data class CoastWithBeaches(
    @Embedded val coast: Coast,
    @Relation(
        parentColumn = "id",
        entityColumn = "coast"
    )
    val beaches: List<Beach>
):Serializable
