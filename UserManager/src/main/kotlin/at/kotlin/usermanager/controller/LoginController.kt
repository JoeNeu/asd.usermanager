package at.kotlin.usermanager.controller

import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.services.AccountService
import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/auth")
class LoginController (
        val accountService: AccountService
) {
    @PostMapping
    fun login(
            @RequestHeader("username") username: String,
            @RequestHeader("password") password: String
    ) {
        if(!accountService.login(Account(null, username, password))) {

        }
    }
}
