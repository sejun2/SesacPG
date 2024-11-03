package domain.usecase

import domain.model.SesacMenu
import domain.repository.ISesacRestaurantOrderRepository

class OrderUseCase(private val sesacOrderRepository: ISesacRestaurantOrderRepository) {
    fun invoke(menus: Map<SesacMenu, Int>, tableNumber: Int) = sesacOrderRepository.order(
        menus = menus,
        tableNumber = tableNumber
    )
}