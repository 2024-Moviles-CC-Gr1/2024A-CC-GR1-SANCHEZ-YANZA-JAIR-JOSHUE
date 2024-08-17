package com.example.pedidoscliente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.content.Intent

class EditClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cliente)

        val clienteName = intent.getStringExtra("cliente_name")
        val clientePosition = intent.getIntExtra("cliente_position", -1)
        val clienteLat = intent.getStringExtra("cliente_lat") // Clave correcta para la latitud
        val clienteLng = intent.getStringExtra("cliente_lon") // Clave correcta para la longitud

        val nameEditText = findViewById<EditText>(R.id.et_cliente_name)
        val latEditText = findViewById<EditText>(R.id.editLatitud)
        val lonEditText = findViewById<EditText>(R.id.editLongitud)

        nameEditText.setText(clienteName)
        latEditText.setText(clienteLat)
        lonEditText.setText(clienteLng)

        findViewById<Button>(R.id.btn_save_cliente).setOnClickListener {
            val newClienteName = nameEditText.text.toString()
            val newLat = latEditText.text.toString()
            val newLng = lonEditText.text.toString()

            val resultIntent = Intent().apply {
                putExtra("new_cliente_name", newClienteName)
                putExtra("cliente_position", clientePosition)
                putExtra("new_lat", newLat)
                putExtra("new_lon", newLng)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
