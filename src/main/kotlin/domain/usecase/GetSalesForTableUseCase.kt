package domain.usecase

import domain.repository.ISesacRestaurantSalesRepository

class GetSalesForTableUseCase(private val salesRepository: ISesacRestaurantSalesRepository) {
    fun invoke(tableNumber: Int) = salesRepository.getSalesForTable(tableNumber)
}