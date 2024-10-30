package di

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import data.repository.SesacRestaurantPaymentRepositoryImpl
import data.repository.SesacRestaurantSalesRepositoryImpl
import domain.repository.ISesacRestaurantOrderRepository
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository
import presentation.HomeScreen
import presentation.OrderScreen
import presentation.PaymentScreen
import presentation.SalesScreen
import presentation.viewmodel.OrderViewModel
import presentation.viewmodel.PaymentViewModel
import presentation.viewmodel.SalesViewModel

object DIProvider {
    fun provide(){
        SesacServiceLocator.registerSingleton<SesacOrderDataSource>(SesacOrderDataSource())

        SesacServiceLocator.registerSingleton<ISesacRestaurantSalesRepository>(
            SesacRestaurantSalesRepositoryImpl(
                SesacServiceLocator.get<SesacOrderDataSource>()
            )
        )
        SesacServiceLocator.registerSingleton<ISesacRestaurantPaymentRepository>(
            SesacRestaurantPaymentRepositoryImpl(
                SesacServiceLocator.get<SesacOrderDataSource>()
            )
        )
        SesacServiceLocator.registerSingleton<ISesacRestaurantOrderRepository>(
            SesacRestaurantOrderRepositoryImpl(
                SesacServiceLocator.get<SesacOrderDataSource>()
            )
        )

        SesacServiceLocator.registerSingleton<OrderViewModel>(OrderViewModel(orderRepository = SesacServiceLocator.get<ISesacRestaurantOrderRepository>()))
        SesacServiceLocator.registerSingleton<PaymentViewModel>(
            PaymentViewModel(
                salesRepository = SesacServiceLocator.get<ISesacRestaurantSalesRepository>(),
                paymentRepository = SesacServiceLocator.get<ISesacRestaurantPaymentRepository>()
            )
        )
        SesacServiceLocator.registerSingleton<SalesViewModel>(SalesViewModel(salesRepository = SesacServiceLocator.get<ISesacRestaurantSalesRepository>()))

        SesacServiceLocator.registerSingleton<HomeScreen>(HomeScreen())
        SesacServiceLocator.registerSingleton<OrderScreen>(
            OrderScreen(
                orderViewModel = SesacServiceLocator.get<OrderViewModel>()
            )
        )
        SesacServiceLocator.registerSingleton<PaymentScreen>(
            PaymentScreen(
                viewModel = SesacServiceLocator.get<PaymentViewModel>()
            )
        )
        SesacServiceLocator.registerSingleton<SalesScreen>(
            SalesScreen(
                salesViewModel = SesacServiceLocator.get<SalesViewModel>()
            )
        )
    }
}