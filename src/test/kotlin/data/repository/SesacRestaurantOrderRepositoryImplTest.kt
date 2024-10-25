package data.repository

import data.datasource.SesacOrderDataSource
import data.dto.OrderDTO
import domain.model.Order
import domain.model.SesacMenu
import domain.repository.ISesacRestaurantOrderRepository
import domain.repository.ISesacRestaurantSalesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import utils.getWholePrice

class SesacRestaurantOrderRepositoryImplTest {

    companion object {
        lateinit var orderListJson: String
        lateinit var orderDtoList: List<OrderDTO>
        lateinit var sesacOrderDataSource: SesacOrderDataSource
        lateinit var repository: ISesacRestaurantOrderRepository

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

            repository = SesacRestaurantOrderRepositoryImpl(sesacOrderDataSource = sesacOrderDataSource)
        }
    }


    @Test
    fun `when new order, then returns new order`() {
        val expect = Order(
            id = 4,
            tableNumber = 55,
            orderedTime = System.currentTimeMillis(),
            paidTime = null,
            price = hashMapOf(
                SesacMenu.BIBIMBAP to 5,
                SesacMenu.DON_KATSU to 5
            ).getWholePrice(),
            paid = false,
            menus = hashMapOf(
                SesacMenu.BIBIMBAP to 5,
                SesacMenu.DON_KATSU to 5
            )
        )

        val res = repository.order(
            tableNumber = 55,
            menus = hashMapOf(
                SesacMenu.BIBIMBAP to 5,
                SesacMenu.DON_KATSU to 5
            )
        )

        kotlin.test.assertEquals(expect.id, res.id)
        kotlin.test.assertEquals(expect.tableNumber, res.tableNumber)
        kotlin.test.assertEquals(expect.paidTime, res.paidTime)
        kotlin.test.assertEquals(expect.paid, res.paid)
        kotlin.test.assertEquals(expect.price, res.price)
        kotlin.test.assertEquals(expect.menus, res.menus)
    }

    @Test
    fun `when additional order for existing, then returns added order`() {
        val expect = Order(
            id = 1,
            tableNumber = 1,
            orderedTime = System.currentTimeMillis(),
            paidTime = null,
            price = hashMapOf(
                SesacMenu.BIBIMBAP to 10,
                SesacMenu.DON_KATSU to 5,
                SesacMenu.KIMCHI_SOUP to 7,
                SesacMenu.SOONDOOBU_SOUP to 5
            ).getWholePrice(),
            paid = false,
            menus = hashMapOf(
                SesacMenu.BIBIMBAP to 10,
                SesacMenu.DON_KATSU to 5,
                SesacMenu.KIMCHI_SOUP to 7,
                SesacMenu.SOONDOOBU_SOUP to 5
            )
        )

        val res = repository.order(
            tableNumber = 1,
            menus = hashMapOf(
                SesacMenu.BIBIMBAP to 5,
                SesacMenu.DON_KATSU to 5
            )
        )

        kotlin.test.assertEquals(expect.id, res.id)
        kotlin.test.assertEquals(expect.tableNumber, res.tableNumber)
        kotlin.test.assertEquals(expect.paidTime, res.paidTime)
        kotlin.test.assertEquals(expect.paid, res.paid)
        kotlin.test.assertEquals(expect.price, res.price)
        kotlin.test.assertEquals(expect.menus, res.menus)
    }
}