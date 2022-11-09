package com.wantobeme.lira.viewmodel

import com.wantobeme.lira.viewmodel.guest.KatalogDetailViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun katalogDetailViewModelFactory(): KatalogDetailViewModel.Factory

}