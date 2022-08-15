package domain

import java.time.LocalDateTime

data class Account(
    val accountNumber: String,
    val currentBalance: Int,
    val transactions: List<Transaction>
) {

    fun operate(amount: Int): Account = this.copy(
        currentBalance = currentBalance + amount,
        transactions = transactions + createTransaction(amount)
    )

    private fun createTransaction(amount: Int) = Transaction(amount, currentBalance + amount, LocalDateTime.now())

}
