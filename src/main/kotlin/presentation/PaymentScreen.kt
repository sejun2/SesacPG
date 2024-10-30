package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantPaymentRepositoryImpl
import data.repository.SesacRestaurantSalesRepositoryImpl
import domain.model.toPrettyString
import presentation.viewmodel.PaymentViewModel

class PaymentScreen(
    private val viewModel: PaymentViewModel = PaymentViewModel(
        paymentRepository = SesacRestaurantPaymentRepositoryImpl(
            SesacOrderDataSource()
        ),
        salesRepository = SesacRestaurantSalesRepositoryImpl(SesacOrderDataSource())
    )
) : BaseScreen {

    override fun display() {
        println("결제하기")
        println("미결제 목록")
        println(viewModel.getUnpaidOrder().toPrettyString())
    }

    override fun handleInput() {
        val orderList = viewModel.getUnpaidOrder()

        if (orderList.isEmpty()) {
            println("결제할 주문이 없습니다")
            ConsoleController.currentScreen = HomeScreen()
            return
        }

        println("결제할 주문 번호: ")
        val orderId = readln().toInt()

        val selectedOrder = orderList.find {
            it.id == orderId
        }

        selectedOrder?.let {
            val res = viewModel.payment(it)
            println("결제 완료\n${res.toPrettyString()}")
        }
    }
}