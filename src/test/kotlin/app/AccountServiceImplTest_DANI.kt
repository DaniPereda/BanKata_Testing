package app

import application.AccountServiceImpl
import domain.Account
import domain.Transaction
import infra.database.AccountRepositoryInMemory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

internal class AccountServiceImplTest_DANI {

    @Test
    fun `retrieveAccount should return empty account for new account`() {

        // GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"

        // WHEN
        val result = sut.retrieveAccount(accountNumber)

        // THEN
        assertEquals(Account(accountNumber, 0, emptyList()), result)
    }

    @Test
    fun `account Properties after one deposit should return the deposit inside the object Account`(){
        //GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"
        val amount = 100

        //WHEN
        sut.deposit(accountNumber, amount)
        val result = sut.retrieveAccount(accountNumber)

        //THEN
        assertEquals(accountNumber, result.accountNumber)
        assertEquals(amount, result.currentBalance)

        var transactionResult = result.transactions[0]
        var transactionExpected = Transaction(amount, amount, LocalDateTime.now())
        compareTransaction(transactionResult, transactionExpected)

    }

    private fun compareTransaction(expected:Transaction, result:Transaction)
    {
        var transactionExpected = Transaction(expected.amount, expected.balance, expected.date.truncatedTo(ChronoUnit.MINUTES))
        var transactionResult = Transaction(result.amount, result.balance, result.date.truncatedTo(ChronoUnit.MINUTES))

        assertEquals(transactionExpected, transactionResult)
    }

    @Test
    fun `account Properties after one withdraw should return the withdraw inside the object Account`(){
        //GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"
        val amount = 100
        val negativeAmount = amount * -1

        //WHEN
        sut.withdraw(accountNumber, amount)
        val result = sut.retrieveAccount(accountNumber)

        //THEN
        assertEquals(accountNumber, result.accountNumber)
        assertEquals(negativeAmount, result.currentBalance)

        var transactionResult = result.transactions[0]
        var transactionExpected = Transaction(negativeAmount, negativeAmount, LocalDateTime.now())
        compareTransaction(transactionResult, transactionExpected)

    }

    @Test
    fun `check retrieveAccountBalance after 1 deposit and 1 withdraw and 1 deposit`(){
        //GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "new"
        val amount1 = 100
        val amount2 = 50
        val amount3 = 5
        val negativeAmount2 = amount2 * -1

        //WHEN
        sut.deposit(accountNumber, amount1)
        sut.withdraw(accountNumber, amount2)
        sut.deposit(accountNumber, amount3)
        val result = sut.retrieveAccount(accountNumber)

        //THEN
        assertEquals(result.accountNumber, accountNumber)
        assertEquals(totalBalance(amount1, negativeAmount2, amount3), result.currentBalance, )

        var transactionResult = result.transactions[0]
        var transactionExpected = Transaction(amount1, amount1, LocalDateTime.now())
        compareTransaction(transactionResult, transactionExpected)

        transactionResult = result.transactions[1]
        transactionExpected = Transaction(negativeAmount2, negativeAmount2 + amount1, LocalDateTime.now())
        compareTransaction(transactionResult, transactionExpected)

        transactionResult = result.transactions[2]
        transactionExpected = Transaction(amount3, totalBalance(amount3, negativeAmount2, amount1), LocalDateTime.now())
        compareTransaction(transactionResult, transactionExpected)


    }

    private fun totalBalance(amount1: Int, negativeAmount2: Int, amount3: Int) =
        amount1 + negativeAmount2 + amount3

    @Test
    fun `check with 2 instances of account, if accountValues are ok`(){
        //GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumberA = "accountA"
        val accountNumberB = "accountB"

        val amount1 = 100
        val amount2 = 30
        val amount3 = 5
        val negativeAmount3 = -amount3

        //WHEN
        sut.deposit(accountNumberA, amount1)
        sut.deposit(accountNumberB, amount2)
        sut.withdraw(accountNumberB, amount3)

        val resultA = sut.retrieveAccount(accountNumberA)
        val resultB = sut.retrieveAccount(accountNumberB)

        //THEN
        assertEquals(accountNumberA, resultA.accountNumber)
        assertEquals(amount1, resultA.currentBalance)

        assertEquals(accountNumberB, resultB.accountNumber)
        assertEquals(amount2 - amount3, resultB.currentBalance, )

        var transactionResult = resultA.transactions[0]
        var transactionExpected = Transaction(amount1, amount1, LocalDateTime.now())
        compareTransaction(transactionExpected, transactionResult)

        transactionResult = resultB.transactions[0]
        transactionExpected = Transaction(amount2, amount2, LocalDateTime.now())
        compareTransaction(transactionExpected, transactionResult)

        transactionResult = resultB.transactions[1]
        transactionExpected = Transaction(negativeAmount3, amount2 - amount3, LocalDateTime.now())
        compareTransaction(transactionExpected, transactionResult)
    }

    @Test
    fun `check withdraw not enough money`(){
        //GIVEN
        val sut = AccountServiceImpl(AccountRepositoryInMemory())
        val accountNumber = "333"
        val amount = 10

        //WHEN
        sut.withdraw(accountNumber, amount)
        val result = sut.retrieveAccount(accountNumber)

        //THEN
        assertEquals( Account(accountNumber, 0, emptyList()), result)



    }


    // Other things we can test
    //    1 - transactions are stored and returned correctly by retrieve account
    //    2 - operations in another account does not affect our original account
    //    3 - use of mocks
}