package domain.repository

import domain.model.Order

interface ISesacRestaurantPaymentRepository {

    /// 계산하기
    fun payment(order: Order): Order
}