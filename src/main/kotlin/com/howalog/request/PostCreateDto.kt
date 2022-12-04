package com.howalog.request

import javax.validation.constraints.NotBlank

data class PostCreateDto(

    @field: NotBlank(message = "게시글 제목은 필수입니다.")
    val title: String,

    @field: NotBlank(message = "게시글 내용은 필수입니다.")
    val content: String,
) {
}