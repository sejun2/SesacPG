package data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.datasource.SesacOrderDataSource
import data.dto.OrderDTO
import data.dto.toDomain
import domain.model.Order
import domain.model.SesacMenu
import domain.repository.ISesacRestaurantOrderRepository
import utils.getWholePrice
import java.util.HashMap
import kotlin.collections.ArrayList

class SesacRestaurantOrderRepositoryImpl(private val sesacOrderDataSource: SesacOrderDataSource) :
    ISesacRestaurantOrderRepository {
    override fun order(menus: Map<SesacMenu, Int>, tableNumber: Int): Order {
        // get all order list
        val orderListJson = sesacOrderDataSource.getOrder()

        // parsing
        val allOrderList = Gson().fromJson<ArrayList<OrderDTO>>(
            orderListJson,
            object : TypeToken<List<OrderDTO>>() {}.type
        ) ?: ArrayList<OrderDTO>()

        val existingOrder = allOrderList.lastOrNull { !it.paid && it.tableNumber == tableNumber }

        //TODO("Satoshi"): separate additional order and new order
        if (existingOrder != null) {
            val index = allOrderList.indexOf(existingOrder)
            allOrderList.removeAt(index)

            val tmpOrder = existingOrder.copy()

            menus.forEach {
                (tmpOrder.menus as HashMap).put(it.key, (it.value + tmpOrder.menus.getOrDefault(it.key, 0)))
            }

            val res = existingOrder.copy(
                menus = tmpOrder.menus,
                price = tmpOrder.menus.getWholePrice()
            )
            allOrderList.add(index, res)

            return res.toDomain()
        } else {
            val lastId = if (allOrderList.isEmpty) 0 else allOrderList.sortedBy {
                it.id
            }.last().id

            val orderDto = OrderDTO(
                id = lastId + 1,
                tableNumber = tableNumber,
                orderedTime = System.currentTimeMillis(),
                paidTime = null,
                price = menus.getWholePrice(),
                paid = false,
                menus = menus,
            )

            allOrderList.add(orderDto)

            sesacOrderDataSource.saveOrder(Gson().toJson(allOrderList))

            return orderDto.toDomain()
        }

    }
}
