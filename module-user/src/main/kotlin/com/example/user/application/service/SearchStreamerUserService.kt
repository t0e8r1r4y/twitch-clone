package com.example.user.application.service

import com.example.user.application.port.`in`.user.streamer.FindPendingStreamerUserUseCase
import com.example.user.application.port.`in`.user.streamer.SearchStreamerUserQuery
import com.example.user.application.port.`in`.user.streamer.SearchStreamerUserUseCase
import com.example.user.application.port.out.StreamerUserRepository
import com.example.user.domain.model.StreamerUser
import com.example.user.domain.model.StreamerUserStatus

class SearchStreamerUserService(private val streamerRepository: StreamerUserRepository) : SearchStreamerUserUseCase, FindPendingStreamerUserUseCase {

    override fun searchStreamers(searchStreamerUserQuery: SearchStreamerUserQuery): List<StreamerUser> {
        return with(searchStreamerUserQuery) {
            streamerRepository.findStreamers(streamerNickname, streamerUserStatus)
        }
    }

    override fun findPendingStreamers(): List<StreamerUser> {
        return streamerRepository.findAllByStatus(StreamerUserStatus.PENDING)
    }
}
