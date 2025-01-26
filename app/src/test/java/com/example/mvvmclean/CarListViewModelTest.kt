package com.example.mvvmclean

import com.example.mvvmclean.domain.model.Car
import com.example.mvvmclean.domain.usecase.GetCarListUseCase
import com.example.mvvmclean.presentation.list.vm.CarListViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@OptIn(ExperimentalCoroutinesApi::class)
class CarListViewModelTest {

    @get:Rule
    val mockito: MockitoRule = MockitoJUnit.rule()

    /*@Mock
    private lateinit var repository: CarRepository*/

    @Mock
    private lateinit var getCarListUseCase: GetCarListUseCase

    private lateinit var carListViewModel: CarListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        //repository = FakeCarRepository()
        //getCarListUseCase = GetCarListUseCase(repository)
        carListViewModel = CarListViewModel(getCarListUseCase)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val mockCarList = listOf(
        Car(
            id = 1,
            name = "Model S",
            brand = "Tesla",
            price = "$89,990",
            imageUrl = "https://example.com/image.jpg"
        ), Car(
            id = 2,
            name = "Model S2",
            brand = "Tesla1",
            price = "$89,999",
            imageUrl = "https://example.com/image.jpg"
        )
    )

    @Test
    fun `verify initial state is empty list`() = runTest {
        //created mock
        `when`(getCarListUseCase.execute()).thenReturn(mockCarList)
        advanceUntilIdle()

        //System.out.println("DP"+{carListViewModel.carList.value})

        assert(carListViewModel.carList.value == null)


    }


}