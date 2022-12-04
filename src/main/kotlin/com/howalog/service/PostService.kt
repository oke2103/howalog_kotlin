package com.howalog.service

import com.howalog.domain.Post
import com.howalog.repository.PostRepository
import com.howalog.request.PostCreateDto
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository,
) {

    fun write(request: PostCreateDto) {
        postRepository.save(Post(title = request.title, content = request.content))
    }
}