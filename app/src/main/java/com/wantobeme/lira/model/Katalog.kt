package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName

// Response KatalogPage
data class RKatalog(
	val title: String,
	val status: String,
	val message: String,
	val data: List<Katalog>
)
// Response KatalogDetailPage
data class RKatalogDetail(
	val title: String,
	val status: String,
	val message: String,
	val data: KatalogDetail
)
// Guest Page
data class Katalog(

	@field:SerializedName("id")
	var id: String,

	@field:SerializedName("bibid")
	var bibid: String,

	@field:SerializedName("title")
	var title: String,

	@field:SerializedName("author")
	var author: String,

	@field:SerializedName("publishYear")
	var publishYear: String,

	@field:SerializedName("coverURL")
	var coverURL: String,

	@field:SerializedName("quantity")
	var quantity: Int,

)

// Guest Page
data class KatalogDetail(

	@field:SerializedName("id")
	var id: String,

	@field:SerializedName("bibid")
	var bibid: String,

	@field:SerializedName("title")
	var title: String,

	@field:SerializedName("author")
	var author: String,

	@field:SerializedName("publisher")
	var publisher: String,

	@field:SerializedName("publishLocation")
	var publishLocation: String,

	@field:SerializedName("publishYear")
	var publishYear: String,

	@field:SerializedName("subject")
	var subject: String,

	@field:SerializedName("physicalDescription")
	var physicalDescription: String,

	@field:SerializedName("isbn")
	var isbn: String,

	@field:SerializedName("callNumber")
	var callNumber: String,

	@field:SerializedName("coverURL")
	var coverURL: String,

	@field:SerializedName("quantity")
	var quantity: Int

)
