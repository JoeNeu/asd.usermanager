package at.kotlin.usermanager.services

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.dtos.UserDto
import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.exceptions.InvalidLoginCredentialsException
import at.kotlin.usermanager.exceptions.UsernameAlreadyExistsException
import at.kotlin.usermanager.mapper.AccountMapper
import at.kotlin.usermanager.repositories.IAccountRepository
import at.kotlin.usermanager.utils.HashUtil.hash
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class AccountService(
        val accountRepository: IAccountRepository,
        val accountMapper: AccountMapper
) : IAccountService {

    val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)

    override fun login(loginDto: LoginDto) {
        if (!passwordCorrect(loginDto.username, loginDto.password)) {
            throw InvalidLoginCredentialsException()
        }
    }

    fun passwordCorrect(username: String, password: String): Boolean {
        return when (val account = accountRepository.findAccountByUsername(username)) {
            null -> false
            else -> account.password == hash(password)
        }
    }

    override fun changePassword(passwordChangeDto: PasswordChangeDto) {
        val account = accountRepository.findAccountByUsername(passwordChangeDto.username)
                ?: throw NullPointerException()

        account.password = hash(passwordChangeDto.newPassword)
        accountRepository.save(account).also {
            logger.info("Password changed successfully: $it")
        }
    }

    override fun createAccount(accountDto: AccountDto) {
        if (usernameAlreadyExists(accountDto.username)) {
            throw UsernameAlreadyExistsException()
        }

        val entity = accountMapper.mapToEntityAndHashPassword(accountDto)
        accountRepository.save(entity).also {
            logger.info("New Account created: $it")
        }
    }

    fun usernameAlreadyExists(username: String): Boolean
            = accountRepository.findAccountByUsername(username) != null

    override fun deleteAccount(loginDto: LoginDto) {
        accountRepository.deleteAccountByUsername(loginDto.username).also {
            logger.info("Deleted Account with Username: ${loginDto.username}")
        }
    }

    override fun findAll(): List<Account>
            = accountRepository.findAll()

    override fun getUserDtoByUsername(username: String): UserDto
            = accountMapper.mapToDto(accountRepository.findAccountByUsername(username)
            ?: throw NullPointerException())
}
