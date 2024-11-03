package presentation.viewmodel

import domain.model.SesacMenu
import domain.usecase.GetSalesForMenuUseCase
import domain.usecase.GetSalesForTableUseCase
import domain.usecase.GetUnpaidOrderUseCase
import domain.usecase.GetWholeSalesUseCase

class SalesViewModel(
    private val getWholeSalesUseCase: GetWholeSalesUseCase,
    private val getSalesForMenuUseCase: GetSalesForMenuUseCase,
    private val getSalesForTableUseCase: GetSalesForTableUseCase,
    private val getUnpaidOrderUseCase: GetUnpaidOrderUseCase,
) {

    fun getWholeSales() = getWholeSalesUseCase.invoke()

    fun getSalesForMenu(sesacMenu: SesacMenu) = getSalesForMenuUseCase.invoke(sesacMenu)

    fun getSalesForTable(tableNumber: Int) = getSalesForTableUseCase.invoke(tableNumber)

    fun getUnpaidTables() = getUnpaidOrderUseCase.invoke()
}