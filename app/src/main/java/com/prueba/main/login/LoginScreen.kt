package com.prueba.main
import MainViewModel
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class) // Indicamos que utilizamos funcionalidades experimentales de Material3
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    // Variables que se utilizarán para almacenar los valores de usuario y contraseña ingresados
    var username = remember { mutableStateOf("") }  // Estado mutable para el nombre de usuario
    var password = remember { mutableStateOf("") }  // Estado mutable para la contraseña

    // Obtener el contexto de la aplicación para mostrar el Toast (mensaje emergente)
    val context = LocalContext.current

    // Fondo de la pantalla con color suave de azul
    Box(
        modifier = Modifier
            .fillMaxSize()  // Hace que el fondo ocupe toda la pantalla
            .background(Color(0xFFE3F2FD)), // Color de fondo azul claro
        contentAlignment = Alignment.Center  // Centra el contenido en el centro de la pantalla
    ) {
        // Card que envuelve todo el contenido, con borde redondeado y un color de fondo blanco
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f) // Ancho del 85% de la pantalla
                .padding(16.dp),  // Espaciado alrededor del Card
            shape = RoundedCornerShape(16.dp),  // Borde redondeado
            colors = CardDefaults.cardColors(containerColor = Color.White),  // Fondo blanco
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)  // Sombra para el card
        ) {
            // Contenedor en columna que alinea los elementos de manera vertical
            Column(
                modifier = Modifier
                    .padding(24.dp)  // Espaciado alrededor de la columna
                    .fillMaxWidth(),  // Hace que la columna ocupe el 100% del ancho
                horizontalAlignment = Alignment.CenterHorizontally  // Centra los elementos horizontalmente
            ) {
                // Título de la pantalla de login, con estilo y color personalizado
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.titleLarge,  // Usamos el estilo de título grande
                    color = Color(0xFF1976D2), // Color azul
                    modifier = Modifier.padding(bottom = 24.dp)  // Espaciado inferior
                )

                // Campo de texto para ingresar el nombre de usuario
                TextField(
                    value = username.value,  // Valor actual del campo de texto
                    onValueChange = { username.value = it },  // Función que actualiza el estado cuando cambia el valor
                    label = { Text("Usuario") },  // Etiqueta del campo de texto
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),  // Ocupa todo el ancho y tiene un espaciado inferior
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5) // Fondo gris claro para el campo
                    )
                )

                // Campo de texto para ingresar la contraseña
                TextField(
                    value = password.value,  // Valor actual de la contraseña
                    onValueChange = { password.value = it },  // Actualiza el estado cuando cambia el valor
                    label = { Text("Contraseña") },  // Etiqueta para el campo de contraseña
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),  // Espaciado similar al campo de usuario
                    visualTransformation = PasswordVisualTransformation(),  // Oculta el texto de la contraseña
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF5F5F5) // Fondo gris claro
                    )
                )

                // Botón de inicio de sesión
                Button(
                    onClick = {
                        // Verifica si los campos no están vacíos
                        if (username.value.isNotBlank() && password.value.isNotBlank()) {
                            // Llama al método de login del ViewModel para verificar las credenciales
                            val isAuthenticated = viewModel.login(username.value, password.value)
                            if (isAuthenticated) {
                                // Si la autenticación es exitosa, redirige a la pantalla de clientes
                                navController.navigate("cliente_list") // Navegación al listado de clientes
                            } else {
                                // Si las credenciales son incorrectas, muestra un mensaje emergente
                                Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // Si alguno de los campos está vacío, muestra un mensaje emergente
                            Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()  // El botón ocupa todo el ancho disponible
                        .padding(top = 16.dp),  // Espaciado superior
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))  // Color de fondo azul para el botón
                ) {
                    // Texto dentro del botón
                    Text("Iniciar Sesión", color = Color.White, fontSize = 16.sp)  // Texto blanco y tamaño de fuente
                }
            }
        }
    }
}
