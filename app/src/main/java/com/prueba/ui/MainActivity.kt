package com.prueba.ui

// Importación de las dependencias necesarias para la actividad principal y la navegación
import MainViewModel
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prueba.data.UserDatabase
import com.prueba.data.Repository
import com.prueba.main.ClienteListScreen
import com.prueba.main.LoginScreen
import com.prueba.main.screens.CreateClienteScreen
import com.prueba.main.screens.EditClienteScreen

// Actividad principal que configura la interfaz de usuario con Jetpack Compose
class MainActivity : ComponentActivity() {

    // Inicialización del ViewModel utilizando un Factory con un Repository
    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            Repository(
                UserDatabase.getDatabase(applicationContext).clienteDao,  // Acceso al DAO de clientes
                UserDatabase.getDatabase(applicationContext).logDao // Acceso al DAO de logs
            )
        )
    }

    // Método onCreate que se ejecuta cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()  // Llamada para poner la aplicación en modo pantalla completa
        setContent {
            // Crear un controlador de navegación para gestionar las pantallas
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "login_screen") {
                // Composable para la pantalla de login
                composable("login_screen") {
                    LoginScreen(navController = navController, viewModel = viewModel)
                }
                // Composable para la pantalla de lista de clientes
                composable("cliente_list") {
                    ClienteListScreen(navController = navController, viewModel = viewModel)
                }
                // Composable para la pantalla de creación de cliente
                composable("create_cliente") {
                    CreateClienteScreen(navController = navController, viewModel = viewModel)
                }
                // Composable para la pantalla de edición de cliente, con un argumento clienteId
                composable("edit_cliente/{clienteId}") { backStackEntry ->
                    // Se obtiene el clienteId del argumento, asegurándose de que sea un entero válido
                    val clienteId = backStackEntry.arguments?.getString("clienteId")?.toIntOrNull() ?: -1
                    EditClienteScreen(navController = navController, viewModel = viewModel, clienteId = clienteId)
                }
            }
        }
    }

    // Función para configurar la actividad en modo pantalla completa
    private fun fullScreen() {
        // Eliminar la barra de estado y navegación para ofrecer una experiencia de pantalla completa
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN // Ocultar la barra de estado
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Ocultar la barra de navegación
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Permite que la aplicación mantenga el modo pantalla completa
                )
    }
}
