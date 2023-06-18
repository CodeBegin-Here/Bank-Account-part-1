// BankAccount class representing a generic bank account
open class BankAccount(val accountNumber: String, var balance: Double = 0.0) {
    open fun deposit(amount: Double) {
        balance += amount
    }

    open fun withdraw(amount: Double) {
        if (amount <= balance) {
            balance -= amount
        } else {
            println("Insufficient funds")
        }
    }

    open fun printBalance() {
        println("Account Number: $accountNumber")
        println("Balance: $balance")
    }
}

// DebitAccount class representing a debit bank account
class DebitAccount(accountNumber: String, balance: Double = 0.0) : BankAccount(accountNumber, balance) {
    override fun printBalance() {
        super.printBalance()
        println("Account Type: Debit Account")
    }
}

// CreditAccount class representing a credit bank account
class CreditAccount(accountNumber: String, balance: Double = 0.0) : BankAccount(accountNumber, balance) {
    override fun withdraw(amount: Double) {
        balance -= amount
    }

    override fun printBalance() {
        super.printBalance()
        println("Account Type: Credit Account")
    }
}

// CheckingAccount class representing a checking bank account
class CheckingAccount(accountNumber: String, balance: Double = 0.0) : BankAccount(accountNumber, balance) {
    override fun withdraw(amount: Double) {
        if (amount <= balance) {
            balance -= amount
        } else {
            println("Insufficient funds in the checking account")
        }
    }

    override fun printBalance() {
        super.printBalance()
        println("Account Type: Checking Account")
    }
}

// Bank class representing the online bank system
class Bank {
    private val accounts = mutableListOf<BankAccount>()

    fun createAccount(accountType: String, accountNumber: String): BankAccount {
        val account = when (accountType) {
            "debit" -> DebitAccount(accountNumber)
            "credit" -> CreditAccount(accountNumber)
            "checking" -> CheckingAccount(accountNumber)
            else -> throw IllegalArgumentException("Invalid account type")
        }
        accounts.add(account)
        return account
    }

    fun processOperations() {
        var choice: Int
        var accountNumber: String
        var amount: Double

        do {
            println("1. Create Account")
            println("2. Deposit")
            println("3. Withdraw")
            println("4. Print Balance")
            println("0. Exit")
            print("Enter your choice: ")
            choice = readLine()?.toIntOrNull() ?: 0

            when (choice) {
                1 -> {
                    print("Enter account number: ")
                    accountNumber = readLine() ?: ""
                    print("Enter account type (debit/credit/checking): ")
                    val accountType = readLine() ?: ""
                    createAccount(accountType, accountNumber)
                    println("Account created successfully")
                }
                2 -> {
                    print("Enter account number: ")
                    accountNumber = readLine() ?: ""
                    print("Enter amount to deposit: ")
                    amount = readLine()?.toDoubleOrNull() ?: 0.0
                    val account = findAccount(accountNumber)
                    if (account != null) {
                        account.deposit(amount)
                        println("Deposit successful")
                    } else {
                        println("Account not found")
                    }
                }
                3 -> {
                    print("Enter account number: ")
                    accountNumber = readLine() ?: ""
                    print("Enter amount to withdraw: ")
                    amount = readLine()?.toDoubleOrNull() ?: 0.0
                    val account = findAccount(accountNumber)
                    if (account != null) {
                        account.withdraw(amount)
                        println("Withdrawal successful")
                    } else {
                        println("Account not found")
                    }
                }
                4 -> {
                    print("Enter account number: ")
                    accountNumber = readLine() ?: ""
                    val account = findAccount(accountNumber)
                    if (account != null) {
                        account.printBalance()
                    } else {
                        println("Account not found")
                    }
                }
                0 -> {
                    println("Exiting bank system...")
                }
                else -> {
                    println("Invalid choice")
                }
            }
            println()
        } while (choice != 0)
    }

    private fun findAccount(accountNumber: String): BankAccount? {
        return accounts.find { it.accountNumber == accountNumber }
    }
}

// Example usage
fun main() {
    val bank = Bank()
    bank.processOperations()
}
