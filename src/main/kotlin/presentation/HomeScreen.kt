package presentation

import di.SesacServiceLocator
import kotlin.system.exitProcess

class HomeScreen : BaseScreen {
    override fun display() {
        println("1.테이블 별 주문 2.매출관리 3.테이블 계산 0.P/G 종료")
    }

    override fun handleInput() {
        when (readlnOrNull() ?: "n") {
            "1" -> {
                ConsoleController.currentScreen = SesacServiceLocator.get<OrderScreen>()
            }

            "2" -> {
                ConsoleController.currentScreen = SesacServiceLocator.get<SalesScreen>()
            }

            "3" -> {
                ConsoleController.currentScreen = SesacServiceLocator.get<PaymentScreen>()
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