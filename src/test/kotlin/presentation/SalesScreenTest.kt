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
import presentation.viewmodel.SalesViewModel
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SalesScreenTest {

    private lateinit var salesScreen: BaseScreen
    private lateinit var mockSalesViewModel: SalesViewModel

    private val standardOut = System.out
    private val standardIn = System.`in`
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        mockSalesViewModel = mockk<SalesViewModel>()
        salesScreen = SalesScreen(mockSalesViewModel)

        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
        clearMocks(mockSalesViewModel)
    }

    @Test
    fun `display test`() {
        salesScreen.display()

        assertEquals(
            "1.테이블 별 매출 2.메뉴 별 매출 3.총 매출 4.계산 안된 테이블 리스트 출력 0.메인콘솔",
            outputStreamCaptor.toString().trimIndent()
        )
    }

    @Test
    fun `when option 0 is selected, then returns HomeScreen`() {
        val simulatedInput = "0"
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(res is HomeScreen)
    }

    @Test
    fun `when option 1 is selected and select correct table number, then print sales for table and returns null`() {
        // mock viewModel behaviour
        every { mockSalesViewModel.getSalesForTable(any()) } returns 15000

        val simulatedInput = """
            1
            1
        """.trimIndent()
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("테이블 별 매출"))
        assert(outputStreamCaptor.toString().trimIndent().contains("테이블을 선택하세요 : 1~7"))
        assert(outputStreamCaptor.toString().trimIndent().contains("15000"))
        assert(res == null)
    }

    @Test
    fun `when option 1 is selected and select incorrect table number, then print error message and returns null`() {
        // mock viewModel behaviour
        every { mockSalesViewModel.getSalesForTable(any()) } returns 15000

        val simulatedInput = """
            1
            100
        """.trimIndent()
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("테이블 별 매출"))
        assert(outputStreamCaptor.toString().trimIndent().contains("테이블을 선택하세요 : 1~7"))
        assert(outputStreamCaptor.toString().trimIndent().contains("올바른 테이블 번호를 선택해주세요"))
        assert(res == null)
    }

    @Test
    fun `when option 2 is selected, then print sales for menu and returns null`() {
        // mock viewModel behaviour
        every { mockSalesViewModel.getSalesForMenu(any()) } returns 50000

        val simulatedInput = """
            2
            돈까스
        """.trimIndent()
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("메뉴 별 매출"))
        assert(outputStreamCaptor.toString().trimIndent().contains("메뉴를 입력하세요: 김치찌개, 돈까스, 된장찌개, 순두부찌개, 비빔밥"))
        assert(outputStreamCaptor.toString().trimIndent().contains("50000"))
        assert(res == null)
    }

    @Test
    fun `when options 3 is selected, then print whole sales and returns null`() {
        // mock viewModel behaviour
        every { mockSalesViewModel.getWholeSales() } returns 1600000

        val simulatedInput = """
            3
            돈까스
        """.trimIndent()
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("총 매출"))
        assert(outputStreamCaptor.toString().trimIndent().contains("1600000"))
        assert(res == null)
    }

    @Test
    fun `when options 4 is selected, then print unpaid order list and returns null`() {
        val order1 =
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
        val order2 =
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
        // mock viewModel behaviour
        every { mockSalesViewModel.getUnpaidTables() } returns listOf(
            order1, order2
        )

        val simulatedInput = """
            4
            돈까스
        """.trimIndent()
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("계산 안된 테이블 리스트 출력"))
        assert(outputStreamCaptor.toString().trimIndent().contains("${order1.toPrettyString()}"))
        assert(outputStreamCaptor.toString().trimIndent().contains("${order2.toPrettyString()}"))
        assert(res == null)
    }

    @Test
    fun `when select unsupported option, then prints error message and returns null`() {
        val simulatedInput = """
            59
        """.trimIndent()
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())

        System.setIn(inputStream)

        val res = salesScreen.handleInput()

        assert(outputStreamCaptor.toString().trimIndent().contains("잘못된 선택"))
        assert(res == null)
    }
}