package com.prueba.ui  // Paquete donde se encuentra la clase MainViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prueba.data.Repository  // Importación del Repository para manejar la lógica de datos

// Clase para la creación de instancias del ViewModel con su respectivo Repository
// Esta clase es necesaria para poder inyectar el Repository en el ViewModel de forma manual
class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    // Sobrescribimos el méttodo create para proporcionar el ViewModel necesario
    // Este méttodo es llamado por el ViewModelProvider para crear instancias de ViewModels
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Creamos el ViewModel usando el constructor que toma un Repository como parámetro
        return modelClass.getConstructor(Repository::class.java).newInstance(repository)
    }
}
