package com.example.bugcatyyo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class VentasAdapter(private val context: Context, private val ventas: List<Venta>) : BaseAdapter() {

    override fun getCount(): Int {
        return ventas.size
    }

    override fun getItem(position: Int): Any {
        return ventas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.ventas_item, parent, false)

        val venta = getItem(position) as Venta

        val txtIdUsuario: TextView = rowView.findViewById(R.id.txt_id_usuario)
        val txtFechaUsuario: TextView = rowView.findViewById(R.id.txt_fecha_usuario)
        val txtMontoTotalUsuario: TextView = rowView.findViewById(R.id.txt_monto_total_usuario)

        txtIdUsuario.text = venta.idUsuario
        txtFechaUsuario.text = venta.fecha
        txtMontoTotalUsuario.text = venta.montoTotal

        return rowView
    }
}
