package com.wantobeme.lira.views.uiState

import com.wantobeme.lira.model.Katalog

data class KatalogState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<Katalog> = emptyList()
)