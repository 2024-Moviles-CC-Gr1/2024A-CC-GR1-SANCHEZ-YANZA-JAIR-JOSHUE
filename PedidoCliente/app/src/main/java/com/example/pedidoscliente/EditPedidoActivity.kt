package com.example.pedidoscliente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.content.Intent


class EditPedidoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pedido)

        val pedidoName = intent.getStringExtra("pedido_name")
        val pedidoPosition = intent.getIntExtra("pedido_position", -1)

        val editText = findViewById<EditText>(R.id.et_pedido_name)
        editText.setText(pedidoName)

        findViewById<Button>(R.id.btn_save_pedido).setOnClickListener {
            val newPedidoName = editText.text.toString()
            val resultIntent = Intent().apply {
                putExtra("new_pedido_name", newPedidoName)
                putExtra("pedido_position", pedidoPosition)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}