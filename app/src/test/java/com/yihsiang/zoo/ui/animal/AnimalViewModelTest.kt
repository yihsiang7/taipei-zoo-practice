package com.yihsiang.zoo.ui.animal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.yihsiang.zoo.MainCoroutineRule
import com.yihsiang.zoo.model.AnimalHeader
import com.yihsiang.zoo.model.Venue
import com.yihsiang.zoo.model.VenueDescriptionHeader
import com.yihsiang.zoo.model.ZooRepository
import com.yihsiang.zoo.model.ZooRepositoryTest.Companion.ANIMALS
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull

@RunWith(MockitoJUnitRunner::class)
internal class AnimalViewModelTest {

    private lateinit var viewModel: AnimalViewModel

    @Mock
    private lateinit var zooRepository: ZooRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = AnimalViewModel(
            zooRepository,
            SavedStateHandle(mapOf("venue" to VENUE))
        )
    }

    @Test
    fun `when there are no animals, should be no animal title`() = runTest {
        given(zooRepository.getAnimalsBy(anyOrNull()))
            .willReturn(flow { emit(Result.success(emptyList())) })

        val result = viewModel.animals.first().getOrNull()

        assertEquals(1, result?.size)
        assertTrue(result?.get(0) is VenueDescriptionHeader)
    }

    @Test
    fun `when there are many animals, should be an animal title`() = runTest {
        given(zooRepository.getAnimalsBy(anyOrNull()))
            .willReturn(flow { emit(Result.success(ANIMALS)) })

        val result = viewModel.animals.first().getOrNull()

        assertTrue(result?.get(1) is AnimalHeader)
    }

    companion object {
        private val VENUE = Venue(1, "熱帶雨林區", "",
            "", "", "")
    }
}