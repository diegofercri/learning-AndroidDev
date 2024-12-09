package net.azarquiel.acbroomjpc.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Equipo::class, Jugador::class], version = 1)
abstract class AcbDB: RoomDatabase() {
    abstract fun daoEquipo(): DaoEquipo
    abstract fun daoJugador(): DaoJugador
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE:  AcbDB? = null


        fun getDatabase(context: Context): AcbDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AcbDB::class.java,   "acb.sqlite"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
