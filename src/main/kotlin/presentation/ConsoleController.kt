package presentation

import domain.repository.ISesacRestaurantOrderRepository
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository

class ConsoleController(
    val orderRepository: ISesacRestaurantOrderRepository,
    val salesRepository: ISesacRestaurantSalesRepository,
    val paymentRepository: ISesacRestaurantPaymentRepository
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
            }
        }
    }

}
