package com.prueba.main.screens

import MainViewModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class) // Activamos el uso de APIs experimentales de Material 3
@Composable
fun EditClienteScreen(navController: NavController, viewModel: MainViewModel, clienteId: Int) {
    // Buscamos el cliente a editar en la lista de clientes del ViewModel por su documento
    val cliente = viewModel.clientes.find { it.documento == clienteId }

    // Variables reactivas para manejar los datos del cliente
    val documento = remember { mutableStateOf(cliente?.documento?.toString() ?: "") } // Documento es de solo lectura
    val nombre = remember { mutableStateOf(cliente?.nombre ?: "") } // Nombre editable
    val telefonos = remember { mutableStateOf(cliente?.telefonos?.joinToString(", ") ?: "") } // Teléfonos editables

    // Contexto para mostrar mensajes al usuario (Toasts)
    val context = LocalContext.current

    // Validamos si el cliente existe al iniciar la pantalla
    LaunchedEffect(cliente) {
        if (cliente == null) {
            // Si el cliente no se encuentra, mostramos un mensaje y navegamos a la lista de clientes
            Toast.makeText(context, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
            navController.navigate("cliente_list")
        }
    }

    // Contenedor principal con un fondo y diseño estilizado
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
    ) {
        // Campo de texto editable para mostrar el documento del cliente
        TextField(
            value = documento.value,
            onValueChange = { documento.value = it }, // Sin efecto ya que el campo está deshabilitado
            label = { Text("Documento del Cliente") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                disabledLabelColor = Color.Gray,
                disabledTextColor = Color.Black
            )
        )

        // Campo de texto editable para el nombre del cliente
        TextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text("Nombre del Cliente") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            singleLine = true, // El nombre se escribe en una sola línea
            shape = RoundedCornerShape(8.dp),
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFF6200EE), // Indicador en foco
                unfocusedIndicatorColor = Color.Gray // Indicador sin foco
            )
        )

        // Campo de texto editable para los números de teléfono
        TextField(
            value = telefonos.value,
            onValueChange = { telefonos.value = it },
            label = { Text("Teléfonos (separados por comas)") },
            enabled = false, // Campo deshabilitado
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFF6200EE),
                unfocusedIndicatorColor = Color.Gray
            )
        )

        // Botón para guardar los cambios realizados en el cliente
        Button(
            onClick = {
                if (nombre.value.isNotBlank() && telefonos.value.isNotBlank()) {
                    // Validamos los teléfonos ingresados como lista
                    val telefonosList = telefonos.value.split(",").map { it.trim() }

                    // Validamos la cantidad máxima de teléfonos permitidos
                    if (telefonosList.size > 5) {
                        Toast.makeText(context, "No se pueden agregar más de 5 números de teléfono.", Toast.LENGTH_SHORT).show()
                    } else {
                        // Guardamos los cambios a través del ViewModel
                        viewModel.updateCliente(clienteId, nombre.value, telefonosList)
                        Toast.makeText(context, "Cliente actualizado exitosamente", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Regresamos a la pantalla anterior
                    }
                } else {
                    // Mostramos un mensaje si los campos están incompletos
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(12.dp), // Botón con bordes redondeados
            contentPadding = PaddingValues(vertical = 12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE), // Color del botón
                contentColor = Color.White // Color del texto
            )
        ) {
            Text("Guardar Cambios") // Texto dentro del botón
        }

        // Botón adicional para cancelar y regresar sin guardar cambios
        TextButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Cancelar", color = Color(0xFF6200EE)) // Texto con estilo violeta
        }
    }
}
