package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantSalesRepositoryImpl
import domain.model.SesacMenu
import domain.model.toPrettyString
import presentation.viewmodel.SalesViewModel

class SalesScreen : BaseScreen {
    private val salesViewModel: SalesViewModel =
        SalesViewModel(SesacRestaurantSalesRepositoryImpl(SesacOrderDataSource()))

    override fun display() {
        println("1.테이블 별 매출 2.메뉴 별 매출 3.총 매출 4.계산 안된 테이블 리스트 출력 0.메인콘솔")
    }

    override fun handleInput(): BaseScreen? {
        return when (readlnOrNull() ?: "n") {
            "0" -> {
                println("메인콘솔")
                HomeScreen()
            }

            "1" -> {
                println("테이블 별 매출")
                println("테이블을 선택하세요 : 1~7")
                val tableNumber = readln().toInt()
                val res = salesViewModel.getSalesForTable(tableNumber)
                println(res)
                null
            }

            "2" -> {
                println("메뉴 별 매출")
                println("메뉴를 입력하세요: 김치찌개, 돈까스, 된장찌개, 순두부찌개, 비빔밥")
                val menu = SesacMenu.getSesacMenuByMenuName(readlnOrNull() ?: "")

                menu?.let {
                    val res = salesViewModel.getSalesForMenu(sesacMenu = menu)
                    println(res)
                }

                null
            }

            "3" -> {
                println("총 매출")
                val res = salesViewModel.getWholeSales()
                println(res)
                null
            }

            "4" -> {
                println("계산 안된 테이블 리스트 출력")
                val res = salesViewModel.getUnpaidTables()
                println(res.toPrettyString())
                null
            }

            else -> {
                println("잘못된 선택")
                null
            }
        }
    }
}