import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginRequest
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.AuthRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.remote.ApiService
import java.lang.Exception

class AuthRepositoryImpl(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        val response = apiService.login(request)

        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Login failed, empty response")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Login failed: ${response.message()}, Body: $errorBody")
        }
    }
}