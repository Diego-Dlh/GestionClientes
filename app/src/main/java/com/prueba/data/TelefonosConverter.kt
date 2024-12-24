package com.prueba.data

import androidx.room.TypeConverter

class TelefonosConverter {

    // Convierte una lista de teléfonos (List<String>) a un solo String, separando con comas
    @TypeConverter
    fun fromTelefonosList(telefonos: List<String>): String {
        return telefonos.joinToString(",")  // Une la lista en un solo String separado por comas
    }

    // Convierte un String con teléfonos separados por comas a una lista de String
    @TypeConverter
    fun toTelefonosList(telefonos: String): List<String> {
        return telefonos.split(",").map { it.trim() }  // Separa el String y elimina los espacios innecesarios
    }
}
