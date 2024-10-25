package domain.model

import data.dto.OrderDTO
import utils.toPrettyString

data class Order(
    val id: Int,
    val tableNumber: Int,
    val orderedTime: Long,
    val paidTime: Long?,
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

fun Order.toPrettyString(): String {
    return """
        테이블번호: ${this.tableNumber}
        메뉴: ${this.menus.toPrettyString()}
        가격: ${this.price}
        주문시간: ${this.orderedTime}
        결재여부: ${this.paid}
    """.trimIndent()
}

fun List<Order>.toPrettyString(): String {
    var res = ""
    this.forEach {
        res += it.toPrettyString() + "\n"
    }

    return res
}
