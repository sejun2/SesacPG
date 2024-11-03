package presentation.viewmodel

import domain.model.Order
import domain.usecase.GetUnpaidOrderUseCase
import domain.usecase.PaymentUseCase

class PaymentViewModel(
    private val paymentUseCase: PaymentUseCase,
    private val getUnpaidOrderUseCase: GetUnpaidOrderUseCase,
) {

    fun getUnpaidOrder() = getUnpaidOrderUseCase.invoke()

    fun payment(order: Order) = paymentUseCase.invoke(order)
}