package at.kotlin.usermanager.mapper

import at.kotlin.usermanager.dtos.AccountDto
import at.kotlin.usermanager.dtos.UserDto
import at.kotlin.usermanager.entities.Account
import at.kotlin.usermanager.utils.HashUtil.hash
import org.springframework.stereotype.Service

@Service
class AccountMapper {

    fun mapToEntityAndHashPassword(accountDto: AccountDto): Account {
        return Account(null, accountDto.username, hash(accountDto.password), accountDto.firstname, accountDto.lastname)
    }

    fun mapToDto(account: Account): UserDto {
        return UserDto(account.username, account.firstname, account.lastname)
    }
}
