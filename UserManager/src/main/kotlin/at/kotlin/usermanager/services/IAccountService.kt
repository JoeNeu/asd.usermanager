package at.kotlin.usermanager.services

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.entities.Account

interface IAccountService {

    fun login(account: LoginDto)

    fun changePassword(passwordChangeDto: PasswordChangeDto)

    fun createAccount(account: AccountDto)

    fun deleteAccount(token: LoginDto)

    fun findAll(): List<Account>

    fun getAccountDtoByUsername(username: String): AccountDto
}
