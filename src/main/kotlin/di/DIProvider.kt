package di

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantOrderRepositoryImpl
import data.repository.SesacRestaurantPaymentRepositoryImpl
import data.repository.SesacRestaurantSalesRepositoryImpl
import domain.repository.ISesacRestaurantOrderRepository
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository
import domain.usecase.GetSalesForMenuUseCase
import domain.usecase.GetSalesForTableUseCase
import domain.usecase.GetUnpaidOrderUseCase
import domain.usecase.GetWholeSalesUseCase
import domain.usecase.OrderUseCase
import domain.usecase.PaymentUseCase
import presentation.HomeScreen
import presentation.OrderScreen
import presentation.PaymentScreen
import presentation.SalesScreen
import presentation.viewmodel.OrderViewModel
import presentation.viewmodel.PaymentViewModel
import presentation.viewmodel.SalesViewModel

object DIProvider {
    fun provide() {
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

        SesacServiceLocator.registerSingleton<GetWholeSalesUseCase>(
            GetWholeSalesUseCase(SesacServiceLocator.get<ISesacRestaurantSalesRepository>())
        )
        SesacServiceLocator.registerSingleton<GetUnpaidOrderUseCase>(
            GetUnpaidOrderUseCase(SesacServiceLocator.get<ISesacRestaurantSalesRepository>())
        )
        SesacServiceLocator.registerSingleton<GetSalesForTableUseCase>(
            GetSalesForTableUseCase(SesacServiceLocator.get<ISesacRestaurantSalesRepository>())
        )
        SesacServiceLocator.registerSingleton<GetSalesForMenuUseCase>(
            GetSalesForMenuUseCase(SesacServiceLocator.get<ISesacRestaurantSalesRepository>())
        )
        SesacServiceLocator.registerSingleton<OrderUseCase>(
            OrderUseCase(SesacServiceLocator.get<ISesacRestaurantOrderRepository>())
        )
        SesacServiceLocator.registerSingleton<PaymentUseCase>(
            PaymentUseCase(SesacServiceLocator.get<ISesacRestaurantPaymentRepository>())
        )


        SesacServiceLocator.registerSingleton<OrderViewModel>(
            OrderViewModel(
                orderUseCase = SesacServiceLocator.get<OrderUseCase>()
            )
        )

        SesacServiceLocator.registerSingleton<PaymentViewModel>(
            PaymentViewModel(
                paymentUseCase = SesacServiceLocator.get<PaymentUseCase>(),
                getUnpaidOrderUseCase = SesacServiceLocator.get<GetUnpaidOrderUseCase>()
            )
        )
        SesacServiceLocator.registerSingleton<SalesViewModel>(
            SalesViewModel(
                getWholeSalesUseCase = SesacServiceLocator.get<GetWholeSalesUseCase>(),
                getSalesForMenuUseCase = SesacServiceLocator.get<GetSalesForMenuUseCase>(),
                getSalesForTableUseCase = SesacServiceLocator.get<GetSalesForTableUseCase>(),
                getUnpaidOrderUseCase = SesacServiceLocator.get<GetUnpaidOrderUseCase>()
            )
        )

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