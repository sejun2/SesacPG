package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import presentation.viewmodel.OrderViewModel
import kotlin.system.exitProcess

class HomeScreen : BaseScreen {
    override fun display() {
        println("1.테이블 별 주문 2.매출관리 3.테이블 계산 0.P/G 종료")
    }

    override fun handleInput(): BaseScreen? {
        return when (readlnOrNull() ?: "n") {
            "1" -> {
                OrderScreen(
                    OrderViewModel(
                        SesacRestaurantOrderRepositoryImpl(
                            SesacOrderDataSource()
                        )
                    )
                )
            }

            "2" -> {
                SalesScreen()
            }

            "3" -> {
                PaymentScreen()
            }

            "0" -> {
                exitProcess(0)
            }

            else -> {
                println("잘못된 선택")
                null
            }
        }
    }
}