package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import presentation.viewmodel.OrderViewModel

class ConsoleController(
) {

    companion object{
        @JvmStatic
        var currentScreen: BaseScreen = HomeScreen()
    }

    fun start() {
        while (true) {
            currentScreen.display()
            currentScreen.handleInput()
        }
    }
}
