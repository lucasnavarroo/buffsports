package com.example.buffsports.modules.buff.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buffsports.modules.buff.repository.BuffRepository

class BuffViewModelFactory(
    private val buffRepository: BuffRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BuffViewModel(buffRepository) as T
    }
}