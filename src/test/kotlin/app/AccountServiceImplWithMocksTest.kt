package app

import application.AccountServiceImpl
import domain.Account
import mock.AccountRepositoryMock
import org.junit.jupiter.api.Test

internal class AccountServiceImplWithMocksTest {

    private val mock = AccountRepositoryMock()
    private val sut = AccountServiceImpl(mock)

    @Test
    fun `deposit should increase account amount`() {

        // GIVEN
        mock.clearMocks()

        val accountNumber = "new"
        mock.mockFindById(accountNumber, 0)

        // WHEN
        sut.deposit(accountNumber, 100)

        // THEN
        mock.verifySave(Account(accountNumber, 100, emptyList()))
    }

    @Test
    fun `withdraw should decrease account amount`() {

        // GIVEN
        mock.clearMocks()

        val accountNumber = "new"
        mock.mockFindById(accountNumber, 100)

        // WHEN
        sut.withdraw(accountNumber, 50)

        // THEN
        mock.verifySave(Account(accountNumber, 50, emptyList()))
    }

}