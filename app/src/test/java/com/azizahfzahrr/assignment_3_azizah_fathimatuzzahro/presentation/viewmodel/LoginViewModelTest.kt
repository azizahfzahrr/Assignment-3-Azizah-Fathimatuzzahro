import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.LoginResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.AuthRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.source.local.UserSession
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.domain.usecase.LoginUseCase
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import androidx.lifecycle.Observer

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var authRepository: AuthRepository

    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var userSession: UserSession

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginUseCase = LoginUseCase(authRepository)
        loginViewModel = LoginViewModel(loginUseCase, userSession)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun loginShouldReturnSuccess() = testDispatcher.runBlockingTest {
        val email = "test@example.com"
        val password = "password"
        val userData = LoginResponse.Data(
            avatar = "avatar_url",
            email = email,
            firstName = "John",
            lastName = "Doe",
            phone = "1234567890",
            token = "token123"
        )
        val loginResponse = LoginResponse(success = true, message = "login successful", data = userData)

        `when`(authRepository.login(email, password)).thenReturn(loginResponse)

        loginViewModel.login(email, password)

        assertTrue(loginViewModel.loginResult.value?.success == true)
        assertEquals(userData, loginViewModel.loginResult.value?.data)
    }

    @Test
    fun loginShouldReturnError() = testDispatcher.runBlockingTest {
        val email = "test@example.com"
        val password = "wrongpassword"
        val errorMessage = "Login failed"

        `when`(authRepository.login(email, password)).thenThrow(RuntimeException(errorMessage))

        val loginResultObserver = Observer<LoginResponse> { response ->
            assertTrue(response.success == false)
            assertEquals(errorMessage, response.message)
        }
        loginViewModel.loginResult.observeForever(loginResultObserver)
        loginViewModel.login(email, password)
        loginViewModel.loginResult.removeObserver(loginResultObserver)
    }
}