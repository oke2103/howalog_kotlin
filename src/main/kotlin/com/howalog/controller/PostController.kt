package com.howalog.controller

import com.howalog.request.PostCreateDto
import com.howalog.response.PostResponse
import com.howalog.service.PostService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class PostController(
    private val postService: PostService,
) {

    @PostMapping("/posts")
    fun write(@RequestBody @Valid postCreateDto: PostCreateDto) {
        postService.write(postCreateDto)
    }

    @GetMapping("/posts/{postId}")
    fun get(@PathVariable postId: Long): PostResponse {
        return postService.get(postId)
    }
}