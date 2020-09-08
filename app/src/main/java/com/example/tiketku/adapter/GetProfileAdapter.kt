package com.example.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiketku.R
import com.example.tiketku.model.kotlin.ResultItemPurchaser
import kotlinx.android.synthetic.main.item_my_ticket.view.*

class GetProfileAdapter(var data:List<ResultItemPurchaser?>?):RecyclerView.Adapter<GetProfileAdapter.MainViewHolder>() {
    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var namaWisata =itemView.namaWisata
        var lokasiWisata = itemView.lokasiWisata
        var jumlahTiket = itemView.angka
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_ticket,parent,false)
        var holder = MainViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return data?.size?:0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.namaWisata.text =data?.get(position)?.nama_wisata
        holder.lokasiWisata.text =data?.get(position)?.lokasi_wisata
        holder.jumlahTiket.text =data?.get(position)?.jumlah_tiket
    }
}