package presentation.viewmodel

import domain.model.Order
import domain.repository.ISesacRestaurantPaymentRepository
import domain.repository.ISesacRestaurantSalesRepository

class PaymentViewModel(
    private val paymentRepository: ISesacRestaurantPaymentRepository,
    private val salesRepository: ISesacRestaurantSalesRepository
) {

    fun getUnpaidOrder() =
        salesRepository.getUnpaidOrder()


    fun payment(order: Order) =
        paymentRepository.payment(order)

}