package presentation

import di.DIProvider
import di.SesacServiceLocator
import domain.model.Order
import domain.model.SesacMenu
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import presentation.viewmodel.OrderViewModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class OrderScreenTest {

    private lateinit var orderScreen: BaseScreen
    private val mockOrderViewModel = mockk<OrderViewModel>()

    private val standardOut = System.out
    private val standardIn = System.`in`
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        DIProvider.provide()
        orderScreen = OrderScreen(mockOrderViewModel)
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
        SesacServiceLocator.clearDi()
    }

    @Test
    fun display() {
        orderScreen.display()

        assertEquals("주문하기", outputStreamCaptor.toString().trimIndent())
    }

    @Test
    fun `order success test`() {
        clearMocks(mockOrderViewModel)

        every { mockOrderViewModel.order(any(), any()) } returns Order(
            id = 100,
            tableNumber = 1,
            orderedTime = 12345,
            paidTime = null,
            price = 50000,
            paid = false,
            menus = hashMapOf(
                SesacMenu.DENJANG_SOUP to 500
            )
        )

        val simulatedInput1 = """
            1
            된장찌개,500;
        """.trimIndent()
        val inputStream1 = ByteArrayInputStream(simulatedInput1.toByteArray())

        System.setIn(inputStream1)

        val res = orderScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("테이블 번호: 1~7"))
        assert(outputStreamCaptor.toString().trimIndent().contains("메뉴(김치찌개, 돈까스, 된장찌개, 순두부찌개, 비빔밥),개수;"))
        assert(outputStreamCaptor.toString().trimIndent().contains("주문 내역:"))
        assert(outputStreamCaptor.toString().trimIndent().contains("테이블번호: 1"))
        assert(outputStreamCaptor.toString().trimIndent().contains("메뉴이름: 된장찌개"))
        assert(outputStreamCaptor.toString().trimIndent().contains("개수: 500"))
        assert(outputStreamCaptor.toString().trimIndent().contains("주문시간:"))
        assert(outputStreamCaptor.toString().trimIndent().contains("결재여부:"))

        assert(ConsoleController.currentScreen is HomeScreen)
    }

    @Test
    fun `order fail test, when input out ranged table number, then it says error message`(){
        val simulatedInput1 = "999"
        val inputStream1 = ByteArrayInputStream(simulatedInput1.toByteArray())

        System.setIn(inputStream1)

        val res = orderScreen.handleInput()

       assert(outputStreamCaptor.toString().trimIndent().contains("1~7 사이의 번호를 입력해주세요"))
    }

    @Test
    fun `order fail test, when input wrong menu string, then it says error message`(){
        val simulatedInput1 = """
            5
            홍두깨국수,500;
            """".trimIndent() // it has no such menu
        val inputStream1 = ByteArrayInputStream(simulatedInput1.toByteArray())

        System.setIn(inputStream1)

        val res = orderScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("메뉴를 올바르게 입력해주세요"))
    }
}