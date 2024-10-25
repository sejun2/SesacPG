package presentation.viewmodel

import domain.model.SesacMenu
import domain.repository.ISesacRestaurantSalesRepository

class SalesViewModel(private val salesRepository: ISesacRestaurantSalesRepository) {

    fun getWholeSales() =
        salesRepository.getWholeSales()

    fun getSalesForMenu(sesacMenu: SesacMenu) = salesRepository.getSalesForMenu(sesacMenu)

    fun getSalesForTable(tableNumber: Int) = salesRepository.getSalesForTable(tableNumber)

    fun getUnpaidTables() = salesRepository.getUnpaidOrder()
}