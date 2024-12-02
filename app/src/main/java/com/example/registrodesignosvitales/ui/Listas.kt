package com.example.registrodesignosvitales.ui

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
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
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.registrodesignosvitales.R
import com.example.registrodesignosvitales.todas_las_listas.Todas_Las_Tomas
import com.example.registrodesignosvitales.todas_las_listas.Tomas_FC
import com.example.registrodesignosvitales.todas_las_listas.Tomas_Spo2
import com.example.registrodesignosvitales.todas_las_listas.Tomas_Temperatura
import com.example.registrodesignosvitales.todas_las_listas.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Listas : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewPager2: ViewPager2
    private lateinit var madapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listas)

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
        viewPager2 = findViewById(R.id.viewPager)
        madapter = ViewPagerAdapter(this, lifecycle) // Cambiar de supportFragmentManager a this
        madapter.addFragment(Todas_Las_Tomas())
        madapter.addFragment(Tomas_FC())
        madapter.addFragment(Tomas_Spo2())
        madapter.addFragment(Tomas_Temperatura())
        viewPager2.adapter = madapter

        val tabLayout = findViewById<TabLayout>(R.id.ViewPagerAdapterListas)
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_todas)
                1 -> tab.setIcon(R.drawable.heartfc)
                2 -> tab.setIcon(R.drawable.spo2)
                3 -> tab.setIcon(R.drawable.temperaturetk)
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(Color.parseColor("#CE6520"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.icon?.setTint(Color.parseColor("#A9A9A9"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        viewPager2.currentItem = 0
        tabLayout.getTabAt(0)?.select()

        tabLayout.getTabAt(0)?.icon?.setTint(Color.parseColor("#CE6520"))
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                for (i in 0 until tabLayout.tabCount) {
                    val tab = tabLayout.getTabAt(i)
                    tab?.icon?.setTint(Color.parseColor("#A9A9A9"))  // Gris
                }
                val selectedTab = tabLayout.getTabAt(position)
                selectedTab?.icon?.setTint(Color.parseColor("#CE6520"))  // Naranja
            }
        })

        var buttonVerInicio: Button = findViewById<Button>(R.id.buttonVerInicio)

        buttonVerInicio.setOnClickListener(){
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }
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
