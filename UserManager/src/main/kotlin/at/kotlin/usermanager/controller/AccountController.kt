package at.kotlin.usermanager.controller

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.exceptions.InvalidLoginCredentialsException
import at.kotlin.usermanager.exceptions.UsernameAlreadyExistsException
import at.kotlin.usermanager.services.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/account")
class AccountController(val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestBody account: AccountDto): ResponseEntity<*> {
        return try {
            accountService.createAccount(account)
            ResponseEntity.ok().body("")

        } catch (e: UsernameAlreadyExistsException) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping
    fun deleteAccount(@RequestBody loginDto: LoginDto): ResponseEntity<*> {
        return try {
            accountService.login(loginDto)
            accountService.deleteAccount(loginDto)
            ResponseEntity.ok().body("")

        } catch (e: InvalidLoginCredentialsException) {
            ResponseEntity(e.message, HttpStatus.FORBIDDEN)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/all")
    fun findAll(): ResponseEntity<*> {
        return try {
            val accounts = accountService.findAll()
            ResponseEntity.ok().body(accounts)

        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<*> {
        return try {
            accountService.login(loginDto)
            val account = accountService.getAccountDtoByUsername(loginDto.username)
            ResponseEntity.ok().body(account)

        } catch (e: InvalidLoginCredentialsException) {
            ResponseEntity(e.message, HttpStatus.FORBIDDEN)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/password")
    fun changePassword(@RequestBody passwordChangeDto: PasswordChangeDto): ResponseEntity<*> {
        return try {
            accountService.changePassword(passwordChangeDto)
            ResponseEntity.ok().body("")

        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
