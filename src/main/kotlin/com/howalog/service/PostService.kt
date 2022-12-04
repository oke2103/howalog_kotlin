package com.howalog.service

import com.howalog.domain.Post
import com.howalog.exception.PostNotFound
import com.howalog.repository.PostRepository
import com.howalog.request.PostCreateDto
import com.howalog.response.PostResponse
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
) {

    fun write(request: PostCreateDto) {
        postRepository.save(Post(title = request.title, content = request.content))
    }

    fun get(id: Long): PostResponse {
        val post = postRepository.findById(id)
            .orElseThrow(::PostNotFound)

        return PostResponse.of(post)
    }
}