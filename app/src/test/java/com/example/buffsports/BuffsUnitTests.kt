package com.example.buffsports

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.buffsports.core.livedata.SingleLiveEvent
import com.example.buffsports.modules.buff.business.BuffBusiness
import com.example.buffsports.modules.buff.model.BuffResponse
import com.example.buffsports.modules.buff.viewmodel.BuffViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class BuffsUnitTests {

    private lateinit var buffViewModel: BuffViewModel

    @Mock
    lateinit var buffBusiness: BuffBusiness

    @Mock
    lateinit var buffObserverMock: Observer<BuffResponse.Buff>

    @Mock
    lateinit var shouldShowBuffMock: Observer<Boolean>

    @Mock
    lateinit var onErrorMock: SingleLiveEvent<String>

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        buffViewModel = BuffViewModel(buffBusiness)
    }

    @Test
    fun `it should assert that buff livedata has updated its value`() {
        val buff = BuffResponse.Buff(0, "", "", 0, 0, "", null, null, null, "")
        buffViewModel.buff.observeForever(buffObserverMock)

        buffViewModel.buff.value = buff

        Assert.assertEquals(buff, buffViewModel.buff.value)
    }

    @Test
    fun `it should verify that observer has been called after getBuff() method`() {
        buffViewModel.buff.observeForever(buffObserverMock)
        buffViewModel.getBuff()

        verify(buffObserverMock, times(1)).onChanged(any())
    }

    @Test
    fun `it should verify if onError live event has been called after updated its value`() {
        buffViewModel.onError = onErrorMock

        buffViewModel.onError.call()

        verify(onErrorMock, times(1)).call()
    }

    @Test
    fun `it should execute getBuff method`() {
        buffViewModel.getBuff()
        Mockito.verify(buffBusiness, Mockito.times(1))?.getBuff("", {}, {})
    }

    @Test
    fun `it should return true to shouldShowBuff value after checkBuffState() method is called`() {
        buffViewModel.shouldShowBuff.observeForever(shouldShowBuffMock)

        buffViewModel.isStreamPlaying = true
        buffViewModel.timer = 15

        buffViewModel.checkBuffState()

        Assert.assertEquals(true, buffViewModel.shouldShowBuff.value)
    }

    @Test
    fun `it should return false to shouldShowBuff value after checkBuffState() method is called`() {
        buffViewModel.shouldShowBuff.observeForever(shouldShowBuffMock)

        buffViewModel.isStreamPlaying = false
        buffViewModel.timer = 15

        buffViewModel.checkBuffState()

        Assert.assertEquals(false, buffViewModel.shouldShowBuff.value)
    }
}