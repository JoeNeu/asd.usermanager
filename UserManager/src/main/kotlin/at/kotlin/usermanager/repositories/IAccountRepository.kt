package at.kotlin.usermanager.repositories

import at.kotlin.usermanager.entities.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IAccountRepository : JpaRepository<Account, UUID> {

    fun findAccountByUsername(username: String): Account?

    fun deleteAccountByUsername(username: String)
}
