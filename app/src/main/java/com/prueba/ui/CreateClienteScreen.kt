package com.prueba.main.screens

import MainViewModel
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class) // Habilita el uso de componentes Material 3 experimentales
@Composable
fun CreateClienteScreen(navController: NavController, viewModel: MainViewModel) {
    // Estado para almacenar los valores de los campos del cliente
    val documento = remember { mutableStateOf("") }  // Documento del cliente
    val nombre = remember { mutableStateOf("") }     // Nombre del cliente
    val telefonos = remember { mutableStateOf("") }  // Teléfonos del cliente
    val context = LocalContext.current  // Acceso al contexto de la aplicación (para mostrar Toast)

    // Caja de contenedor principal que ocupa todo el espacio de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()  // La caja llena toda la pantalla
            .padding(16.dp),  // Añade un margen alrededor
        contentAlignment = Alignment.Center // Centra el contenido dentro de la caja
    ) {
        // Card que se utiliza como contenedor principal para el formulario
        Card(
            modifier = Modifier.fillMaxWidth(), // El card ocupa todo el ancho de la pantalla
            elevation = CardDefaults.cardElevation(8.dp), // Sombra del card
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface) // Color de fondo
        ) {
            // Columna que contiene los campos del formulario
            Column(
                modifier = Modifier
                    .fillMaxWidth()  // La columna ocupa todo el ancho
                    .padding(24.dp), // Añade un relleno interno al contenido del card
                horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos dentro de la columna
            ) {
                // Título de la pantalla
                Text(
                    text = "Crear Cliente",  // Texto del título
                    fontSize = 24.sp,  // Tamaño de la fuente
                    fontWeight = FontWeight.Bold,  // Peso de la fuente
                    modifier = Modifier.padding(bottom = 20.dp),  // Añade un margen en la parte inferior
                    color = MaterialTheme.colorScheme.primary  // Color del texto (color primario del tema)
                )

                // Campo de texto para el documento del cliente
                TextField(
                    value = documento.value,  // Valor del campo (controlado por el estado)
                    onValueChange = { documento.value = it },  // Actualiza el valor del documento
                    label = { Text("Documento del Cliente") },  // Etiqueta del campo
                    modifier = Modifier
                        .fillMaxWidth()  // El campo ocupa todo el ancho
                        .padding(bottom = 12.dp),  // Añade margen inferior
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.background,  // Fondo del campo
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Color cuando el campo está enfocado
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline  // Color cuando el campo no está enfocado
                    )
                )

                // Campo de texto para el nombre del cliente
                TextField(
                    value = nombre.value,  // Valor del campo (controlado por el estado)
                    onValueChange = { nombre.value = it },  // Actualiza el valor del nombre
                    label = { Text("Nombre del Cliente") },  // Etiqueta del campo
                    modifier = Modifier
                        .fillMaxWidth()  // El campo ocupa todo el ancho
                        .padding(bottom = 12.dp),  // Añade margen inferior
                    singleLine = true,  // El campo es de una sola línea
                    shape = RoundedCornerShape(8.dp),  // Bordes redondeados en el campo
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.background,  // Fondo del campo
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Color cuando el campo está enfocado
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline  // Color cuando el campo no está enfocado
                    )
                )

                // Campo de texto para los teléfonos del cliente
                TextField(
                    value = telefonos.value,  // Valor del campo (controlado por el estado)
                    onValueChange = { telefonos.value = it },  // Actualiza el valor de los teléfonos
                    label = { Text("Teléfonos (separados por comas)") },  // Etiqueta del campo
                    modifier = Modifier
                        .fillMaxWidth()  // El campo ocupa todo el ancho
                        .padding(bottom = 20.dp),  // Añade margen inferior
                    shape = RoundedCornerShape(8.dp),  // Bordes redondeados en el campo
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.background,  // Fondo del campo
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,  // Color cuando el campo está enfocado
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline  // Color cuando el campo no está enfocado
                    )
                )

                // Botón para guardar el cliente
                Button(
                    onClick = {
                        // Verificación para asegurarse de que los campos no están vacíos
                        if (documento.value.isNotBlank() && nombre.value.isNotBlank() && telefonos.value.isNotBlank()) {
                            // Convierte los números de teléfono a una lista separada por comas
                            val telefonosList = telefonos.value.split(",").map { it.trim() }

                            // Verifica si el número de teléfonos excede el límite
                            if (telefonosList.size > 5) {
                                Toast.makeText(context, "No se pueden agregar más de 5 números de teléfono.", Toast.LENGTH_SHORT).show()
                            } else {
                                // Guarda el cliente usando el ViewModel
                                viewModel.saveCliente(documento.value.toInt(), nombre.value, telefonosList)
                                // Muestra un mensaje de éxito
                                Toast.makeText(context, "Cliente guardado exitosamente", Toast.LENGTH_SHORT).show()
                                // Navega hacia atrás en la pila de navegación
                                navController.popBackStack()
                            }
                        } else {
                            // Muestra un mensaje si algún campo está vacío
                            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()  // El botón ocupa todo el ancho
                        .padding(top = 16.dp),  // Añade margen superior
                    shape = RoundedCornerShape(12.dp),  // Bordes redondeados en el botón
                    contentPadding = PaddingValues(vertical = 12.dp),  // Añade relleno vertical al botón
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)  // Color del botón
                ) {
                    Text("Guardar Cliente", color = MaterialTheme.colorScheme.onPrimary)  // Texto del botón
                }
            }
        }
    }
}
