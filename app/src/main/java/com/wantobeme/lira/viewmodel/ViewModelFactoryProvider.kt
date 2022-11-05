package com.wantobeme.lira.viewmodel

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun katalogDetailViewModelFactory(): KatalogDetailViewModel.Factory

}