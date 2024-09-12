package com.diatomicsoft.tntodo.presentation.map

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.tntodo.domain.usecase.CurrentLocationTrackingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MapViewModel @Inject constructor(private val useCase: CurrentLocationTrackingUseCase) :
    ViewModel() {

    private val _currentLocation = MutableSharedFlow<Unit>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val currentLocation: StateFlow<Location?> = _currentLocation.flatMapLatest {
        useCase()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    init {
        requestLocationUpdates()
    }

    fun requestLocationUpdates() {
        viewModelScope.launch {
            _currentLocation.emit(Unit)
        }
    }

}