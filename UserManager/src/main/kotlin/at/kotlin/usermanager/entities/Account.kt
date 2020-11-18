package at.kotlin.usermanager.entities

import javax.persistence.*

@Entity
data class Account (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uuid: String?,

    @Column(nullable = false)
    var username: String?,

    @Column(nullable = false)
    var password: String?
)
