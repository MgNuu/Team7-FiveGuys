package org.softeer.robocar.data.service.auth

import org.softeer.robocar.data.dto.login.request.LoginRequest
import org.softeer.robocar.data.dto.login.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

}