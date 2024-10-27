package presentation

import domain.model.Order
import domain.model.SesacMenu
import domain.model.toPrettyString
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import presentation.viewmodel.PaymentViewModel
import presentation.viewmodel.SalesViewModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class PaymentScreenTest {
    private lateinit var paymentScreen: BaseScreen
    private lateinit var mockPaymenViewModel: PaymentViewModel

    private val standardOut = System.out
    private val standardIn = System.`in`
    private val outputStreamCaptor = ByteArrayOutputStream()

    val unpaidOrder1 =
        Order(
            id = 101,
            tableNumber = 1,
            orderedTime = 12345678,
            paidTime = null,
            price = 150000,
            paid = false,
            menus = hashMapOf(
                SesacMenu.DON_KATSU to 5,
                SesacMenu.DENJANG_SOUP to 15,
            )
        )
    val unpaidOrder2 =
        Order(
            id = 102,
            tableNumber = 2,
            orderedTime = 12345678,
            paidTime = null,
            price = 350000,
            paid = false,
            menus = hashMapOf(
                SesacMenu.BIBIMBAP to 5,
                SesacMenu.KIMCHI_SOUP to 15,
            )
        )

    @BeforeEach
    fun setUp() {
        mockPaymenViewModel = mockk<PaymentViewModel>()
        paymentScreen = PaymentScreen(mockPaymenViewModel)

        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
        clearMocks(mockPaymenViewModel)
    }

    @Test
    fun `display test, when unpaid order is empty, then prints only '결제하기' '미결제 목록'`() {
        every { mockPaymenViewModel.getUnpaidOrder() } returns listOf()

        paymentScreen.display()
        assert(outputStreamCaptor.toString().trimIndent().contains("결제하기"))
        assert(outputStreamCaptor.toString().trimIndent().contains("미결제 목록"))
        assert(!outputStreamCaptor.toString().trimIndent().contains("주문번호:"))
        assert(!outputStreamCaptor.toString().trimIndent().contains("테이블번호:"))
    }

    @Test
    fun `display test, when unpaid order is not empty, then prints '결제하기' '미결제 목록' 'order's prettyString'`() {
        every { mockPaymenViewModel.getUnpaidOrder() } returns listOf(
            unpaidOrder1, unpaidOrder2
        )

        paymentScreen.display()
        assert(outputStreamCaptor.toString().trimIndent().contains("결제하기"))
        assert(outputStreamCaptor.toString().trimIndent().contains("미결제 목록"))
        assert(outputStreamCaptor.toString().trimIndent().contains("주문번호: 101"))
        assert(outputStreamCaptor.toString().trimIndent().contains("테이블번호: 1"))
        assert(outputStreamCaptor.toString().trimIndent().contains("주문번호: 102"))
        assert(outputStreamCaptor.toString().trimIndent().contains("테이블번호: 2"))
    }

    @Test
    fun `handleInput test, when unpaid order is empty, then prints 'empty order error message' and returns HomeScreen`() {
        every { mockPaymenViewModel.getUnpaidOrder() } returns emptyList()

        val res = paymentScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("결제할 주문이 없습니다"))
        assert(res is HomeScreen)
    }

    @Test
    fun `handleInput test, when unpaid order is not empty, payment is successful, then prints payment success message and returns null`() {
        every { mockPaymenViewModel.getUnpaidOrder() } returns listOf(
            unpaidOrder1, unpaidOrder2
        )
        every { mockPaymenViewModel.payment(unpaidOrder1) } returns unpaidOrder1.copy(
            paid = true,
            paidTime = 123456789,
        )

        val simulatedInput = """
            101
        """.trimIndent()

        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)
        val res = paymentScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("결제할 주문 번호: "))
        assert(outputStreamCaptor.toString().trimIndent().contains("결제 완료"))
        assert(res == null)
    }

    @Test
    fun `handleInput test, when unpaid order is not empty, payment is unsuccessful, then do not prints payment success message and returns null`() {
        every { mockPaymenViewModel.getUnpaidOrder() } returns listOf(
            unpaidOrder1, unpaidOrder2
        )
        every { mockPaymenViewModel.payment(unpaidOrder1) } returns unpaidOrder1.copy(
            paid = true,
            paidTime = 123456789,
        )

        val simulatedInput = """
            777
        """.trimIndent()

        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)
        val res = paymentScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("결제할 주문 번호: "))
        assert(!outputStreamCaptor.toString().trimIndent().contains("결제 완료"))
        assert(res == null)
    }
}