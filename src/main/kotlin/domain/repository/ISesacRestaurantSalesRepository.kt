package domain.repository

import domain.model.Order
import domain.model.SesacMenu

interface ISesacRestaurantSalesRepository {

    /// 메뉴별 매출
    suspend fun getSalesForMenu(menu: SesacMenu): Int

    /// 테이블 별 매출
    suspend fun getSalesForTable(tableNumber: Int): Int

    /// 총 매출
    suspend fun getWholeSales(): Int

    /// 미 계산 테이블 정보
    suspend fun getUnpaidOrder(): List<Order>
}