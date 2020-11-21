package at.kotlin.usermanager.repositories

import at.kotlin.usermanager.entities.BannedAccount
import org.springframework.data.jpa.repository.JpaRepository

interface IBannedAccountRepository: JpaRepository<BannedAccount, String>
