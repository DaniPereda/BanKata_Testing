package mock

import domain.Account
import domain.AccountRepository
import kotlin.test.assertTrue
import kotlin.test.fail

class AccountRepositoryMock : AccountRepository {

    private val saveInteractions: MutableList<String> = mutableListOf()
    private val findByIdMocks = mutableMapOf<String, Int>()

    override fun save(account: Account) {
        saveInteractions.add(account.toMock())
    }

    fun verifySave(account: Account) {
        assertTrue { saveInteractions.contains(account.toMock()) }
    }

    override fun findById(accountNumber: String): Account {
        val value = findByIdMocks[accountNumber]
        if (value == null) fail("account $accountNumber not exists or is not mocked")
        else return Account(accountNumber, value, emptyList())
    }

    fun mockFindById(accountNumber: String, amount: Int) {
        findByIdMocks[accountNumber] = amount
    }

    fun clearMocks() {
        saveInteractions.clear()
        findByIdMocks.clear()
    }

    private fun Account.toMock() = "${this.accountNumber}-${this.currentBalance}"

}