package application

import domain.Account

interface AccountService {

    fun deposit(accountNumber: String, amount: Int)
    fun withdraw(accountNumber: String, amount: Int)
    fun retrieveAccount(accountNumber: String): Account
}