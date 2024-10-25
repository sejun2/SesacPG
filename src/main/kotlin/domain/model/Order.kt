package domain.model

import data.dto.OrderDTO

data class Order(
    val id: Int,
    val tableNumber: Int,
    val orderedTime: Int,
    val paidTime: Int?,
    val price: Int,
    val paid: Boolean,
    val menus: Map<SesacMenu, Int>
)

fun Order.toDto(): OrderDTO {
    return OrderDTO(
        id = this.id,
        tableNumber = this.tableNumber,
        orderedTime = this.orderedTime,
        paidTime = this.paidTime,
        price = this.price,
        paid = this.paid,
        menus = this.menus
    )

}

