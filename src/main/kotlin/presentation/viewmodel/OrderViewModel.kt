package presentation.viewmodel

import domain.model.Order
import domain.model.SesacMenu
import domain.usecase.OrderUseCase

class OrderViewModel(private val orderUseCase: OrderUseCase) {

    fun order(menus: HashMap<SesacMenu, Int>, tableNumber: Int): Order {
        return orderUseCase.invoke(menus, tableNumber)
    }
}