package com.example.tiketku.model.kotlin

import com.google.gson.annotations.SerializedName

data class ResponseWisata(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ResultItem(

	@field:SerializedName("wifi_available")
	val wifiAvailable: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("lokasi_wisata")
	val lokasiWisata: String? = null,

	@field:SerializedName("harga_tiket")
	val hargaTiket: String? = null,

	@field:SerializedName("festivals")
	val festivals: String? = null,

	@field:SerializedName("id_wisata")
	val idWisata: String? = null,

	@field:SerializedName("kode_wisata")
	val kodeWisata: String? = null,

	@field:SerializedName("photo_spots")
	val photoSpots: String? = null,

	@field:SerializedName("nama_wisata")
	val namaWisata: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("information")
	val information: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null
)
