package com.yihsiang.zoo.model

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class ZooRepositoryTest {

    @Mock
    private lateinit var zooService: ZooService

    @Mock
    private lateinit var dataSource: LocalAnimalJsonDataSource

    private lateinit var zooRepository: ZooRepository

    @Before
    fun setup() {
        zooRepository = ZooRepository(zooService, dataSource)
    }

    @Test
    fun `when get animals by location, should be filter from animal list`() = runTest {
        given(dataSource.getAnimals()).willReturn(ANIMALS)

        val animals = zooRepository.getAnimalsBy("企鵝館")
            .first()
            .getOrNull()

        assertThat(animals?.get(0)?.id, `is`(2))
    }

    companion object {
        val ANIMALS  =
            listOf(
                Animal(1, "大貓熊", "Giant Panda", "大貓熊館",
                    "", "", "", "", ""),
                Animal(2, "國王企鵝", "", "企鵝館",
                    "", "", "", "", "")
            )
    }
}