package at.kotlin.usermanager.dtos

data class UserDto (
    val username: String,
    val firstname: String,
    val lastname: String,
    var token: String?
)
