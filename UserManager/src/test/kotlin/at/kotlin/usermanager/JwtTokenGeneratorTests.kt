package at.kotlin.usermanager

import at.kotlin.usermanager.services.JwtTokenGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class JwtTokenGeneratorTests(
        @Autowired val jwtTokenGenerator: JwtTokenGenerator
) {
    val testUuid: UUID = UUID.fromString("e9bc5698-557d-4323-ba02-575195558c04")
    val testJwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDczNTYzNTAsImlhdCI6MTYwNzM1NTc1MCwiaXNzIjoid3d3LmFzZC5hdCIsInN1YiI6ImU5YmM1Njk4LTU1N2QtNDMyMy1iYTAyLTU3NTE5NTU1OGMwNCJ9.OLIqMjLpv2XRUNddP06KRSjwp_4sk6JNl7pUoXq2yDc"

    @Test
    fun `build a JWT as a String`() {
        val jwt = jwtTokenGenerator.buildJwt(testUuid)
        assertThat(jwt).isNotNull
    }

    @Test
    fun `recover the JWT from a String`() {
        val jwt = jwtTokenGenerator.recoverJWT(testJwt)
        assertThat(jwt.subject).isEqualTo(testUuid.toString())
    }
}
