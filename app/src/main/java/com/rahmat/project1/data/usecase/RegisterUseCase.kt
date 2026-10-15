package com.rahmat.project1.data.usecase

import com.rahmat.project1.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        companyName: String,
        ownerName: String,
        email: String,
        password: String
    ) {
        authRepository.signUp(
            email = email,
            password = password,
            companyName = companyName,
            ownerName = ownerName
        )
    }
}
