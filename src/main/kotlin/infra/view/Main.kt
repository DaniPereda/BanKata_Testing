package infra.view

import application.AccountServiceImpl
import infra.database.AccountRepositoryInMemory

fun main(args: Array<String>) {

    // Main class
    // It should create an accountService, do all the operations needed, and print the statement on console
    // Note that the accountService needs a transactionRepository implementation, so it should also be created here!

    val transactionRepository = AccountRepositoryInMemory()
    val accountService = AccountServiceImpl(transactionRepository)

    val accountNumber = "Dani"

    accountService.deposit(accountNumber, 1000)
    accountService.withdraw(accountNumber, 100)
    accountService.deposit(accountNumber, 400)

    val account = accountService.retrieveAccount(accountNumber)

    println("STATEMENT FOR ACCOUNT $accountNumber")
    for (transaction in account.transactions) {
        println("${transaction.date} | ${transaction.amount} | ${transaction.balance}")
    }

}
