package com.wantobeme.lira.views.uimodel

data class KatalogUIModelApi(
    val title: String = "",
    val status: String = "",
    val message: String = "",
    val data: List<KatalogUIModel> = listOf()
)

data class KatalogUIModel(
    val id: String,
    val bibid: String,
    val title: String,
    val author: String,
    val publishYear: String,
    val coverURL: String,
    val quantity: Int,
)
