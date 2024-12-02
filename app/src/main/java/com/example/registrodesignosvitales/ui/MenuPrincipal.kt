package com.example.registrodesignosvitales.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.registrodesignosvitales.R
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

        toggle = ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigator_drawer_open,
            R.string.navigator_drawer_close
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        var buttonVerHistiarl: Button = findViewById<Button>(R.id.buttonVerHistorial)

        buttonVerHistiarl.setOnClickListener(){
            val intent = Intent(this, Listas::class.java)
            startActivity(intent)
        }

        var cardViewCorazon: CardView = findViewById(R.id.buttonEntrarAcardiacas)
        var cardViewSpo2: CardView = findViewById(R.id.buttonEntrarAspo2)
        var cardViewTemperatura: CardView = findViewById(R.id.buttonEntrarAtemperatura)
        var cardViewTodos: CardView = findViewById(R.id.buttonEntrarAtodos)
        var cardViewNosotros: CardView = findViewById(R.id.buttonEntrarNosotros)


        cardViewCorazon.setOnClickListener(){
            val intent = Intent(this, Menu_De_Signos::class.java)
            intent.putExtra("clave_texto", "mi frecuencia cardica")
            startActivity(intent)
        }

        cardViewSpo2.setOnClickListener(){
            val intent = Intent(this, Menu_De_Signos::class.java)
            intent.putExtra("clave_texto", "de mi saturacion de oxigeno")
            startActivity(intent)
        }

        cardViewTemperatura.setOnClickListener(){
            val intent = Intent(this, Menu_De_Signos::class.java)
            intent.putExtra("clave_texto", "de mi temperatura")
            startActivity(intent)
        }

        cardViewTodos.setOnClickListener(){
            val intent = Intent(this, Menu_De_Signos::class.java)
            intent.putExtra("clave_texto", "Todos mis signos")
            startActivity(intent)
        }

        cardViewNosotros.setOnClickListener(){
            val intent = Intent(this, Menu_De_Signos::class.java)
            intent.putExtra("clave_texto", "Ad Astra")
            startActivity(intent)
        }

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_item_salir -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
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

}