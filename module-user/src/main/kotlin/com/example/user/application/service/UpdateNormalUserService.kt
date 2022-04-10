package com.example.user.application.service

import com.example.user.application.port.`in`.user.normal.SuspendNormalUserUseCase
import com.example.user.application.port.`in`.user.normal.UpdateNormalUserUseCase
import com.example.user.application.port.`in`.user.normal.UpdateUserCommand
import com.example.user.application.port.out.NormalUserRepository
import com.example.user.domain.model.User
import java.util.*

internal class UpdateNormalUserService(private val normalUserRepository: NormalUserRepository) : UpdateNormalUserUseCase, SuspendNormalUserUseCase {

    /**
     * - 현재는 nickname만 변경 합니다.
     */
    override fun updateNormalUser(updateUserCommand: UpdateUserCommand): User {
        return with(updateUserCommand) {
            val normalUser = normalUserRepository.findById(id).apply {
                nickname = updateUserCommand.nickname ?: nickname
            }
            normalUserRepository.save(normalUser)
        }
    }

    override fun suspendNormalUser(userId: UUID) {
        val user = normalUserRepository.findById(userId)
        user.suspendedUser()
        normalUserRepository.save(user)
    }
}
