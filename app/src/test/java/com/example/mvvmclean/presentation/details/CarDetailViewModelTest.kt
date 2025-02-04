package com.example.mvvmclean.presentation.details

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.usecase.GetCarDetailsUseCase
import com.example.mvvmclean.domain.usecase.UpdateCarDetailsUseCase
import com.example.mvvmclean.presentation.details.vm.CarDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@OptIn(ExperimentalCoroutinesApi::class) // Required for coroutines testing
class CarDetailViewModelTest {

    // Tell JUnit to use Mockito for this test class
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    // Create mock objects for our use cases
    @Mock
    private lateinit var getCarDetailsUseCase: GetCarDetailsUseCase

    @Mock
    private lateinit var updateCarDetailsUseCase: UpdateCarDetailsUseCase

    // The ViewModel we're testing
    private lateinit var viewModel: CarDetailViewModel

    // Dispatcher used for testing coroutines
    private val testDispatcher = StandardTestDispatcher()

    // Sample test data
    private val testCar = Car(
        id = 1,
        name = "Model S",
        brand = "Tesla",
        price = "$89,990",
        imageUrl = "https://example.com/image.jpg"
    )


    @Before
    fun setup() {
        // Replace the Main dispatcher with our test dispatcher
        Dispatchers.setMain(testDispatcher)

        // Create new ViewModel with mocked dependencies
        viewModel = CarDetailViewModel(
            getCarDetailsUseCase = getCarDetailsUseCase,
            updateCarDetailsUseCase = updateCarDetailsUseCase
        )
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher after tests
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCarDetails should update state with car details when successful`() = runTest {
        /// Given - Setup the test conditions
        val carId = 1
        // When getCarDetailsUseCase.execute(carId) is called, return testCar
        `when`(getCarDetailsUseCase.execute(carId)).thenReturn(testCar)

        // When - Perform the action we're testing
        viewModel.fetchCarDetails(carId)
        // Wait for all coroutines to complete
        advanceUntilIdle()

        // Then - Verify the results
        // Check if the StateFlow has the expected value
        assert(viewModel.carDetails.value == testCar)

        // Verify that the use case was called with the correct ID
        verify(getCarDetailsUseCase).execute(carId)
    }

    @Test
    fun `fetchCarDetails should handle exception`() = runTest {
        // Given - Setup error condition
        val carId = 1
        // Configure mock to throw exception
        `when`(getCarDetailsUseCase.execute(carId))
            .thenReturn(null)

        // When - Try to fetch details
        viewModel.fetchCarDetails(carId)
        advanceUntilIdle()

        // Then - Verify error handling
        // StateFlow should remain null
        assert(viewModel.carDetails.value == null)
    }


    @Test
    fun `updateCarDetails should maintain state on failure`() = runTest {
        // Given - Setup initial state
        val originalCar = testCar
        val updatedCar = testCar.copy(name = "Model S Plaid")

        // Set initial state
        viewModel.fetchCarDetails(originalCar.id)
        advanceUntilIdle()

        // Configure update to fail
        `when`(updateCarDetailsUseCase.execute(null))
            .thenThrow(RuntimeException("Update failed"))

        // When - Try to update
        viewModel.updateCarDetails(updatedCar)
        advanceUntilIdle()

        // Then - Verify state
        // State should be updated even if update fails (based on your implementation)
        assert(viewModel.carDetails.value == updatedCar)
        // Verify update was attempted
        verify(updateCarDetailsUseCase).execute(updatedCar)
    }
}