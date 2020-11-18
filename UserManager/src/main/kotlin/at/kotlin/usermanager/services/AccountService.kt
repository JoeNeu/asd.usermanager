package at.kotlin.usermanager.services

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.mapper.AccountMapper
import at.kotlin.usermanager.repositories.IAccountRepository
import at.kotlin.usermanager.utils.HashUtil.hash
import org.springframework.stereotype.Service
import org.slf4j.Logger
import org.slf4j.LoggerFactory


@Service
class AccountService (
        val accountRepository: IAccountRepository,
        val accountMapper: AccountMapper
): IAccountService {

    val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)

    override fun login(account: LoginDto): Boolean{
        return passwordCorrect(account.username, account.password)
    }

    override fun createAccount(account: AccountDto) {
        if(usernameExists(account.username)) {
            throw Exception("Username already exists")
        }
        accountRepository.save(accountMapper.mapToEntityAndHashPassword(account)).also {
            logger.info("New Account created: $it")
        }
    }

    fun findAll(): List<Account>
            = accountRepository.findAll()

    fun usernameExists(username: String): Boolean
            = accountRepository.findAccountByUsername(username) != null

    fun getAccountDtoByUsername(username: String): AccountDto
            = accountMapper.mapToDto(accountRepository.findAccountByUsername(username) ?: throw NullPointerException())

    fun passwordCorrect(username: String, password: String): Boolean {
        return when (val account = accountRepository.findAccountByUsername(username)){
            null -> false
            else -> account.password == hash(password)
        }
    }

    fun changePassword(passwordChangeDto: PasswordChangeDto) {
        val account = accountRepository.findAccountByUsername(passwordChangeDto.username) ?: throw NullPointerException()
        account.password = hash(passwordChangeDto.newPassword)
        accountRepository.save(account)
    }

    fun deleteAccount(token: LoginDto) {
        accountRepository.deleteAccountByUsername(token.username)
    }
}
