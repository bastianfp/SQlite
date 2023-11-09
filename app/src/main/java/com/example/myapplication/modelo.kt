package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class UsuarioDAO(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun insertarUsuario(usuario: Usuario): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DatabaseHelper.COLUMN_RUT, usuario.rut)
        values.put(DatabaseHelper.COLUMN_NOMBRE, usuario.nombre)
        values.put(DatabaseHelper.COLUMN_CORREO, usuario.correo)
        return db.insert(DatabaseHelper.TABLE_NAME, null, values)
    }

    fun listarUsuarios(): ArrayList<Usuario> {
        val db = dbHelper.readableDatabase
        val usuarios = ArrayList<Usuario>()

        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            do {
                val rut = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RUT))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE))
                val correo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CORREO))
                usuarios.add(Usuario(rut, nombre, correo))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return usuarios
    }

    fun buscarUsuarioPorRut(rut: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${DatabaseHelper.TABLE_NAME} WHERE ${DatabaseHelper.COLUMN_RUT} = ?",
            arrayOf(rut)
        )

        return if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE))
            val correo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CORREO))
            cursor.close()
            Usuario(rut, nombre, correo)
        } else {
            cursor.close()
            null
        }
    }

    fun actualizarUsuario(usuario: Usuario): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(DatabaseHelper.COLUMN_NOMBRE, usuario.nombre)
        values.put(DatabaseHelper.COLUMN_CORREO, usuario.correo)

        return db.update(
            DatabaseHelper.TABLE_NAME,
            values,
            "${DatabaseHelper.COLUMN_RUT} = ?",
            arrayOf(usuario.rut)
        )
    }

    fun eliminarUsuarioPorRut(rut: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseHelper.TABLE_NAME,
            "${DatabaseHelper.COLUMN_RUT} = ?",
            arrayOf(rut)
        )
    }
}
