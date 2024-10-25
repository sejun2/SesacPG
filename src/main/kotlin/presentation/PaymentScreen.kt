package presentation

import data.datasource.SesacOrderDataSource
import data.repository.SesacRestaurantPaymentRepositoryImpl
import data.repository.SesacRestaurantSalesRepositoryImpl
import domain.model.toPrettyString
import presentation.viewmodel.PaymentViewModel

class PaymentScreen : BaseScreen {
    private val viewModel: PaymentViewModel = PaymentViewModel(
        paymentRepository = SesacRestaurantPaymentRepositoryImpl(
            SesacOrderDataSource()
        ),
        salesRepository = SesacRestaurantSalesRepositoryImpl(SesacOrderDataSource())
    )

    override fun display() {
        println("결제하기")
        println("미결제 목록")
        println(viewModel.getUnpaidOrder().toPrettyString())
    }

    override fun handleInput(): BaseScreen? {
        val orderList = viewModel.getUnpaidOrder()

        if(orderList.isEmpty()){
            println("결제할 주문이 없습니다")
            return HomeScreen()
        }

        println("결제할 주문 번호: ")
        val orderId = readln().toInt()

        val selectedOrder = orderList.find {
            it.id == orderId
        }

        selectedOrder?.let {
            val res = viewModel.payment(it)
        }

        return null
    }
}