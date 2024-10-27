package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import domain.model.toPrettyString
import presentation.viewmodel.OrderViewModel
import utils.setMenus

class OrderScreen(
    private val orderViewModel: OrderViewModel = OrderViewModel(
        orderRepository = SesacRestaurantOrderRepositoryImpl(
            sesacOrderDataSource = SesacOrderDataSource()
        )
    )
) : BaseScreen {
    override fun display() {
        println("주문하기")
    }

    override fun handleInput(): BaseScreen {
        try {
            println("테이블 번호: 1~7")
            val tableNumber = readlnOrNull()?.toInt()
            require(tableNumber in 1..7) {
                throw IllegalArgumentException("1~7 사이의 번호를 입력해주세요")
            }

            println("메뉴(김치찌개, 돈까스, 된장찌개, 순두부찌개, 비빔밥),개수;")
            val menuTable = setMenus(readlnOrNull() ?: "")
            require(menuTable.isNotEmpty()) {
                throw IllegalStateException("메뉴를 올바르게 입력해주세요")
            }

            val res = orderViewModel.order(menuTable, tableNumber ?: -1)
            println("주문 내역: ${res.toPrettyString()}")
        } catch (e: Exception) {
            println(e.message)
        } finally {
            return HomeScreen()
        }
    }

}