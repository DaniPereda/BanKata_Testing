package application

import domain.Account
import domain.AccountRepository
import kotlin.math.abs

class AccountServiceImpl(private val accountRepository: AccountRepository) : AccountService {

    override fun deposit(accountNumber: String, amount: Int) {
        val account = accountRepository.findById(accountNumber)
        val updatedAccount = account.operate(abs(amount))
        accountRepository.save(updatedAccount)
    }

    override fun withdraw(accountNumber: String, amount: Int) {
        val account = accountRepository.findById(accountNumber)
        if(EnoughBalance(amount, account)) {
            val updatedAccount = account.operate(-abs(amount))
            accountRepository.save(updatedAccount)
        }
        else
        {
            accountRepository.save(account)

        }


    }

    private fun EnoughBalance(amount: Int, account: Account) = amount <= account.currentBalance

    override fun retrieveAccount(accountNumber: String): Account {
        return accountRepository.findById(accountNumber)
    }
}