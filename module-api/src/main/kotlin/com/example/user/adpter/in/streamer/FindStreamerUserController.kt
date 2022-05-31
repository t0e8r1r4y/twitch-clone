package com.example.user.adpter.`in`.streamer

import com.example.user.application.port.`in`.streamer.FindPendingStreamerUserUseCase
import com.example.user.application.port.`in`.streamer.FindStreamerUserUseCase
import com.example.user.domain.model.StreamerUser
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Transactional(readOnly = true)
@RestController
class FindStreamerUserController(
    private val findStreamerUserUseCase: FindStreamerUserUseCase,
    private val findPendingStreamerUserUseCase: FindPendingStreamerUserUseCase
) {

    @GetMapping(path = ["/api/streamer"])
    fun find(@Valid @RequestBody req: FindStreamerUserQuery): FindStreamerUserResponse {
        // 스트리머 이름으로 조회
        req.streamerNickname?.let {
            val findStreamerUserQuery = com.example.user.application.port.`in`.streamer.FindStreamerUserQuery(req.streamerNickname)
            return FindStreamerUserResponse(findStreamerUserUseCase.findStreamers(findStreamerUserQuery))
        }

        // 전체 조회
        return FindStreamerUserResponse(findPendingStreamerUserUseCase.findPendingStreamers())
    }
}

data class FindStreamerUserQuery(val streamerNickname: String? = null)

data class FindStreamerUserResponse(val streamerUsers: List<StreamerUser>)
