package com.example.registrodesignosvitales

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MenuPrincipal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout;
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)

        val toolbar: Toolbar = findViewById(R.id.toolbar_PerfilPrincipal)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawe_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigator_drawer_open, R.string.navigator_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val buttonRegistro = findViewById<Button>(R.id.buttonRegistro)
        val imagenButtonPerfil = findViewById<ImageButton>(R.id.imagenButtonPerfil)
        val edadUsuario = findViewById<TextView>(R.id.edadUsuario)
        val fechaDeNacimiento = findViewById<TextView>(R.id.fechaDeNacimiento)
        val Peso = findViewById<TextView>(R.id.Peso)
        val estatura = findViewById<TextView>(R.id.estatura)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        buttonRegistro.setOnClickListener(){
            showAlertDialog()
        }

        imagenButtonPerfil.setOnClickListener(){
            Toast.makeText(this, "hola", Toast.LENGTH_LONG).show()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_salir -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_item_listas -> {
                val intent = Intent(this, listas::class.java)
                startActivity(intent)
            }
            R.id.nav_item_frecuenciaCardiaca -> Toast.makeText(this, "Presionado cardiaca", Toast.LENGTH_SHORT).show()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true;
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Registro de enfermedades")

            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.setPadding(16, 16, 16, 16)

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val textNombre = EditText(this)
            textNombre.hint = "Nombre"
            textNombre.inputType = InputType.TYPE_CLASS_TEXT
            textNombre.layoutParams = layoutParams
            layout.addView(textNombre)

            val edadUsuario = EditText(this)
            edadUsuario.hint = "Edad"
            edadUsuario.inputType = InputType.TYPE_CLASS_TEXT
            edadUsuario.layoutParams = layoutParams
            layout.addView(edadUsuario)

            val fechaNacimiento = EditText(this)
            fechaNacimiento.hint = "fecha de nacimiento"
            fechaNacimiento.inputType = InputType.TYPE_CLASS_TEXT
            fechaNacimiento.layoutParams = layoutParams
            layout.addView(fechaNacimiento)

            val pesoUsuaruario = EditText(this)
            pesoUsuaruario.hint = "Peso"
            pesoUsuaruario.inputType = InputType.TYPE_CLASS_TEXT
            pesoUsuaruario.layoutParams = layoutParams
            layout.addView(pesoUsuaruario)

            val estaturaUsuario = EditText(this)
            estaturaUsuario.hint = "Estatura"
            estaturaUsuario.inputType = InputType.TYPE_CLASS_TEXT
            estaturaUsuario.layoutParams = layoutParams
            layout.addView(estaturaUsuario)

            builder.setView(layout)

        builder.setPositiveButton("Guardar", null)

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog: DialogInterface, which: Int -> dialog.cancel() }

        val alertDialog = builder.create()

        alertDialog.setOnShowListener { dialog ->
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                Toast.makeText(this, "Listo", Toast.LENGTH_LONG).show()
            }
        }

        alertDialog.show()
    }

        private fun openGallery() {
            //galleryLauncher.launch("image/*")
        }
}