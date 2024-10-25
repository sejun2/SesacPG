package data.repository

import data.datasource.SesacOrderDataSource
import data.dto.OrderDTO
import data.dto.toDomain
import domain.model.SesacMenu
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

class SesacRestaurantSalesRepositoryImplTest {

    companion object {
        lateinit var orderListJson: String
        lateinit var orderDtoList: List<OrderDTO>
        lateinit var sesacOrderDataSource: SesacOrderDataSource
        lateinit var repository: ISesacRestaurantSalesRepository

        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            orderListJson =
                """[{"id":3,"tableNumber":5,"orderedTime":123123126,"paidTime":1111311,"price":163500,"paid":true,"menus":{"BIBIMBAP":3,"DON_KATSU":10,"DENJANG_SOUP":6}},{"id":2,"tableNumber":2,"orderedTime":123123126,"paidTime":1111311,"price":138000,"paid":true,"menus":{"DON_KATSU":10,"DENJANG_SOUP":6}},{"id":1,"tableNumber":1,"orderedTime":123123123,"price":166500,"paid":false,"menus":{"BIBIMBAP":5,"KIMCHI_SOUP":7,"SOONDOOBU_SOUP":5}}]""".trimIndent()
            orderDtoList = listOf(
                OrderDTO(
                    id = 3,
                    tableNumber = 5,
                    orderedTime = 123123126,
                    paid = true,
                    menus = hashMapOf(
                        SesacMenu.DON_KATSU to 10,
                        SesacMenu.DENJANG_SOUP to 6,
                        SesacMenu.BIBIMBAP to 3
                    ),
                    paidTime = 1111311,
                    price = 90000 + 48000 + 25500
                ),
                OrderDTO(
                    id = 2,
                    tableNumber = 2,
                    orderedTime = 123123126,
                    paid = true,
                    menus = hashMapOf(
                        SesacMenu.DON_KATSU to 10,
                        SesacMenu.DENJANG_SOUP to 6
                    ),
                    paidTime = 1111311,
                    price = 90000 + 48000
                ),
                OrderDTO(
                    id = 1,
                    tableNumber = 1,
                    orderedTime = 123123123,
                    paid = false,
                    menus = hashMapOf(
                        SesacMenu.BIBIMBAP to 5,
                        SesacMenu.KIMCHI_SOUP to 7,
                        SesacMenu.SOONDOOBU_SOUP to 5
                    ),
                    paidTime = null,
                    price = 42500 + 84000 + 40000
                )
            )

            sesacOrderDataSource = mockk<SesacOrderDataSource>()
            every { sesacOrderDataSource.getOrder() } returns orderListJson
            every { sesacOrderDataSource.saveOrder(any()) } returns true

            repository = SesacRestaurantSalesRepositoryImpl(sesacOrderDataSource = sesacOrderDataSource)
        }

        @JvmStatic
        @AfterAll
        fun tearDown(): Unit {
        }
    }

    @Test
    fun getSalesForMenu() {
        val res = repository.getSalesForMenu(SesacMenu.BIBIMBAP)

        assertEquals(68000, res)
    }

    @Test
    fun getSalesForTable() {
        val res = repository.getSalesForTable(1)

        kotlin.test.assertEquals(42500 + 84000 + 40000, res)
    }

    @Test
    fun getWholeSales() {
        val res = repository.getWholeSales()

        kotlin.test.assertEquals(90000 + 48000 + 25500 + 90000 + 48000 + 42500 + 84000 + 40000, res)
    }

    @Test
    fun getUnpaidOrder() {
        val res = repository.getUnpaidOrder()

        val unpaidOrderDtoList = orderDtoList.filter {
            !it.paid
        }

        assertArrayEquals(unpaidOrderDtoList.map { it.toDomain() }.toTypedArray(), res.toTypedArray())
    }

}