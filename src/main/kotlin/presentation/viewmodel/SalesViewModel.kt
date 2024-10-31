package presentation.viewmodel

import domain.model.Order
import domain.model.SesacMenu
import domain.repository.ISesacRestaurantSalesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SalesViewModel(private val salesRepository: ISesacRestaurantSalesRepository) {

    suspend fun getWholeSales(): Int {
        return withContext(Dispatchers.IO) {
            return@withContext salesRepository.getWholeSales()
        }
    }

    suspend fun getSalesForMenu(sesacMenu: SesacMenu): Int {
        return withContext(Dispatchers.IO) {
            return@withContext salesRepository.getSalesForMenu(sesacMenu)
        }
    }

    suspend fun getSalesForTable(tableNumber: Int): Int {
        return withContext(Dispatchers.IO) {
            return@withContext salesRepository.getSalesForTable(tableNumber)
        }
    }

    suspend fun getUnpaidTables(): List<Order> {
        return withContext(Dispatchers.IO) {
            return@withContext salesRepository.getUnpaidOrder()
        }
    }
}