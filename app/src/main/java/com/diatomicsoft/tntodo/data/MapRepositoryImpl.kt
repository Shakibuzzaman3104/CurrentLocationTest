package com.diatomicsoft.tntodo.data

import android.location.Location
import com.diatomicsoft.tntodo.data.location_tracker.LocationTracker
import com.diatomicsoft.tntodo.domain.repo.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val locationTracker: LocationTracker) :MapRepository{
    override fun trackCurrentLocation(): Flow<Location> {
        return locationTracker.getLocationUpdates()
    }
}