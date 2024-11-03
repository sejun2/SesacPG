package domain.usecase

import domain.model.Order
import domain.repository.ISesacRestaurantPaymentRepository

class PaymentUseCase(private val paymentRepository: ISesacRestaurantPaymentRepository) {
    fun invoke(order: Order) = paymentRepository.payment(order)
}