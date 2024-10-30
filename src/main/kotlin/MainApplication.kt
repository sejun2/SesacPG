import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import data.repository.SesacRestaurantPaymentRepositoryImpl
import data.repository.SesacRestaurantSalesRepositoryImpl
import di.SesacServiceLocator
import domain.repository.ISesacRestaurantOrderRepository
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository
import presentation.*
import presentation.viewmodel.OrderViewModel
import presentation.viewmodel.PaymentViewModel
import presentation.viewmodel.SalesViewModel

class MainApplication {
    fun run() {
        SesacServiceLocator.setSingleton<SesacOrderDataSource>(SesacOrderDataSource())

        SesacServiceLocator.setSingleton<ISesacRestaurantSalesRepository>(
            SesacRestaurantSalesRepositoryImpl(
                SesacServiceLocator.get<SesacOrderDataSource>()
            )
        )
        SesacServiceLocator.setSingleton<ISesacRestaurantPaymentRepository>(
            SesacRestaurantPaymentRepositoryImpl(
                SesacServiceLocator.get<SesacOrderDataSource>()
            )
        )
        SesacServiceLocator.setSingleton<ISesacRestaurantOrderRepository>(
            SesacRestaurantOrderRepositoryImpl(
                SesacServiceLocator.get<SesacOrderDataSource>()
            )
        )

        SesacServiceLocator.setSingleton<OrderViewModel>(OrderViewModel(orderRepository = SesacServiceLocator.get<ISesacRestaurantOrderRepository>()))
        SesacServiceLocator.setSingleton<PaymentViewModel>(
            PaymentViewModel(
                salesRepository = SesacServiceLocator.get<ISesacRestaurantSalesRepository>(),
                paymentRepository = SesacServiceLocator.get<ISesacRestaurantPaymentRepository>()
            )
        )
        SesacServiceLocator.setSingleton<SalesViewModel>(SalesViewModel(salesRepository = SesacServiceLocator.get<ISesacRestaurantSalesRepository>()))

        SesacServiceLocator.setSingleton<HomeScreen>(HomeScreen())
        SesacServiceLocator.setSingleton<OrderScreen>(
            OrderScreen(
                orderViewModel = SesacServiceLocator.get<OrderViewModel>()
            )
        )
        SesacServiceLocator.setSingleton<PaymentScreen>(
            PaymentScreen(
                viewModel = SesacServiceLocator.get<PaymentViewModel>()
            )
        )
        SesacServiceLocator.setSingleton<SalesScreen>(
            SalesScreen(
                salesViewModel = SesacServiceLocator.get<SalesViewModel>()
            )
        )

        ConsoleController().start()
    }
}

fun main(args: Array<String>) {
    MainApplication().run()
}