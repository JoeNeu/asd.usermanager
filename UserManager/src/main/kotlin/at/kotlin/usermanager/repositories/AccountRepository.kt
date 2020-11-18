package at.kotlin.usermanager.repositories

import at.kotlin.usermanager.entities.Account
import org.springframework.data.repository.CrudRepository
import java.util.*

interface AccountRepository : CrudRepository<Account, String> {

    fun findByuuid(address: String): Optional<Account>
}
