package com.howalog.exception

class PostNotFound(
    MESSAGE: String = "게시글이 존재하지 않습니다."
) : HowalogException(MESSAGE) {

    override fun getStatusCode(): Int {
        return 400
    }

}
