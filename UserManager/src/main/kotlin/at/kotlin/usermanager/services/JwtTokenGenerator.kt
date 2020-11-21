package at.kotlin.usermanager.services

import io.fusionauth.jwt.Signer
import io.fusionauth.jwt.Verifier
import io.fusionauth.jwt.domain.JWT
import io.fusionauth.jwt.hmac.HMACSigner
import io.fusionauth.jwt.hmac.HMACVerifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Service
class JwtTokenGenerator {

    @Value("\${token.privatekey}")
    val privateKey: String? = null
    @Value("\${token.issuer}")
    val issuer: String? = null
    @Value("\${token.expirationtime}")
    val expirationTime: Long = 0

    fun buildJwt(uuid: UUID): String {
        val signer: Signer = HMACSigner.newSHA256Signer(privateKey)
        val jwt = JWT().setIssuer(issuer)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(uuid.toString())
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(expirationTime))
        return JWT.getEncoder().encode(jwt, signer)
    }

    fun recoverJWT(encodedJWT: String): JWT {
        val verifier: Verifier = HMACVerifier.newVerifier(privateKey)
        return JWT.getDecoder().decode(encodedJWT, verifier)
    }
}
