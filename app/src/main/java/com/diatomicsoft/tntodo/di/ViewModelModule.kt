package com.diatomicsoft.tntodo.di

import com.diatomicsoft.tntodo.data.MapRepositoryImpl
import com.diatomicsoft.tntodo.data.location_tracker.LocationTracker
import com.diatomicsoft.tntodo.domain.repo.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideMapRepository(locationTracker: LocationTracker): MapRepository {
        return MapRepositoryImpl(locationTracker)
    }

}