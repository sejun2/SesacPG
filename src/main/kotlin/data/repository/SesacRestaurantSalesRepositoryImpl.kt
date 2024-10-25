package data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import data.datasource.SesacOrderDataSource
import data.dto.OrderDTO
import data.dto.toDomain
import domain.model.Order
import domain.model.SesacMenu
import domain.repository.ISesacRestaurantSalesRepository

class SesacRestaurantSalesRepositoryImpl(private val sesacOrderDataSource: SesacOrderDataSource) :
    ISesacRestaurantSalesRepository {
    override suspend fun getSalesForMenu(menu: SesacMenu): Int {
        var total = 0

        // get all order list
        val orderListJson = sesacOrderDataSource.getOrder()

        // parsing
        val allOrderList = Gson().fromJson<ArrayList<OrderDTO>>(
            orderListJson,
            object : TypeToken<List<OrderDTO>>() {}.type
        )

        val res = allOrderList.filter {
            it.menus.containsKey(menu)
        }

        res.forEach {
            total += it.menus.getOrDefault(menu, 0) * menu.price
        }

        return total
    }

    override suspend fun getSalesForTable(tableNumber: Int): Int {
        var total = 0

        // get all order list
        val orderListJson = sesacOrderDataSource.getOrder()

        // parsing
        val allOrderList = Gson().fromJson<ArrayList<OrderDTO>>(
            orderListJson,
            object : TypeToken<List<OrderDTO>>() {}.type
        )

        val res = allOrderList.filter {
            it.tableNumber == tableNumber
        }

        res.forEach {
            total += it.price
        }

        return total
    }

    override suspend fun getWholeSales(): Int {
        var total = 0

        // get all order list
        val orderListJson = sesacOrderDataSource.getOrder()

        // parsing
        val allOrderList = Gson().fromJson<ArrayList<OrderDTO>>(
            orderListJson,
            object : TypeToken<List<OrderDTO>>() {}.type
        )

        allOrderList.forEach {
            total += it.price
        }

        return total
    }

    override suspend fun getUnpaidOrder(): List<Order> {
        var total = 0

        // get all order list
        val orderListJson = sesacOrderDataSource.getOrder()

        // parsing
        val allOrderList = Gson().fromJson<ArrayList<OrderDTO>>(
            orderListJson,
            object : TypeToken<List<OrderDTO>>() {}.type
        )

        val res = allOrderList.filter {
            !it.paid
        }


        return res.map {
            it.toDomain()
        }
    }
}