package com.example.buffsports.modules.buff.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buffsports.modules.buff.business.BuffBusiness

class BuffViewModelFactory(
    private val buffBusiness: BuffBusiness
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BuffViewModel(buffBusiness) as T
    }
}