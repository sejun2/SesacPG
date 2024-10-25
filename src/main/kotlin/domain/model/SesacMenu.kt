package domain.model

enum class SesacMenu(val price: Int, val menuName: String) {
    DON_KATSU(9000, "돈까스"),
    KIMCHI_SOUP(12000, "김치찌개"),
    DENJANG_SOUP(8000, "된장찌개"),
    SOONDOOBU_SOUP(8000, "순두부찌개"),
    BIBIMBAP(8500, "비빔밥");

    companion object {
        fun getSesacMenuByMenuName(menuName: String): SesacMenu? {
            return entries.find {
                it.menuName == menuName
            }
        }
    }
}