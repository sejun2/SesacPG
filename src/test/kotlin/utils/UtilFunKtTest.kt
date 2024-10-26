package utils

import domain.model.SesacMenu
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UtilFunKtTest {

    @Test
    fun `setMenu happy path test1`() {
        val correctOrderString = "순두부찌개,500;"
        val res = setMenus(correctOrderString)

        assertEquals(res[SesacMenu.SOONDOOBU_SOUP], 500)
    }

    @Test
    fun `setMenu happy path test2`() {
        val correctOrderString = "순두부찌개,500;돈까스,300;된장찌개,700;"
        val res = setMenus(correctOrderString)

        assertEquals(res[SesacMenu.SOONDOOBU_SOUP], 500)
        assertEquals(res[SesacMenu.DENJANG_SOUP], 700)
        assertEquals(res[SesacMenu.DON_KATSU], 300)
    }

    @Test
    fun `setMenu happy path test3`() {
        val correctOrderString = "순두부찌개,500;돈까스,300;된장찌개,700"
        val res = setMenus(correctOrderString)

        assertEquals(res[SesacMenu.SOONDOOBU_SOUP], 500)
        assertEquals(res[SesacMenu.DENJANG_SOUP], 700)
        assertEquals(res[SesacMenu.DON_KATSU], 300)
    }

    @Test
    fun `setMenu sad path test1`() {
        val wrongOrderString = "괴물낙지찜,100;"// no such menu in [SesacMenu]

        val res = setMenus(wrongOrderString)

        assert(res.isEmpty())
    }

    @Test
    fun `setMenu sad path test2`() {
        val wrongOrderString = "순두부찌개,500;된장찌개.100;"// comma is replace as dot

        val res = setMenus(wrongOrderString)

        assert(res.isEmpty())
    }
}