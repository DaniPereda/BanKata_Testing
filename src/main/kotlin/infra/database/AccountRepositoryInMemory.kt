package infra.database

import domain.Account
import domain.AccountRepository

class AccountRepositoryInMemory : AccountRepository {

    private val database: MutableMap<String, Account> = mutableMapOf()

    override fun save(account: Account) {
        database[account.accountNumber] = account
    }

    override fun findById(accountNumber: String): Account =
        database.getOrDefault(
            key = accountNumber,
            defaultValue = Account(accountNumber, 0, emptyList())
        )
}