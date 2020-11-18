package at.kotlin.usermanager.services

import at.kotlin.usermanager.entities.Account

interface IAccountService {

    fun login(account: Account): Boolean
}
