package domain.usecase

import domain.model.SesacMenu
import domain.repository.ISesacRestaurantSalesRepository

class GetSalesForMenuUseCase(private val salesRepository: ISesacRestaurantSalesRepository) {
    fun invoke(sesacMenu: SesacMenu) = salesRepository.getSalesForMenu(sesacMenu)
}