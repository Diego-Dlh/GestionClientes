package com.prueba.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// Definición de la entidad LogEntity que representa un registro en la tabla "logs"
@Entity(tableName = "logs")
data class LogEntity(
    @PrimaryKey(autoGenerate = true)  // La clave primaria se genera automáticamente
    val id: Int = 0,  // ID único del log, generado automáticamente
    val comentario: String // Comentario asociado al log
)
