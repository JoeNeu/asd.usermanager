package at.kotlin.usermanager.services

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.dtos.UserDto
import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.entities.BannedAccount
import at.kotlin.usermanager.exceptions.BannedAccountException
import at.kotlin.usermanager.exceptions.InvalidLoginCredentialsException
import at.kotlin.usermanager.exceptions.UsernameAlreadyExistsException
import at.kotlin.usermanager.mapper.AccountMapper
import at.kotlin.usermanager.repositories.IAccountRepository
import at.kotlin.usermanager.repositories.IBannedAccountRepository
import at.kotlin.usermanager.utils.HashUtil.hash
import at.kotlin.usermanager.utils.Unwrap.unwrapBannedAccount
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
        val accountRepository: IAccountRepository,
        val bannedAccountRepository: IBannedAccountRepository,
        val accountMapper: AccountMapper
) : IAccountService {

    val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)

    override fun login(loginDto: LoginDto) {
        if (accountBanned(loginDto.username) || !passwordCorrect(loginDto.username, loginDto.password)) {
            incrementLoginTries(loginDto.username)
            throw InvalidLoginCredentialsException()
        } else {
            resetLoginTries(loginDto.username)
        }
    }

    private fun resetLoginTries(username: String) {
        bannedAccountRepository.findById(username).unwrapBannedAccount()
                ?.let { account: BannedAccount -> {
                    bannedAccountRepository.delete(account)
                }}
    }

    private fun accountBanned(username: String): Boolean {
        if(bannedAccountRepository.findById(username).unwrapBannedAccount()
                ?.let { account: BannedAccount -> account.tries >= 3 } == true) {
            throw BannedAccountException()
        }
        return false
    }

    private fun incrementLoginTries(username: String) {
        when (bannedAccountRepository.findById(username).isPresent) {
            false -> {
                val bannedAccount = BannedAccount(username, 1)
                bannedAccountRepository.save(bannedAccount)
            }
            true -> {
                val bannedAccount: BannedAccount? = bannedAccountRepository.findById(username).unwrapBannedAccount()
                bannedAccount!!.tries += 1
                bannedAccountRepository.save(bannedAccount!!)
            }
        }
    }

    fun passwordCorrect(username: String, password: String): Boolean {
        return when (val account = accountRepository.findAccountByUsername(username)) {
            null -> false
            else -> account.password == hash(password)
        }
    }


    @Transactional
    override fun changePassword(passwordChangeDto: PasswordChangeDto) {
        val account = accountRepository.findAccountByUsername(passwordChangeDto.username)
                ?: throw NullPointerException()

        account.password = hash(passwordChangeDto.newPassword)
        accountRepository.save(account).also {
            logger.info("Password changed successfully: $it")
        }
    }

    @Transactional
    override fun createAccount(accountDto: AccountDto) {
        if (usernameAlreadyExists(accountDto.username)) {
            throw UsernameAlreadyExistsException()
        }

        val entity = accountMapper.mapToEntityAndHashPassword(accountDto)
        accountRepository.save(entity).also {
            logger.info("New Account created: $it")
        }
    }

    fun usernameAlreadyExists(username: String): Boolean = accountRepository.findAccountByUsername(username) != null

    @Transactional
    override fun deleteAccount(userDto: UserDto) {
        accountRepository.deleteAccountByUsername(userDto.username).also {
            logger.info("Deleted Account with Username: ${userDto.username}")
        }
    }

    override fun findAll(): List<Account> = accountRepository.findAll()

    override fun getUserDtoByUsername(username: String): UserDto = accountMapper.mapToDto(accountRepository.findAccountByUsername(username)
            ?: throw NullPointerException())
}
