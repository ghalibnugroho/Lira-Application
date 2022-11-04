package com.wantobeme.lira.model

import com.google.gson.annotations.SerializedName


data class RKatalog(
	val title: String,
	val status: String,
	val message: String,
	val data: List<Katalog>
)

data class RKatalogDetail(
	val title: String,
	val status: String,
	val message: String,
	val data: KatalogDetail
)

data class Katalog(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("bibid")
	val bibid: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("publishYear")
	val publishYear: String,

	@field:SerializedName("coverURL")
	val coverURL: String,

	@field:SerializedName("quantity")
	val quantity: Int,

)

data class KatalogDetail(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("bibid")
	val bibid: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("author")
	val author: String,

	@field:SerializedName("publisher")
	val publisher: String,

	@field:SerializedName("publishLocation")
	val publishLocation: String,

	@field:SerializedName("publishYear")
	val publishYear: String,

	@field:SerializedName("subject")
	val subject: String,

	@field:SerializedName("physicalDescription")
	val physicalDescription: String,

	@field:SerializedName("isbn")
	val isbn: String,

	@field:SerializedName("callNumber")
	val callNumber: String,

	@field:SerializedName("coverURL")
	val coverURL: String,

	@field:SerializedName("quantity")
	val quantity: Int

)
