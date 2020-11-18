package at.kotlin.usermanager.dtos

data class PasswordChangeDto (
        val username: String,
        val newPassword: String
)
