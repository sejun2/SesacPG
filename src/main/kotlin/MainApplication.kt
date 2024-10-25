import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import data.repository.SesacRestaurantPaymentRepositoryImpl
import data.repository.SesacRestaurantSalesRepositoryImpl
import domain.repository.ISesacRestaurantOrderRepository
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository
import presentation.ConsoleController

class MainApplication {
    fun run() {
        val sesacOrderDataSource = SesacOrderDataSource()

        val orderRepository: ISesacRestaurantOrderRepository =
            SesacRestaurantOrderRepositoryImpl(sesacOrderDataSource = sesacOrderDataSource)

        val paymentRepository: ISesacRestaurantPaymentRepository =
            SesacRestaurantPaymentRepositoryImpl(sesacOrderDataSource = sesacOrderDataSource)

        val salesRepository: ISesacRestaurantSalesRepository =
            SesacRestaurantSalesRepositoryImpl(sesacOrderDataSource = sesacOrderDataSource)

        ConsoleController(
            orderRepository = orderRepository,
            salesRepository = salesRepository,
            paymentRepository = paymentRepository
        ).start()
    }
}

fun main(args: Array<String>) {
    MainApplication().run()
}