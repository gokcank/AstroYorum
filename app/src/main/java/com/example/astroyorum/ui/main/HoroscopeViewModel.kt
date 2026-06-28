package com.example.astroyorum.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.astroyorum.data.HoroscopeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.example.astroyorum.data.DailyAstroData

sealed class HoroscopeUiState {
    object Loading : HoroscopeUiState()
    data class Success(val data: DailyAstroData) : HoroscopeUiState()
    data class Error(val message: String) : HoroscopeUiState()
}

class HoroscopeViewModel : ViewModel() {
    private val repository = HoroscopeRepository()
    
    private val _uiState = MutableStateFlow<HoroscopeUiState>(HoroscopeUiState.Loading)
    val uiState: StateFlow<HoroscopeUiState> = _uiState.asStateFlow()

    init {
        fetchHoroscopes()
    }

    fun fetchHoroscopes() {
        viewModelScope.launch {
            _uiState.value = HoroscopeUiState.Loading
            
            val astroData = repository.getDailyAstroData()
            if (astroData != null && astroData.horoscopes.isNotEmpty()) {
                _uiState.value = HoroscopeUiState.Success(astroData)
            } else {
                _uiState.value = HoroscopeUiState.Error("Henüz bugünün yorumları yüklenmemiş veya internet bağlantısı yok.")
            }
        }
    }
}
