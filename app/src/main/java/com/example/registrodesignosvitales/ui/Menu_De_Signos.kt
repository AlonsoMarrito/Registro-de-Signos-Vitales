package com.example.registrodesignosvitales.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.registrodesignosvitales.R
import com.google.android.material.navigation.NavigationView

class Menu_De_Signos : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var buttonVerHistorial: Button
    lateinit var buttonToma: Button
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var claseElegida: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_de_signos)

        buttonVerHistorial = findViewById(R.id.buttonVerHistorial)
        buttonToma = findViewById(R.id.buttonConectar)


        claseElegida = intent.getStringExtra("clave_texto") ?: "Valor predeterminado"
        val textoDeClase: TextView = findViewById(R.id.textoDeClase)
        textoDeClase.text = "Conexion a dispositivos"

        buttonVerHistorial.setOnClickListener {
            val intent = Intent(this, Listas::class.java)
            startActivity(intent)
        }

        buttonToma.setOnClickListener {
            val intent = Intent(this, Vista_bluetooth::class.java)
            intent.putExtra("clave_texto", claseElegida)
            startActivity(intent)
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar_PerfilPrincipal)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawe_layout)
        toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigator_drawer_open,
            R.string.navigator_drawer_close
        )
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_salir -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
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
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
