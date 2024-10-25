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
