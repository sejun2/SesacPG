package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import domain.model.SesacMenu
import presentation.viewmodel.OrderViewModel

class OrderScreen : BaseScreen {
    private val orderViewModel: OrderViewModel =
        OrderViewModel(SesacRestaurantOrderRepositoryImpl(SesacOrderDataSource()))

    override fun display() {
        println("주문하기")
    }

    override fun handleInput(): BaseScreen {
        println("테이블 번호: 1~7")
        val tableNumber = readlnOrNull()?.toInt()

        println("메뉴,개수;")
        val menuTable = setMenus(readlnOrNull() ?: "")

        if (menuTable.isNotEmpty()) {
            val res = orderViewModel.order(menuTable, tableNumber ?: -1)
            println("주문 내역: $res")
        }

        return HomeScreen()
    }

    private fun setMenus(order: String): HashMap<SesacMenu, Int> {
        val menuTable = HashMap<SesacMenu, Int>()
        val split1 = order.split(";")

        split1.forEach { orderString ->
            if (orderString.isEmpty()) {
                return@forEach
            }

            val split2 = orderString.split(",")
            val menu = SesacMenu.getSesacMenuByMenuName(split2[0])
            println("menu: $menu")
            val count = split2[1].toInt()
            println("count: $count")

            menu?.let {
                menuTable[it] = count
            }
        }

        return menuTable
    }
}