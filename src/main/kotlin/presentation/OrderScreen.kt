package presentation

import domain.model.SesacMenu
import domain.model.toPrettyString
import presentation.viewmodel.OrderViewModel

class OrderScreen(private val orderViewModel: OrderViewModel) : BaseScreen {
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

            if (menuTable.isNotEmpty()) {
                val res = orderViewModel.order(menuTable, tableNumber ?: -1)
                println("주문 내역: ${res.toPrettyString()}")
            }
        } catch (e: Exception) {
            println(e.message)
        } finally {
            return HomeScreen()
        }
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