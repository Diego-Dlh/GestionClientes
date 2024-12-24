package com.prueba.data

import androidx.room.Dao // Anotación que indica que esta interfaz es un DAO (Data Access Object)
import androidx.room.Insert // Anotación que permite realizar la operación de inserción en la base de datos
import androidx.room.Query // Anotación que permite realizar consultas SQL
import androidx.room.Update // Anotación que permite realizar la operación de actualización en la base de datos

// Interfaz ClienteDao que define las operaciones de la base de datos relacionadas con los clientes
@Dao
interface ClienteDao {

    // Método para insertar un nuevo cliente en la base de datos
    @Insert
    suspend fun insertCliente(cliente: ClienteEntity) // La operación es suspendida porque es asíncrona

    // Método para obtener todos los clientes de la base de datos
    @Query("SELECT * FROM clientes") // Consulta SQL que selecciona todos los registros de la tabla 'clientes'
    suspend fun getAllClientes(): List<ClienteEntity> // Devuelve una lista de objetos ClienteEntity

    // Método para actualizar un cliente en la base de datos
    @Update
    suspend fun update(cliente: ClienteEntity) // Actualiza los datos de un cliente

    // Método para eliminar un cliente de la base de datos utilizando su documento
    @Query("DELETE FROM clientes WHERE documento = :documento") // Consulta SQL que elimina un cliente según su documento
    suspend fun deleteByDocumento(documento: Int) // Elimina el cliente con el documento especificado

    // Método para obtener un cliente por su ID (documento)
    @Query("SELECT * FROM clientes WHERE documento = :clienteId LIMIT 1") // Consulta SQL que busca un cliente por su documento (ID)
    suspend fun getClienteById(clienteId: Int): ClienteEntity? // Devuelve el cliente si se encuentra, o null si no existe
}
