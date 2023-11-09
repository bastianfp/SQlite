package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "miBaseDeDatos"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "Usuarios"
        const val COLUMN_RUT = "rut"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_CORREO = "correo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_RUT TEXT PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT," +
                "$COLUMN_CORREO TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    }



