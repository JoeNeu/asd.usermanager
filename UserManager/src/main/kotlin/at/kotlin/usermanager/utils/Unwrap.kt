package at.kotlin.usermanager.utils

import java.util.*

object Unwrap {

    fun <BannedAccount> Optional<BannedAccount>.unwrapBannedAccount(): BannedAccount? = orElse(null)
}
