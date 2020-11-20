package at.kotlin.usermanager.services

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto

interface IAccountService {

    fun login(account: LoginDto): Boolean

    fun createAccount(account: AccountDto)
}
