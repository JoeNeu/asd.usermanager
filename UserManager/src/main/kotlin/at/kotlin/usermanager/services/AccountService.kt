package at.kotlin.usermanager.services

import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.repositories.IAccountRepository
import org.springframework.stereotype.Service

@Service
class AccountService (
        val accountRepository: IAccountRepository
): IAccountService {

    fun saveAccount(account: Account): Boolean{
        accountRepository.save(account)
        return true;
    }

    override fun login(account: Account): Boolean{
        accountRepository.findByusername(account)
        return true;
    }

    fun findAll(): List<Account> {
        return accountRepository.findAll()
    }
}
