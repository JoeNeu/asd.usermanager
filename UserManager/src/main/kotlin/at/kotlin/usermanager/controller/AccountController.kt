package at.kotlin.usermanager.controller

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.LoginDto
import at.kotlin.usermanager.dtos.PasswordChangeDto
import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.services.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/account")
class AccountController(val accountService: AccountService) {

    @PostMapping
    fun createAccount(@RequestBody account: AccountDto): ResponseEntity<*> {
        return try {
            accountService.createAccount(account)
            ResponseEntity.ok().body("")
        } catch (e: Exception) {
            ResponseEntity("Username already exists", HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping
    fun deleteAccount(@RequestBody loginDto: LoginDto): ResponseEntity<*> {
        if (accountService.login(loginDto)) {
            accountService.deleteAccount(loginDto)
            return ResponseEntity.ok().body("")
        }
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "username or password not correct")
    }

    @GetMapping("/all")
    fun findAll(): ResponseEntity<List<Account>> {
        val accounts = accountService.findAll()
        return ResponseEntity.ok().body(accounts)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<*> {
        if (accountService.login(loginDto)) {
            val account = accountService.getAccountDtoByUsername(loginDto.username)
            return ResponseEntity.ok().body(account)
        }
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "username or password not correct")
    }

    @PostMapping("/password")
    fun changePassword(@RequestBody passwordChangeDto: PasswordChangeDto): ResponseEntity<*> {
        accountService.changePassword(passwordChangeDto)
        return ResponseEntity.ok().body("")
    }
}
