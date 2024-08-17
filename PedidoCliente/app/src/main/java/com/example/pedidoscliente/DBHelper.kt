package com.example.pedidoscliente

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "clientes_pedidos.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_PEDIDOS = "pedidos"
        const val COLUMN_ID = "id"
        const val COLUMN_CLIENTE_NAME = "cliente_name"
        const val COLUMN_PEDIDO_NAME = "pedido_name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_PEDIDOS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_CLIENTE_NAME TEXT,"
                + "$COLUMN_PEDIDO_NAME TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PEDIDOS")
        onCreate(db)
    }

    fun addPedido(clienteName: String, pedidoName: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CLIENTE_NAME, clienteName)
        values.put(COLUMN_PEDIDO_NAME, pedidoName)
        db.insert(TABLE_PEDIDOS, null, values)
        db.close()
    }

    fun getPedidos(clienteName: String): List<String> {
        val productList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_PEDIDOS,
            arrayOf(COLUMN_PEDIDO_NAME),
            "$COLUMN_CLIENTE_NAME=?",
            arrayOf(clienteName),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                productList.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PEDIDO_NAME)))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return productList
    }

    fun updatePedido(clienteName: String, oldPedidoName: String, newPedidoName: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PEDIDO_NAME, newPedidoName)
        db.update(
            TABLE_PEDIDOS,
            values,
            "$COLUMN_CLIENTE_NAME=? AND $COLUMN_PEDIDO_NAME=?",
            arrayOf(clienteName, oldPedidoName)
        )
        db.close()
    }

    fun deletePedido(clienteName: String, pedidoName: String) {
        val db = this.writableDatabase
        db.delete(
            TABLE_PEDIDOS,
            "$COLUMN_CLIENTE_NAME=? AND $COLUMN_PEDIDO_NAME=?",
            arrayOf(clienteName, pedidoName)
        )
        db.close()
    }
}