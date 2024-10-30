package presentation

import kotlin.system.exitProcess

class HomeScreen : BaseScreen {
    override fun display() {
        println("1.테이블 별 주문 2.매출관리 3.테이블 계산 0.P/G 종료")
    }

    override fun handleInput() {
        when (readlnOrNull() ?: "n") {
            "1" -> {
                ConsoleController.currentScreen = OrderScreen()
            }

            "2" -> {
                ConsoleController.currentScreen = SalesScreen()
            }

            "3" -> {
                ConsoleController.currentScreen = PaymentScreen()
            }

            "0" -> {
                exitProcess(0)
            }

            else -> {
                println("잘못된 선택")
            }
        }
    }
}