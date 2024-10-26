import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import data.dto.OrderDTO
import domain.model.SesacMenu
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GsonTest {

    @Test
    @Disabled("No need to test anymore")
    fun toJsonTest() {
        val orderDtoList = listOf(
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

        val expect = """
            [{"id":3,"tableNumber":5,"orderedTime":123123126,
            "paidTime":1111311,"price":163500,"paid":true,"menus":{"BIBIMBAP":3,"DON_KATSU":10,"DENJANG_SOUP":6}},
            {"id":2,"tableNumber":2,"orderedTime":123123126,"paidTime":1111311,
            "price":138000,"paid":true,"menus":{"DON_KATSU":10,"DENJANG_SOUP":6}},
            {"id":1,"tableNumber":1,"orderedTime":123123123,"price":166500,
            "paid":false,"menus":{"BIBIMBAP":5,"KIMCHI_SOUP":7,"SOONDOOBU_SOUP":5}}]
        """.trimIndent()

        val res = Gson().toJson(orderDtoList)

        println(res)

        assertEquals(res, expect)
    }

    @Test
    @Disabled("No need to test anymore")
    fun fromJsonTest() {
        val jsonString = """
            [{"id":2,"tableNumber":2,"orderedTime":123123126,"paidTime":1111311,"price":6000,"paid":false,"menus":{"DON_KATSU":10}},{"id":1,"tableNumber":1,"orderedTime":123123123,"paidTime":11111,"price":8000,"paid":false,"menus":{"BIBIMBAP":5}}]
        """.trimIndent()

        val expect = listOf(
            OrderDTO(
                id = 2,
                tableNumber = 2,
                orderedTime = 123123126,
                paid = false,
                menus = hashMapOf(
                    SesacMenu.DON_KATSU to 10
                ),
                paidTime = 1111311,
                price = 6000
            ),
            OrderDTO(
                id = 1,
                tableNumber = 1,
                orderedTime = 123123123,
                paid = false,
                menus = hashMapOf(
                    SesacMenu.BIBIMBAP to 5
                ),
                paidTime = 11111,
                price = 8000
            )
        )

        val res = Gson().fromJson<ArrayList<OrderDTO>>(
            jsonString,
            object : TypeToken<List<OrderDTO>>() {}.type
        )

        assertEquals(res, expect)
    }
}