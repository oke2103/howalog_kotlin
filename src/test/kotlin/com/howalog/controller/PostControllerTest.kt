package com.howalog.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.howalog.domain.Post
import com.howalog.repository.PostRepository
import com.howalog.request.PostCreateDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val postRepository: PostRepository,
) {

    @AfterEach
    fun initRepository() {
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("게시글 작성 - 성공")
    fun write() {
        // given
        val request = PostCreateDto(title = "제목", content = "내용")
        val json = objectMapper.writeValueAsString(request)

        // when
        mockMvc.perform(
            post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
            .andDo(print())

        // then
        val result = postRepository.findAll()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].title).isEqualTo("제목")
        assertThat(result[0].content).isEqualTo("내용")
    }

    @Test
    @DisplayName("게시글 작성 - 실패 :: 제목, 내용 공백")
    fun writeFailTitleBlank() {
        // given
        val request = PostCreateDto(title = "", content = "")
        val json = objectMapper.writeValueAsString(request)

        // expected
        mockMvc.perform(
            post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
            .andExpect(jsonPath("$.validation.title").value("게시글 제목은 필수입니다."))
            .andExpect(jsonPath("$.validation.content").value("게시글 내용은 필수입니다."))
            .andDo(print())
    }

    @Test
    @DisplayName("게시글 조회 - 성공")
    fun get() {
        // given
        val request = postRepository.save(Post(title = "제목", content = "내용"))

        // expected
        mockMvc.perform(get("/posts/{postId}", request.id))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("제목"))
            .andExpect(jsonPath("$.content").value("내용"))
            .andDo(print())
    }

    @Test
    @DisplayName("게시글 조회 - 실패 :: 게시글 없음")
    fun getFailPostNotFound() {
        // expected
        mockMvc.perform(get("/posts/{postId}", 0L))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value(400))
            .andExpect(jsonPath("$.message").value("게시글이 존재하지 않습니다."))
            .andDo(print())
    }

}