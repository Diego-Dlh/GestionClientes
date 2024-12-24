import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prueba.data.ClienteEntity
import com.prueba.data.Repository
import kotlinx.coroutines.launch

// Definimos la clase MainViewModel que extiende de ViewModel.
// ViewModel nos permite gestionar los datos relacionados con la interfaz de usuario de manera eficiente y reactiva.
class MainViewModel(private val repository: Repository) : ViewModel() {

    // Definimos un par de variables para simular un sistema de autenticación básico.
    // En un entorno real, deberíamos utilizar un sistema de autenticación más robusto.
    private val validUsername = "usuario"  // Usuario válido para el login
    private val validPassword = "123456"  // Contraseña válida para el login

    // Función de login que verifica si las credenciales coinciden con las válidas.
    // Devuelve un valor booleano indicando si las credenciales son correctas.
    fun login(username: String, password: String): Boolean {
        return username == validUsername && password == validPassword
    }

    // Lista observable de clientes, se utilizará en la UI para mostrar y gestionar los datos de clientes.
    val clientes = mutableStateListOf<ClienteEntity>()

    // Función para guardar un nuevo cliente en la base de datos.
    // Recibe el documento, nombre y lista de teléfonos del cliente a agregar.
    fun saveCliente(documento: Int, nombre: String, telefonos: List<String>) {
        // Creamos un objeto ClienteEntity con los datos proporcionados.
        val newCliente = ClienteEntity(documento = documento, nombre = nombre, telefonos = telefonos)

        // Llamamos a viewModelScope.launch para realizar operaciones asincrónicas dentro de la ViewModel.
        viewModelScope.launch {
            // Insertamos el nuevo cliente en la base de datos mediante el repositorio.
            repository.insertCliente(newCliente)

            // Actualizamos la lista local de clientes sin recargar toda la lista desde la base de datos.
            // Solo agregamos el cliente si aún no existe en la lista.
            if (clientes.none { it.documento == documento }) {
                clientes.add(newCliente)
            }
        }
    }

    // Función para eliminar un cliente de la base de datos.
    // Recibe el documento del cliente a eliminar.
    fun deleteCliente(documento: Int) {
        viewModelScope.launch {
            // Eliminamos el cliente de la base de datos utilizando el repositorio.
            repository.deleteCliente(documento)
            fetchClientes() // Refrescamos la lista de clientes para reflejar la eliminación.
        }
    }

    // Función para cargar todos los clientes desde la base de datos.
    // Actualiza la lista de clientes local en la UI.
    fun fetchClientes() {
        viewModelScope.launch {
            // Limpiamos la lista actual de clientes antes de cargar los nuevos datos.
            clientes.clear()
            // Obtenemos todos los clientes de la base de datos y los agregamos a la lista observable.
            clientes.addAll(repository.getAllClientes())
        }
    }

    // Función para actualizar los datos de un cliente existente.
    // Recibe el ID del cliente, el nombre y la nueva lista de teléfonos.
    fun updateCliente(clienteId: Int, nombre: String, telefonosList: List<String>) {
        viewModelScope.launch {
            // Buscamos el cliente por su ID en la base de datos.
            val cliente = repository.getClienteById(clienteId)
            if (cliente != null) {
                // Creamos una nueva instancia de ClienteEntity con los datos actualizados.
                val updatedCliente = cliente.copy(
                    nombre = nombre,
                    telefonos = telefonosList
                )
                // Actualizamos los datos del cliente en la base de datos.
                repository.updateCliente(updatedCliente)
                fetchClientes() // Refrescamos la lista de clientes para reflejar la actualización.
            }
        }
    }
}
