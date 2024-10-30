package di

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import presentation.HomeScreen
import java.rmi.NoSuchObjectException

class SesacServiceLocatorTest {

    @AfterEach
    fun tearDown() {
        SesacServiceLocator.clearDi()
    }

    @Test
    fun `when try to get same type instance, then the gets will be same`() {
        SesacServiceLocator.registerSingleton(HomeScreen())

        val h1 = SesacServiceLocator.get<HomeScreen>()
        val h2 = SesacServiceLocator.get<HomeScreen>()

        assertEquals(h1.hashCode(), h2.hashCode())
    }

    @Test
    fun `when try to get unregistered instance, then throws NoSuchObjectException`(){
        org.junit.jupiter.api.assertThrows<NoSuchObjectException> {
            val h1 = SesacServiceLocator.get<HomeScreen>()
        }
    }
}