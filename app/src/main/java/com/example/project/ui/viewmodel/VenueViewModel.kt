package com.example.project.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.Home.models.Court
import com.example.project.data.repository.VenueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class VenueUiState {
    object Loading : VenueUiState()
    data class Success(val venues: List<Court>) : VenueUiState()
    data class Error(val message: String) : VenueUiState()
}

class VenueViewModel(
    private val repository: VenueRepository = VenueRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<VenueUiState>(VenueUiState.Loading)
    val uiState: StateFlow<VenueUiState> = _uiState.asStateFlow()

    init {
        loadVenues()
    }

    fun loadVenues() {
        viewModelScope.launch {
            _uiState.value = VenueUiState.Loading
            repository.getVenues()
                .onSuccess { venues ->
                    _uiState.value = VenueUiState.Success(venues)
                }
                .onFailure { exception ->
                    _uiState.value = VenueUiState.Error(
                        exception.message ?: "Đã xảy ra lỗi khi tải danh sách sân"
                    )
                }
        }
    }
}

