package data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.datasource.SesacOrderDataSource
import data.dto.OrderDTO
import data.dto.toDomain
import domain.model.Order
import domain.model.toDto
import domain.repository.ISesacRestaurantPaymentRepository

class SesacRestaurantPaymentRepositoryImpl(private val sesacOrderDataSource: SesacOrderDataSource) :
    ISesacRestaurantPaymentRepository {

    override fun payment(order: Order): Order {
        val orderDto = order.toDto()

        // get all order list
        val orderListJson = sesacOrderDataSource.getOrder()

        // parsing
        val allOrderList = Gson().fromJson<ArrayList<OrderDTO>>(
            orderListJson,
            object : TypeToken<List<OrderDTO>>() {}.type
        )

        val index = allOrderList.indexOfLast {
            it.id == orderDto.id
        }

        val paidOrderDto = orderDto.copy(
            paid = true,
            paidTime = System.currentTimeMillis(),
        )

        allOrderList.removeAt(index)
        allOrderList.add(index, paidOrderDto)

        sesacOrderDataSource.saveOrder(Gson().toJson(allOrderList))

        println(allOrderList)

        return paidOrderDto.toDomain()
    }
}