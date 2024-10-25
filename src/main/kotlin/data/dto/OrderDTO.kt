package data.dto

import domain.model.Order
import domain.model.SesacMenu

data class OrderDTO(
    val id: Int,
    val tableNumber: Int,
    val orderedTime: Int,
    val paidTime: Int?,
    val price: Int,
    val paid: Boolean,
    val menus: Map<SesacMenu, Int>
)

fun OrderDTO.toDomain(): Order {
    return Order(
        id = this.id,
        tableNumber = this.tableNumber,
        orderedTime = this.orderedTime,
        paidTime = this.paidTime,
        price = this.price,
        paid = this.paid,
        menus = this.menus
    )
}

