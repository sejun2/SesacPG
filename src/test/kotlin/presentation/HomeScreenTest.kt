package presentation

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class HomeScreenTest {

    private val homeScreen: BaseScreen = HomeScreen()

    private val standardOut = System.out
    private val standardIn = System.`in`
    private val outputStreamCaptor = ByteArrayOutputStream()

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
    }

    @Test
    fun `display correct options`() {
        homeScreen.display()
        assertEquals("1.테이블 별 주문 2.매출관리 3.테이블 계산 0.P/G 종료", outputStreamCaptor.toString().trimIndent())
    }

    @Test
    fun `when option 1 is selected, then currentScreen being OrderScreen`() {
        val simulatedInput = "1\n"
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())
        System.setIn(inputStream)

        homeScreen.handleInput()

        assert(ConsoleController.currentScreen is OrderScreen)
    }

    @Test
    fun `when option 2 is selected, then currentScreen being SalesScreen()`() {
        val simulatedInput = "2\n"
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())
        System.setIn(inputStream)

        homeScreen.handleInput()

        assert(ConsoleController.currentScreen is SalesScreen)
    }

    @Test
    fun `when option 3 is selected, then currentScreen being PaymentScreen()`() {
        val simulatedInput = "3\n"
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())
        System.setIn(inputStream)

        val res = homeScreen.handleInput()

        assert(ConsoleController.currentScreen is PaymentScreen)
    }

    @Test
    @Disabled("can not simulate behavior after exitProcess()")
    fun `when option 0 is selected, then exit process`() {
        val simulatedInput = "0\n"
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())
        System.setIn(inputStream)

        homeScreen.handleInput()
    }

    @Test
    fun `when unsupported option is selected, then print '잘못된 선택'`() {
        val simulatedInput = "NOOPTION\n"
        val inputStream = ByteArrayInputStream(simulatedInput.toByteArray())
        System.setIn(inputStream)

        val res = homeScreen.handleInput()

        assertEquals("잘못된 선택", outputStreamCaptor.toString().trimIndent())
    }
}