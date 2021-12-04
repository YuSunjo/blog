package com.blog.domain.admin

import com.blog.domain.BaseTimeEntity
import javax.persistence.*

@Entity
class Admin(
    @Column(nullable = false)
    var email: String,
    @Column(nullable = false)
    var password: String,
    var adminImage: String?
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L

}