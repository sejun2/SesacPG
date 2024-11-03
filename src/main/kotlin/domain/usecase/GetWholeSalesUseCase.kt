package domain.usecase

import domain.repository.ISesacRestaurantSalesRepository

class GetWholeSalesUseCase(private val salesRepository: ISesacRestaurantSalesRepository) {
    fun invoke() = salesRepository.getWholeSales()
}