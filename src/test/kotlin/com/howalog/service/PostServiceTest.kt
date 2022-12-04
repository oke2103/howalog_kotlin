package com.howalog.service

import com.howalog.domain.Post
import com.howalog.exception.PostNotFound
import com.howalog.repository.PostRepository
import com.howalog.request.PostCreateDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PostServiceTest @Autowired constructor(
    private val postService: PostService,
    private val postRepository: PostRepository,
) {

    @AfterEach
    fun initRepository() {
        postRepository.deleteAll()
    }

    @Test
    @DisplayName("게시글 등록 - 성공")
    fun writeTest() {
        // given
        val request = PostCreateDto(title = "제목", content = "내용")

        // when
        postService.write(request)

        // then
        val result = postRepository.findAll()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].title).isEqualTo("제목")
        assertThat(result[0].content).isEqualTo("내용")
    }

    @Test
    @DisplayName("게시글 조회 - 성공")
    fun getTest() {
        // given
        val savedPost = postRepository.save(Post(title = "제목", content = "내용"))

        // when
        val response = postService.get(savedPost.id!!)

        // then
        assertThat(response.title).isEqualTo("제목")
        assertThat(response.content).isEqualTo("내용")
    }

    @Test
    @DisplayName("게시글 조회 - 실패 :: 게시글 없음")
    fun getFailPostNotFound() {
        // expected
        assertThrows<PostNotFound> {
            postService.get(0L)
        }
    }

}