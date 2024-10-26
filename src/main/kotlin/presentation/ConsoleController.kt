package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import presentation.viewmodel.OrderViewModel

class ConsoleController(
) {

    var currentScreen: BaseScreen = HomeScreen()

    fun start() {
        while (true) {
            currentScreen.display()
            when (currentScreen.handleInput()) {
                null -> continue
                is SalesScreen -> {
                    currentScreen = SalesScreen()
                }

                is HomeScreen -> {
                    currentScreen = HomeScreen()
                }

                is OrderScreen -> {
                    currentScreen = OrderScreen(
                        OrderViewModel(
                            orderRepository = SesacRestaurantOrderRepositoryImpl(
                                sesacOrderDataSource = SesacOrderDataSource()
                            )
                        )
                    )
                }

                is PaymentScreen -> {
                    currentScreen = PaymentScreen()
                }
            }
        }
    }

}
