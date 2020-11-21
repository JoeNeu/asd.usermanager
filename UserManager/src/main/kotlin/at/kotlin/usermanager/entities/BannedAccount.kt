package at.kotlin.usermanager.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class BannedAccount (

        @Id
        var username: String,

        @Column(nullable = false)
        var tries: Int
)
