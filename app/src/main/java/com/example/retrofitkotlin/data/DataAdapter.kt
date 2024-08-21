package com.example.retrofitkotlin.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitkotlin.R

class DataAdapter (private  val dataList:ArrayList<DataModel>): RecyclerView.Adapter<DataAdapter.ViewHolderClass>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_view_item,parent,false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.sl.text = (position+1).toString()
        holder.p_code.text = currentItem.p_code
        holder.p_desc.text = currentItem.p_desc
        holder.pack_size.text = currentItem.pack_size
        holder.comm_tp.text = currentItem.comm_tp
        holder.comm_vp.text = currentItem.comm_vp
        holder.comm_mrp.text = currentItem.comm_mrp
    }

    override fun getItemCount(): Int {
        return  dataList.size
    }

    inner class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView) {
        var sl = itemView.findViewById<TextView>(R.id.sl)
        var p_code = itemView.findViewById<TextView>(R.id.p_code)
        var p_desc = itemView.findViewById<TextView>(R.id.p_desc)
        var pack_size = itemView.findViewById<TextView>(R.id.pack_size)
        var comm_tp = itemView.findViewById<TextView>(R.id.comm_tp)
        var comm_vp = itemView.findViewById<TextView>(R.id.comm_vp)
        var comm_mrp = itemView.findViewById<TextView>(R.id.comm_mrp)

    }
}