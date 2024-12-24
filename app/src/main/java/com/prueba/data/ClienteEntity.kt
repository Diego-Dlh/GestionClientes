package com.prueba.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

// Entidad de cliente para la tabla "clientes" en la base de datos
@Entity(tableName = "clientes")
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,  // ID único del cliente, generado automáticamente
    val documento: Int,  // Documento del cliente (por ejemplo, cédula o NIT)
    val nombre: String,  // Nombre del cliente
    @TypeConverters(TelefonosConverter::class) val telefonos: List<String>  // Lista de números de teléfono del cliente
)
