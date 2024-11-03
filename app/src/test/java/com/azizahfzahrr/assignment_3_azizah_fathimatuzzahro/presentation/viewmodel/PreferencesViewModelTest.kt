import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.model.PreferencesResponse
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.data.repository.PreferencesRepository
import com.azizahfzahrr.assignment_3_azizah_fathimatuzzahro.presentation.viewmodel.PreferencesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class PreferencesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var preferencesRepository: PreferencesRepository

    private lateinit var preferencesViewModel: PreferencesViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        preferencesViewModel = PreferencesViewModel(preferencesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchTravelTypes should return expected list of travel types`() = runTest {
        val travelTypes = listOf("alam", "kuliner", "pasar", "museum")
        val response = PreferencesResponse(travelTypes, "Success", true)
        whenever(preferencesRepository.fetchTravelTypes()).thenReturn(response)
        val result = preferencesViewModel.fetchTravelTypes()
        val observer = Observer<List<String?>?> {}
        try {
            result.observeForever(observer)
            assertEquals(travelTypes, result.value)
        } finally {
            result.removeObserver(observer)
        }
    }

    @Test
    fun `setSelectedType should update selectedType LiveData`() {
        val selectedType = "alam"
        preferencesViewModel.setSelectedType(selectedType)
        assertEquals(selectedType, preferencesViewModel.selectedType.value)
    }
}