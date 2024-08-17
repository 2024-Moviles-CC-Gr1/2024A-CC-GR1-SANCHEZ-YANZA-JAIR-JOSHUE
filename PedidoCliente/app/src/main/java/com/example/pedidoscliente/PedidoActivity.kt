package com.example.pedidoscliente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.activity.result.contract.ActivityResultContracts


class PedidoActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private val pedidos = mutableListOf<String>()
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var pedidoName: String

    private val editPedidoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val position = data?.getIntExtra("pedido_position", -1)
            val newName = data?.getStringExtra("new_pedido_name")
            if (position != null && position != -1 && newName != null) {
                val oldName = pedidos[position]
                dbHelper.updatePedido(pedidoName, oldName, newName)
                pedidos[position] = newName
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        dbHelper = DBHelper(this)
        pedidoName = intent.getStringExtra("cliente_name") ?: ""
        findViewById<TextView>(R.id.tv_cliente_name).text = pedidoName

        listView = findViewById(R.id.list_pedidos)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pedidos)
        listView.adapter = adapter

        findViewById<Button>(R.id.btn_create_pedido).setOnClickListener {
            createNewPedido()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            showPedidoOptions(position)
        }

        loadProducts()
    }

    private fun createNewPedido() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear nuevo pedido")

        val input = EditText(this)
        input.hint = "Nombre del producto del pedido"
        builder.setView(input)

        builder.setPositiveButton("Crear") { _, _ ->
            val pedidosName = input.text.toString()
            if (pedidosName.isNotEmpty()) {
                dbHelper.addPedido(pedidoName, pedidosName)
                pedidos.add(pedidosName)
                adapter.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun showPedidoOptions(position: Int) {
        val pedidoName = pedidos[position]
        val builder = AlertDialog.Builder(this)
        builder.setTitle(pedidoName)
        builder.setItems(arrayOf("Editar", "Eliminar")) { _, which ->
            when (which) {
                0 -> { // Editar
                    val intent = Intent(this, EditPedidoActivity::class.java).apply {
                        putExtra("pedido_name", pedidoName)
                        putExtra("pedido_position", position)
                    }
                    editPedidoLauncher.launch(intent)
                }
                1 -> { // Eliminar
                    dbHelper.deletePedido(pedidoName, pedidoName)
                    pedidos.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        builder.show()
    }

    private fun loadProducts() {
        pedidos.clear()
        pedidos.addAll(dbHelper.getPedidos(pedidoName))
        adapter.notifyDataSetChanged()
    }
}
