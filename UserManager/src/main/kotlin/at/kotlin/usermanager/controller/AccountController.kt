package at.kotlin.usermanager.controller

import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.services.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
}
