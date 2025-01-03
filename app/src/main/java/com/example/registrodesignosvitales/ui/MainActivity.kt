package com.example.registrodesignosvitales.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.registrodesignosvitales.R
import com.example.registrodesignosvitales.data.ConnectionBd

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textUsuario = findViewById<EditText>(R.id.textUsuario)
        val textContraseña = findViewById<EditText>(R.id.textContraseña)
        val buttonCancelar = findViewById<Button>(R.id.buttonCancelar)
        val buttonFb = findViewById<Button>(R.id.buttonFb)
        val buttonInstagram = findViewById<Button>(R.id.buttonInstagram)
        val buttonChangeWindow = findViewById<Button>(R.id.buttonChangeWindow)

        textUsuario.visibility = View.GONE
        textContraseña.visibility = View.GONE
        buttonCancelar.visibility = View.GONE


        // Lógica para el botón Change Window
        buttonChangeWindow.setOnClickListener {
            // Toggle visibility de los campos de usuario/contraseña
            if (textContraseña.visibility == View.VISIBLE) {
                if (textUsuario.text.toString() == "Alonso" && textContraseña.text.toString() == "tiburoncin") {
                    // Si las credenciales son correctas, abre la siguiente ventana (MenuPrincipal)
                    val intent = Intent(this, MenuPrincipal::class.java)
                    startActivity(intent)
                } else if (textUsuario.text.toString() == "Alonso" && textContraseña.text.toString() != "tiburoncin") {
                    // Si la contraseña es incorrecta
                    textContraseña.setText("")
                    Toast.makeText(this, "Contraseña inválida", Toast.LENGTH_SHORT).show()
                } else {
                    textUsuario.setText("")
                    textContraseña.setText("")
                    Toast.makeText(this, "Usuario inexistente", Toast.LENGTH_SHORT).show()
                }
            }

            textUsuario.visibility = View.VISIBLE
            textContraseña.visibility = View.VISIBLE
            buttonCancelar.visibility = View.VISIBLE

            buttonFb.visibility = View.GONE
            buttonInstagram.visibility = View.GONE
        }

        buttonCancelar.setOnClickListener {
            textUsuario.visibility = View.GONE
            textContraseña.visibility = View.GONE
            buttonCancelar.visibility = View.GONE

            buttonFb.visibility = View.VISIBLE
            buttonInstagram.visibility = View.VISIBLE
        }
    }

}
