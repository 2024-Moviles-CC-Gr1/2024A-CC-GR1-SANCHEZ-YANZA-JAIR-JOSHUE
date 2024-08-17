package com.example.pedidoscliente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: ClienteDBHelper
    private val clientes = mutableListOf<Cliente>()
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<Cliente>

    private val editStoreLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val position = data?.getIntExtra("cliente_position", -1)
            val newName = data?.getStringExtra("new_cliente_name")
            val newLat = data?.getStringExtra("new_lat")
            val newLng = data?.getStringExtra("new_lon")
            if (position != null && position != -1 && newName != null && newLat != null && newLng != null) {
                val oldStore = clientes[position]
                val updatedStore = Cliente(oldStore.id, newName, newLat, newLng)
                dbHelper.updateCliente(updatedStore)
                clientes[position] = updatedStore
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ClienteDBHelper(this)

        listView = findViewById(R.id.lista_clientes)

        // Usar un ArrayAdapter con el layout personalizado
        adapter = object : ArrayAdapter<Cliente>(this, R.layout.list_item_cliente, R.id.cliente_nombre, clientes) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val cliente = getItem(position)
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_cliente, parent, false)
                val storeNameTextView = view.findViewById<TextView>(R.id.cliente_nombre)
                storeNameTextView.text = cliente?.name
                return view
            }
        }
        listView.adapter = adapter

        findViewById<Button>(R.id.btn_create_cliente).setOnClickListener {
            createNewStore()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            showStoreOptions(position)
        }

        loadStores()
    }

    private fun createNewStore() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registrar Nuevo Cliente")

        val inputName = EditText(this).apply {
            hint = "Nombre del Cliente"
        }

        val inputLat = EditText(this).apply {
            hint = "Latitud"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL or android.text.InputType.TYPE_NUMBER_FLAG_SIGNED
        }

        val inputLng = EditText(this).apply {
            hint = "Longitud"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL or android.text.InputType.TYPE_NUMBER_FLAG_SIGNED
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(inputName)
            addView(inputLat)
            addView(inputLng)
        }

        builder.setView(layout)

        builder.setPositiveButton("Crear") { _, _ ->
            val clienteName = inputName.text.toString()
            val clienteLat = inputLat.text.toString()
            val clienteLng = inputLng.text.toString()
            if (clienteName.isNotEmpty() && clienteLat.isNotEmpty() && clienteLng.isNotEmpty()) {
                val newCliente = Cliente(name = clienteName, lat = clienteLat, lng = clienteLng)
                dbHelper.addCliente(newCliente)
                clientes.add(newCliente)
                adapter.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun showStoreOptions(position: Int) {
        val cliente = clientes[position]
        val builder = AlertDialog.Builder(this)
        builder.setTitle(cliente.name)
        builder.setItems(arrayOf("Editar", "Eliminar", "Ver Pedidos", "Ver Dirección")) { _, which ->
            when (which) {
                0 -> { // Editar
                    val intent = Intent(this, EditClienteActivity::class.java).apply {
                        putExtra("cliente_name", cliente.name)
                        putExtra("cliente_position", position)
                        putExtra("cliente_lat", cliente.lat)
                        putExtra("cliente_lon", cliente.lng)
                    }
                    editStoreLauncher.launch(intent)
                }
                1 -> { // Eliminar
                    dbHelper.deleteCliente(cliente)
                    clientes.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
                2 -> { // Ver Productos
                    val intent = Intent(this, PedidoActivity::class.java).apply {
                        putExtra("cliente_name", cliente.name)
                    }

                    startActivity(intent)
                }
                3 -> { // Ver en el Mapa
                    val intent = Intent(this, MapsActivity::class.java).apply {
                        putExtra("cliente_name", cliente.name)
                        putExtra("cliente_lat", cliente.lat)
                        putExtra("cliente_lon", cliente.lng)
                    }
                    startActivity(intent)
                }
            }
        }
        builder.show()
    }

    private fun loadStores() {
        clientes.clear()
        clientes.addAll(dbHelper.getClientes())
        adapter.notifyDataSetChanged()
    }
}
