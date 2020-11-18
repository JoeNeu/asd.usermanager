package at.kotlin.usermanager.repositories

import at.kotlin.usermanager.entities.Account
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(
        val database: IAccountRepository)
{
    fun add(account: Account) {
        database.save(account)
    }
}
