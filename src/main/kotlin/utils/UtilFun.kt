package utils

import domain.model.SesacMenu

/**
 * When menu string input, then it converts the string as [[SesacMenu], [Int]].
 * After that, it stores in an [HashMap], and then returns the hashMap
 * If it has errors, then it returns empty hashMap
 *
 * The order string must format as
 * `메뉴이름,개수;메뉴이름,개수;`
 * Menu name and count is split as `,`
 * Each item is split as `;`
 *
 * ex) input : 순두부찌개,100;돈까스,400
 *     result: hashMap[SesacMenu.SOONDUBU_SOUP] is 100
 *     hashMap[SesacMenu.DONKATSU] is 400
 */
fun setMenus(order: String): HashMap<SesacMenu, Int> {
    try {
        val menuTable = HashMap<SesacMenu, Int>()
        val split1 = order.split(";")

        split1.forEach { orderString ->
            if (orderString.isEmpty()) {
                return@forEach
            }

            val split2 = orderString.split(",")
            val menu = SesacMenu.getSesacMenuByMenuName(split2[0])
            val count = split2[1].toInt()

            menu?.let {
                menuTable[it] = count
            }
        }

        return menuTable
    } catch (e: Exception) {
        return HashMap<SesacMenu,Int>()
    }
}