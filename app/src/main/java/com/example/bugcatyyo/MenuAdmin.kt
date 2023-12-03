package com.example.bugcatyyo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class MenuAdmin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_admin)

        auth = FirebaseAuth.getInstance()

        val serviceIntent = Intent(this, MusicaService::class.java)
        stopService(serviceIntent)
    }

    fun cerrarSesion(view: View) {
        auth.signOut()

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun MantenimientoProducto(view: View){
        startActivity(Intent(this, MantenimientoActivity::class.java))
    }

    fun ListadoVentas(view: View){
        startActivity(Intent(this,ListadoVentas::class.java))
    }


}