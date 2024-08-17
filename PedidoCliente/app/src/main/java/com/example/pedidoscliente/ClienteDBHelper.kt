package com.example.pedidoscliente

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Cliente(var id: Long = -1, val name: String, val lat: String, val lng: String)

class ClienteDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cliente_manager.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_CLIENTES = "clientes"
        const val COLUMN_ID = "id"
        const val COLUMN_CLIENTE_NAME = "cliente_name"
        const val COLUMN_CLIENTE_LAT = "latitud"
        const val COLUMN_CLIENTE_LNG = "longitud"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_CLIENTES ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_CLIENTE_NAME TEXT,"
                + "$COLUMN_CLIENTE_LAT REAL,"
                + "$COLUMN_CLIENTE_LNG REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        onCreate(db)
    }

    fun addCliente(cliente: Cliente) {
        val db = this.writableDatabase
        try {
            val values = ContentValues().apply {
                put(COLUMN_CLIENTE_NAME, cliente.name)
                put(COLUMN_CLIENTE_LAT, cliente.lat.toDoubleOrNull())
                put(COLUMN_CLIENTE_LNG, cliente.lng.toDoubleOrNull())
            }
            val id = db.insert(TABLE_CLIENTES, null, values)
            cliente.id = id // Actualizar el ID del store con el valor insertado
        } catch (e: Exception) {
            e.printStackTrace() // Manejo básico de excepciones
        } finally {
            db.close()
        }
    }

    fun updateCliente(cliente: Cliente) {
        val db = this.writableDatabase
        try {
            val values = ContentValues().apply {
                put(COLUMN_CLIENTE_NAME, cliente.name)
                put(COLUMN_CLIENTE_LAT, cliente.lat.toDoubleOrNull())
                put(COLUMN_CLIENTE_LNG, cliente.lng.toDoubleOrNull())
            }
            db.update(
                TABLE_CLIENTES,
                values,
                "$COLUMN_ID=?",
                arrayOf(cliente.id.toString())
            )
        } catch (e: Exception) {
            e.printStackTrace() // Manejo básico de excepciones
        } finally {
            db.close()
        }
    }

    fun deleteCliente(cliente: Cliente) {
        val db = this.writableDatabase
        try {
            db.delete(
                TABLE_CLIENTES,
                "$COLUMN_ID=?",
                arrayOf(cliente.id.toString())
            )
        } catch (e: Exception) {
            e.printStackTrace() // Manejo básico de excepciones
        } finally {
            db.close()
        }
    }

    fun getClientes(): List<Cliente> {
        val clienteList = mutableListOf<Cliente>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CLIENTES,
            arrayOf(COLUMN_ID, COLUMN_CLIENTE_NAME, COLUMN_CLIENTE_LAT, COLUMN_CLIENTE_LNG),
            null,
            null,
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val clienteName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLIENTE_NAME))
                val lat = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CLIENTE_LAT)).toString()
                val lng = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CLIENTE_LNG)).toString()
                clienteList.add(Cliente(id, clienteName, lat, lng))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clienteList
    }
}

