package com.howalog.response

import com.howalog.domain.Post

data class PostResponse(
    val title: String,
    val content: String,
) {

    companion object {
        fun of(post: Post): PostResponse {
            return PostResponse(
                title = post.title,
                content = post.content
            )
        }
    }
}