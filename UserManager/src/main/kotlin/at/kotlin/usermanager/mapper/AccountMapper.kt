package at.kotlin.usermanager.mapper

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.UserDto
import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.services.JwtTokenGenerator
import at.kotlin.usermanager.utils.HashUtil.hash
import org.springframework.stereotype.Service

@Service
class AccountMapper(
        val jwtTokenGenerator: JwtTokenGenerator
) {

    fun mapToEntityAndHashPassword(accountDto: AccountDto): Account {
        return Account(id = null, accountDto.username, hash(accountDto.password), accountDto.firstname, accountDto.lastname)
    }

    fun mapToDtoAndGenerateJwt(account: Account): UserDto {
        return UserDto(account.username, account.firstname, account.lastname, jwtTokenGenerator.buildJwt(account.id!!))
    }
}
