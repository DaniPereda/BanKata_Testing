package domain

import java.time.LocalDateTime

data class Transaction(
    val amount: Int,
    val balance: Int,
    val date: LocalDateTime
)