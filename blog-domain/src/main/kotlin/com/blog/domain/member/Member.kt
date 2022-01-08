package com.blog.domain.member

import com.blog.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Member(
        @Column(nullable = false)
        var email: String,

        var password: String,

        var memberImage: String?,

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var provider: Provider,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var role: Role,

): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
}