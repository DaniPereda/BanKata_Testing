package app

import application.AccountServiceImpl
import domain.Account
import infra.database.AccountRepositoryInMemory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AccountServiceImplTest {

    @Test
    fun `retrieveAccount should return empty account for new account`() {

        // GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"

        // WHEN
        val result = sut.retrieveAccount(accountNumber)

        // THEN
        assertEquals(result, Account(accountNumber, 0, emptyList()))
    }

    @Test
    fun `deposit should increase account amount`() {

        // GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"

        // WHEN
        sut.deposit(accountNumber, 100)
        val result = sut.retrieveAccount(accountNumber)

        // THEN
        assertEquals(result.currentBalance, 100)
    }

    @Test
    fun `withdraw should decrease account amount`() {

        // GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"

        // WHEN
        sut.deposit(accountNumber, 100)
        sut.withdraw(accountNumber, 50)
        val result = sut.retrieveAccount(accountNumber)

        // THEN
        assertEquals(result.currentBalance, 50)
    }

    // Other things we can test
    //    1 - transactions are stored and returned correctly by retrieve account
    //    2 - operations in another account does not affect our original account
    //    3 - use of mocks
}