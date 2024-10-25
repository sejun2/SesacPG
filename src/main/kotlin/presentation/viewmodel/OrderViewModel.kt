package presentation.viewmodel

import domain.model.Order
import domain.model.SesacMenu
import domain.repository.ISesacRestaurantOrderRepository

class OrderViewModel(private val orderRepository: ISesacRestaurantOrderRepository) {

    fun order(menus: HashMap<SesacMenu, Int>, tableNumber: Int): Order {
        return orderRepository.order(menus, tableNumber)
    }
}