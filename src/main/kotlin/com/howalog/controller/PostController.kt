package com.howalog.controller

import com.howalog.request.PostCreateDto
import com.howalog.service.PostService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class PostController(
    private val postService: PostService,
) {

    @PostMapping("/posts")
    fun write(@RequestBody @Valid postCreateDto: PostCreateDto) {
        postService.write(postCreateDto)
    }
}