package domain.repository

import domain.model.Order
import domain.model.SesacMenu

interface ISesacRestaurantSalesRepository {

    /// 메뉴별 매출
    fun getSalesForMenu(menu: SesacMenu): Int

    /// 테이블 별 매출
    fun getSalesForTable(tableNumber: Int): Int

    /// 총 매출
    fun getWholeSales(): Int

    /// 미 계산 테이블 정보
    fun getUnpaidOrder(): List<Order>
}