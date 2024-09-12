package com.diatomicsoft.tntodo.domain.repo

import android.location.Location
import kotlinx.coroutines.flow.Flow


interface MapRepository {
    fun trackCurrentLocation(): Flow<Location>
}