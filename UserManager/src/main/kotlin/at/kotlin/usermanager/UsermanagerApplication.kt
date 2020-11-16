package at.kotlin.usermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UsermanagerApplication

fun main(args: Array<String>) {
	runApplication<UsermanagerApplication>(*args)
}
