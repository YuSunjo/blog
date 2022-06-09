package com.blog.domain.member

import com.blog.domain.BaseTimeEntity
import java.util.*
import javax.persistence.*

@Entity
class Member(

        var email: String?,

        var password: String?,

        var memberImage: String?,

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var provider: Provider,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var role: Role,

        var nickname: String,

        var socialId: String?

): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

    companion object {
        fun newMember(socialId: String, provider: Provider, email: String?, name: String?): Member {
            val uuIdName = name ?: UUID.randomUUID().toString()
            return Member(email, null, null, provider, Role.USER, uuIdName, socialId)
        }

        fun testGoogleInstance(socialId: String): Member {
            return Member(null, null, null, Provider.GOOGLE, Role.USER, "name", socialId)
        }

        fun testFacebookInstance(socialId: String): Member {
            return Member(null, null, null, Provider.FACEBOOK, Role.USER, "name", socialId)
        }

    }

}