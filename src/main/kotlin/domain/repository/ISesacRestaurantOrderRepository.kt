package domain.repository

import domain.model.Order
import domain.model.SesacMenu

interface ISesacRestaurantOrderRepository {

    /// 주문하기
    fun order(menus: Map<SesacMenu, Int>, tableNumber: Int): Order
}