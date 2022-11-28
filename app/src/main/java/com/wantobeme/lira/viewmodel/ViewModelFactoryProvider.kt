package com.wantobeme.lira.viewmodel

import com.wantobeme.lira.viewmodel.guest.KatalogDetailViewModel
import com.wantobeme.lira.viewmodel.petugas.KoleksiViewModel
import com.wantobeme.lira.viewmodel.petugas.QRViewModel
import com.wantobeme.lira.viewmodel.petugas.SirkulasiLoanItemsViewModel
import com.wantobeme.lira.viewmodel.petugas.SirkulasiLoanViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {
    fun katalogDetailViewModelFactory(): KatalogDetailViewModel.Factory
    fun sirkulasiLoanViewModelFactory(): SirkulasiLoanViewModel.Factory
    fun sirkulasiLoanItemsViewModelFactory(): SirkulasiLoanItemsViewModel.Factory
    fun koleksiViewModelFactory(): KoleksiViewModel.Factory
    fun qrViewModelFactory(): QRViewModel.Factory
}