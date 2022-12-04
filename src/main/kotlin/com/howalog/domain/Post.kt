package com.howalog.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Post(

    var title: String,

    var content: String,

    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null
) {
}