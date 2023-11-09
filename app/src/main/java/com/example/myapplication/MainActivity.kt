package com.example.myapplication

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etRut: EditText
    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnListar: Button
    private lateinit var btnBuscar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnEliminar: Button
    private lateinit var usuarioDAO: UsuarioDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etRut = findViewById(R.id.etRut)
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnListar = findViewById(R.id.btnListar)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnEliminar = findViewById(R.id.btnEliminar)
        usuarioDAO = UsuarioDAO(this)

        btnGuardar.setOnClickListener {
            val rut = etRut.text.toString()
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()

            if (rut.isNotEmpty() && nombre.isNotEmpty() && correo.isNotEmpty()) {
                val usuario = Usuario(rut, nombre, correo)
                val result = usuarioDAO.insertarUsuario(usuario)

                if (result != -1L) {
                    Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show()
                    etRut.text.clear()
                    etNombre.text.clear()
                    etCorreo.text.clear()
                } else {
                    Toast.makeText(this, "Error al guardar el usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }

        // ...

        btnListar.setOnClickListener {
            val usuarios = usuarioDAO.listarUsuarios()
            if (usuarios.isNotEmpty()) {
                val userList = StringBuilder()
                for (usuario in usuarios) {
                    userList.append("RUT: ${usuario.rut}\n")
                    userList.append("Nombre: ${usuario.nombre}\n")
                    userList.append("Correo: ${usuario.correo}\n\n")
                }

                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Lista de Usuarios")
                alertDialog.setMessage(userList.toString())
                alertDialog.setPositiveButton("Cerrar") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            } else {
                Toast.makeText(this, "No hay usuarios almacenados", Toast.LENGTH_SHORT).show()
            }
        }


        btnBuscar.setOnClickListener {
            val rut = etRut.text.toString()
            if (rut.isNotEmpty()) {
                val usuario = usuarioDAO.buscarUsuarioPorRut(rut)
                if (usuario != null) {
                    etNombre.setText(usuario.nombre)
                    etCorreo.setText(usuario.correo)
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingresa un RUT para buscar", Toast.LENGTH_SHORT).show()
            }
        }

        btnActualizar.setOnClickListener {
            val rut = etRut.text.toString()
            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()

            if (rut.isNotEmpty() && nombre.isNotEmpty() && correo.isNotEmpty()) {
                val usuario = Usuario(rut, nombre, correo)
                val rowsAffected = usuarioDAO.actualizarUsuario(usuario)

                if (rowsAffected > 0) {
                    Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            val rut = etRut.text.toString()
            if (rut.isNotEmpty()) {
                val rowsAffected = usuarioDAO.eliminarUsuarioPorRut(rut)

                if (rowsAffected > 0) {
                    Toast.makeText(this, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show()
                    etRut.text.clear()
                    etNombre.text.clear()
                    etCorreo.text.clear()
                } else {
                    Toast.makeText(this, "No se pudo eliminar el usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Ingresa un RUT para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
