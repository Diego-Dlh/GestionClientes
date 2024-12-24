package com.prueba.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// Interfaz DAO para interactuar con la tabla "logs" en la base de datos
@Dao
interface LogDao {

    // Consulta para obtener todos los registros de logs
    @Query("SELECT * FROM logs")
    suspend fun getAllLogs(): List<LogEntity>  // Devuelve una lista de todos los logs

    // Inserta un nuevo log en la tabla "logs"
    @Insert
    suspend fun insertLog(logEntity: LogEntity)  // Inserta un log en la base de datos
}
