package com.prueba.main

import MainViewModel
import androidx.compose.foundation.background // Importa el modificador para establecer el fondo
import androidx.compose.foundation.layout.* // Importa los modificadores para el layout
import androidx.compose.foundation.lazy.LazyColumn // Importa el componente LazyColumn para listas eficientes
import androidx.compose.foundation.lazy.items // Importa el modificador para manejar la lista de ítems
import androidx.compose.foundation.shape.RoundedCornerShape // Importa la forma redondeada para tarjetas
import androidx.compose.material3.* // Importa los componentes de Material Design 3
import androidx.compose.runtime.Composable // Anotación para funciones composables
import androidx.compose.runtime.LaunchedEffect // Importa LaunchedEffect para ejecutar efectos secundarios
import androidx.compose.ui.Alignment // Importa las opciones de alineación
import androidx.compose.ui.Modifier // Importa los modificadores para modificar los componentes
import androidx.compose.ui.graphics.Color // Importa el color
import androidx.compose.ui.text.style.TextAlign // Importa las opciones de alineación de texto
import androidx.compose.ui.unit.dp // Importa la unidad de medida dp
import androidx.navigation.NavController // Importa el controlador de navegación
import com.prueba.data.ClienteEntity // Importa la clase ClienteEntity

// Pantalla que muestra la lista de clientes
@Composable
fun ClienteListScreen(navController: NavController, viewModel: MainViewModel) {

    // Carga inicial de clientes cuando la pantalla se crea
    LaunchedEffect(Unit) {
        viewModel.fetchClientes() // Llama a la función del ViewModel para obtener los clientes
    }

    // Caja que contiene todo el contenido de la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize() // Rellena el tamaño completo de la pantalla
            .background(Color(0xFFE3F2FD)), // Establece el fondo azul claro
        contentAlignment = Alignment.TopCenter // Alinea el contenido al centro superior
    ) {
        // Columna que organiza los elementos verticalmente
        Column(
            modifier = Modifier
                .fillMaxSize() // Rellena todo el espacio disponible
                .padding(16.dp) // Añade un padding de 16 dp
        ) {
            // Título principal de la pantalla
            Text(
                text = "Lista de Clientes", // El texto del título
                style = MaterialTheme.typography.titleLarge, // Estilo del texto
                color = Color(0xFF1976D2), // Color azul principal
                textAlign = TextAlign.Center, // Alineación centrada
                modifier = Modifier
                    .fillMaxWidth() // Hace que el texto ocupe todo el ancho
                    .padding(bottom = 16.dp) // Añade padding en la parte inferior
            )

            // Botón para navegar a la pantalla de creación de un nuevo cliente
            Button(
                onClick = { navController.navigate("create_cliente") }, // Navega a la pantalla de creación
                modifier = Modifier
                    .fillMaxWidth() // Hace que el botón ocupe todo el ancho
                    .padding(bottom = 16.dp), // Añade padding en la parte inferior
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // Color del fondo del botón
                shape = MaterialTheme.shapes.medium // Forma redondeada del botón
            ) {
                // Texto del botón
                Text("Crear Nuevo Cliente", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }

            // Lista de clientes utilizando LazyColumn para optimizar la carga de datos
            LazyColumn(
                modifier = Modifier.weight(1f) // Hace que la lista ocupe el espacio restante disponible
            ) {
                // Itera sobre la lista de clientes proporcionada por el ViewModel
                items(viewModel.clientes) { cliente ->
                    // Cada ítem es un cliente que se muestra en la lista
                    ClienteItem(
                        cliente = cliente, // Pasa el cliente a la vista de ítem
                        onDelete = { viewModel.deleteCliente(cliente.documento) }, // Función para eliminar el cliente
                        onEdit = { navController.navigate("edit_cliente/${cliente.documento}") } // Función para editar el cliente
                    )
                }
            }

            // Botón para actualizar manualmente la lista de clientes
            Button(
                onClick = { viewModel.fetchClientes() }, // Llama la función para obtener clientes nuevamente
                modifier = Modifier
                    .fillMaxWidth() // Hace que el botón ocupe todo el ancho
                    .padding(top = 16.dp), // Añade padding en la parte superior
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // Color del fondo del botón
                shape = MaterialTheme.shapes.medium // Forma redondeada del botón
            ) {
                // Texto del botón
                Text("Actualizar Lista", color = Color.White, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

// Componente que representa un ítem de cliente en la lista
@Composable
fun ClienteItem(cliente: ClienteEntity, onDelete: () -> Unit, onEdit: () -> Unit) {
    // Tarjeta que contiene la información del cliente
    Card(
        modifier = Modifier
            .fillMaxWidth() // Hace que la tarjeta ocupe todo el ancho
            .padding(bottom = 16.dp), // Añade padding en la parte inferior
        shape = RoundedCornerShape(12.dp), // Forma redondeada de la tarjeta
        colors = CardDefaults.cardColors(containerColor = Color.White), // Color de fondo de la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Sombra de la tarjeta
    ) {
        // Columna que organiza la información dentro de la tarjeta
        Column(
            modifier = Modifier
                .padding(16.dp) // Añade padding dentro de la tarjeta
                .fillMaxWidth(), // Hace que la columna ocupe todo el ancho disponible
            verticalArrangement = Arrangement.SpaceBetween // Espacio entre los elementos dentro de la tarjeta
        ) {
            // Información del cliente
            Text("Documento: ${cliente.documento}", style = MaterialTheme.typography.bodyMedium)
            Text("Nombre: ${cliente.nombre}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Teléfonos: ${cliente.telefonos.joinToString(", ")}", // Muestra los teléfonos separados por coma
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp) // Padding en la parte inferior del texto
            )

            // Fila de botones de acción (Editar y Eliminar)
            Row(
                modifier = Modifier.fillMaxWidth(), // Hace que la fila ocupe todo el ancho
                horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los botones
            ) {
                // Botón de edición
                Button(
                    onClick = onEdit, // Acción de edición
                    modifier = Modifier.weight(1f).padding(end = 8.dp), // Ocupa el 50% del ancho
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)), // Color azul del botón
                    shape = MaterialTheme.shapes.small // Forma redondeada del botón
                ) {
                    Text("Editar", color = Color.White, style = MaterialTheme.typography.bodyMedium) // Texto del botón
                }

                // Botón de eliminación
                Button(
                    onClick = onDelete, // Acción de eliminación
                    modifier = Modifier.weight(1f).padding(start = 8.dp), // Ocupa el 50% del ancho
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)), // Color rojo del botón
                    shape = MaterialTheme.shapes.small // Forma redondeada del botón
                ) {
                    Text("Eliminar", color = Color.White, style = MaterialTheme.typography.bodyMedium) // Texto del botón
                }
            }
        }
    }
}
