package at.kotlin.usermanager.services

import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.repositories.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService (
        val accountRepository: AccountRepository
): IAccountService {

    override fun login(account: Account): Boolean{
        accountRepository.add(account)
        return true;
    }
}
