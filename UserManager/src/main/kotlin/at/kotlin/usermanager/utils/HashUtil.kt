package at.kotlin.usermanager.utils

object HashUtil {
    fun hash(input: String): String
            = org.apache.commons.codec.digest.DigestUtils.sha256Hex(input)
}
