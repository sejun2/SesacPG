package presentation.viewmodel

import domain.model.Order
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository

class PaymentViewModel(
    private val paymentRepository: ISesacRestaurantPaymentRepository,
    private val salesRepository: ISesacRestaurantSalesRepository
) {

    suspend fun getUnpaidOrder() =
        salesRepository.getUnpaidOrder()


    suspend fun payment(order: Order) =
        paymentRepository.payment(order)

}