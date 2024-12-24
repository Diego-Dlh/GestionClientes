package com.prueba.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

// Definición de la base de datos de Room, que incluye las entidades ClienteEntity y LogEntity
@Database(entities = [ClienteEntity::class, LogEntity::class], version = 3) // Indica las entidades y la versión de la base de datos
@TypeConverters(TelefonosConverter::class) // Utiliza un conversor personalizado para manejar tipos de datos complejos (como Teléfonos)
abstract class UserDatabase : RoomDatabase() {

    // Definición de los DAOs (Data Access Objects) que se utilizarán para acceder a la base de datos
    abstract val clienteDao: ClienteDao // DAO para interactuar con la tabla de clientes
    abstract val logDao: LogDao  // DAO para interactuar con la tabla de logs

    // Bloque companion object, que permite obtener una instancia única de la base de datos utilizando el patrón Singleton
    companion object {
        // Variable para almacenar la instancia única de la base de datos (instancia volátil)
        @Volatile
        private var INSTANCE: UserDatabase? = null

        // Función para obtener la instancia de la base de datos
        fun getDatabase(context: Context): UserDatabase {
            // Si la instancia ya existe, la retorna
            return INSTANCE ?: synchronized(this) {
                // Si la instancia no existe, la crea
                val instance = Room.databaseBuilder(
                    context.applicationContext, // Contexto de la aplicación
                    UserDatabase::class.java, // Clase de la base de datos
                    "user_database" // Nombre de la base de datos
                ).build() // Construye la base de datos
                // Asigna la nueva instancia a la variable INSTANCE
                INSTANCE = instance
                // Retorna la instancia
                instance
            }
        }
    }
}
