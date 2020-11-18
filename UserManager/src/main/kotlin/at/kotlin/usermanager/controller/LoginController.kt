package at.kotlin.usermanager.controller

import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.services.AccountService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class LoginController (
        val accountService: AccountService
) {

    @PostMapping
    fun login(
            @RequestHeader("username") username: String,
            @RequestHeader("username") password: String
    ) {
        accountService.login(Account(null, username, password))
    }
}
