package com.diatomicsoft.tntodo.domain.usecase

import android.location.Location
import com.diatomicsoft.tntodo.domain.repo.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentLocationTrackingUseCase @Inject constructor( private val repository: MapRepository) {

    operator fun invoke(): Flow<Location> {
        return repository.trackCurrentLocation()
    }

}