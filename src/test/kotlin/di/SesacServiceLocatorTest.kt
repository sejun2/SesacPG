package di

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import presentation.HomeScreen
import java.rmi.NoSuchObjectException

class SesacServiceLocatorTest {

    @AfterEach
    fun tearDown() {
        SesacServiceLocator.clearDi()
    }

    @Test
    fun `given default named singleton instances, when try to get same type instance, then the gets will be same`() {
        SesacServiceLocator.registerSingleton(HomeScreen())

        val h1 = SesacServiceLocator.get<HomeScreen>()
        val h2 = SesacServiceLocator.get<HomeScreen>()

        assertEquals(h1.hashCode(), h2.hashCode())
    }

    @Test
    fun `given custom named singleton instances, when try to get same named instance, then the gets will be same`() {
        SesacServiceLocator.registerSingleton(HomeScreen(), "home")

        val h1 = SesacServiceLocator.get<HomeScreen>("home")
        val h2 = SesacServiceLocator.get<HomeScreen>("home")

        assertEquals(h1.hashCode(), h2.hashCode())
    }

    @Test
    fun `given custom named singleton instances, when try to get same type instance without name param, then throws NoSuchObjectException`() {
        // register named singleton instance
        SesacServiceLocator.registerSingleton(HomeScreen(), "home")

        assertThrows<NoSuchObjectException> {
            // belows try to get default named instances
            val h1 = SesacServiceLocator.get<HomeScreen>()
        }
    }

    @Test
    fun `when try to get unregistered instance, then throws NoSuchObjectException`() {
        org.junit.jupiter.api.assertThrows<NoSuchObjectException> {
            val h1 = SesacServiceLocator.get<HomeScreen>()
        }
    }
}