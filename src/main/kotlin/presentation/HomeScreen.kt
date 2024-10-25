package presentation

class HomeScreen : BaseScreen {
    override fun display() {
        println("1, 테이블 별 주문 2. 매출관리 3.테이블 계산 0.P/G 종료")
    }

    override fun handleInput(): BaseScreen? {
        return when (readlnOrNull() ?: "n") {
            "1" -> {
                println("1선택")
                OrderScreen()
            }

            "2" -> {
                println("2선택")
                SalesScreen()
            }

            "3" -> {
                println("3선택")
                null
            }

            "4" -> {
                println("4선택")
                null
            }

            else -> {
                println("잘못된 선택")
                null
            }
        }
    }
}