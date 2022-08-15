package domain

interface AccountRepository {

    fun save(account: Account)
    fun findById(accountNumber: String): Account

}