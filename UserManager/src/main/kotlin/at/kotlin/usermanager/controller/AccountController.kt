package at.kotlin.usermanager.controller

import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.services.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/account")
class AccountController(
        val accountService: AccountService
) {
    @PostMapping
    fun createUser(
            @RequestHeader("username") username: String,
            @RequestHeader("password") password: String
    ) {
        accountService.login(Account(null, username, password))
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<Account>> {
        val accounts = accountService.findAll()
        return ResponseEntity.ok().body(accounts)
    }

    @PostMapping("/login")
    fun login(
            @RequestHeader("username") username: String,
            @RequestHeader("password") password: String
    ) : ResponseEntity<*> {
        if(!accountService.login(Account(null, username, password))) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "username oder password nicht korrekt")
        }
    }
}
