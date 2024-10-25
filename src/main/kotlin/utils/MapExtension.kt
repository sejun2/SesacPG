package utils

import domain.model.SesacMenu


fun Map<SesacMenu, Int>.getWholePrice(): Int {
    var total = 0

    val iterator = this.iterator()

    while (iterator.hasNext()) {
        val key = iterator.next().key

        val count = this.getOrDefault(key, 0)
        total += count * key.price
    }

    return total
}

fun Map<SesacMenu, Int>.toPrettyString(): String {
    var res = ""

    this.entries.forEach {
        res += "메뉴이름: ${it.key.menuName}  개수: ${it.value} "
    }

    return res
}