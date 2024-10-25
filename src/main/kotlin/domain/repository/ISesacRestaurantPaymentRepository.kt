package domain.repository

import domain.model.Order

interface ISesacRestaurantPaymentRepository {

    /// 계산하기
    suspend fun payment(order: Order): Order
}