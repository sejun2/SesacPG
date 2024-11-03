package domain.usecase

import domain.repository.ISesacRestaurantSalesRepository

class GetUnpaidOrderUseCase(private val salesRepository: ISesacRestaurantSalesRepository) {
    fun invoke() = salesRepository.getUnpaidOrder()
}