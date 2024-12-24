package com.prueba.data

// Clase Repository que gestiona el acceso a los datos de los clientes y los logs
class Repository(
    private val clienteDao: ClienteDao, // DAO para interactuar con la tabla de clientes
    private val logDao: LogDao // DAO para interactuar con la tabla de logs (agregado aquí)
) {

    // Métodos para gestionar los clientes

    // Método para insertar un cliente en la base de datos
    suspend fun insertCliente(cliente: ClienteEntity) {
        clienteDao.insertCliente(cliente) // Llama al método insertCliente del clienteDao
    }

    // Método para obtener todos los clientes de la base de datos
    suspend fun getAllClientes(): List<ClienteEntity> {
        return clienteDao.getAllClientes() // Llama al método getAllClientes del clienteDao
    }

    // Método para actualizar un cliente en la base de datos
    suspend fun updateCliente(cliente: ClienteEntity) {
        clienteDao.update(cliente) // Llama al método update del clienteDao
    }

    // Método para eliminar un cliente de la base de datos por su documento
    suspend fun deleteCliente(documento: Int) {
        clienteDao.deleteByDocumento(documento) // Llama al método deleteByDocumento del clienteDao
    }

    // Método para obtener un cliente por su ID
    suspend fun getClienteById(clienteId: Int): ClienteEntity? {
        return clienteDao.getClienteById(clienteId) // Llama al método getClienteById del clienteDao
    }

    // Métodos para gestionar los logs

    // Método para insertar un log en la base de datos
    suspend fun insertLog(logEntity: LogEntity) {
        logDao.insertLog(logEntity) // Llama al método insertLog del logDao
    }
}
