package at.kotlin.usermanager.services

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.dtos.UserDto
import at.kotlin.usermanager.entities.Account

interface IAccountService {

    fun login(loginDto: LoginDto)

    fun changePassword(passwordChangeDto: PasswordChangeDto)

    fun createAccount(accountDto: AccountDto)

    fun deleteAccount(loginDto: UserDto)

    fun findAll(): List<Account>

    fun getUserDtoByUsername(username: String): UserDto
}
