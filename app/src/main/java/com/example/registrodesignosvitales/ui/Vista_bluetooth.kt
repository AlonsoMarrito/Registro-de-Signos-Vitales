package com.example.registrodesignosvitales.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.registrodesignosvitales.R
import com.ingenieriajhr.blujhr.BluJhr

class Vista_bluetooth : AppCompatActivity() {

    lateinit var blue: BluJhr
    var devicesBluetooth: ArrayList<String> = ArrayList()
    lateinit var listDeviceBluetooth: ListView
    lateinit var viewConn: LinearLayout
    lateinit var consola: TextView
    lateinit var buttonSend: Button
    lateinit var edtTx: EditText
    lateinit var barra_deCarga: ProgressBar
    private lateinit var claseElegida: String


    private val requestBluetoothPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.BLUETOOTH] == true && permissions[Manifest.permission.BLUETOOTH_ADMIN] == true) {
            blue.initializeBluetooth()
        } else {
            Toast.makeText(this, "Se requieren permisos de Bluetooth para continuar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_bluetooth)
        var textoDeClase: TextView = findViewById(R.id.textoDeClase)
        claseElegida = intent.getStringExtra("clave_texto") ?: "Valor predeterminado"
        listDeviceBluetooth = findViewById(R.id.listDeviceBluetooth)
        viewConn = findViewById(R.id.viewConn)
        consola = findViewById(R.id.consola)
        buttonSend = findViewById(R.id.buttonSend)
        edtTx = findViewById(R.id.edtTx)
        barra_deCarga = findViewById(R.id.progressBar)
        barra_deCarga.indeterminateDrawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        textoDeClase.text = "Acerca de " + claseElegida

        blue = BluJhr(this)
        blue.onBluetooth()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestBluetoothPermissions.launch(arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION
            ))
        } else {
            blue.initializeBluetooth()
        }

        listDeviceBluetooth.setOnItemClickListener { adapterView, view, i, l ->
            if (devicesBluetooth.isNotEmpty()) {
                blue.connect(devicesBluetooth[i])
                blue.setDataLoadFinishedListener(object : BluJhr.ConnectedBluetooth {
                    override fun onConnectState(state: BluJhr.Connected) {
                        when (state) {
                            BluJhr.Connected.True -> {

                                listDeviceBluetooth.visibility = View.GONE
                                viewConn.visibility = View.VISIBLE
                                rxReceived() // Iniciar la recepción de datos
                            }
                            BluJhr.Connected.Pending -> {
                                Toast.makeText(applicationContext, "Conexión pendiente", Toast.LENGTH_SHORT).show()
                            }
                            BluJhr.Connected.False -> {
                                Toast.makeText(applicationContext, "Fallo en la conexión", Toast.LENGTH_SHORT).show()
                            }
                            BluJhr.Connected.Disconnect -> {
                                Toast.makeText(applicationContext, "Desconectado", Toast.LENGTH_SHORT).show()
                                listDeviceBluetooth.visibility = View.VISIBLE
                                viewConn.visibility = View.GONE
                            }
                        }
                    }
                })
            }
        }

        buttonSend.setOnClickListener {
            buttonSend.visibility = View.INVISIBLE
            consola.text = ""
            val message = edtTx.text.toString()
            if (message.isNotEmpty()) {
                // Mostrar ProgressBar
                barra_deCarga.visibility = View.VISIBLE

                // Enviar mensaje por Bluetooth
                blue.bluTx(message)

                // Ocultar ProgressBar después de 15 segundos
                Handler().postDelayed({
                    barra_deCarga.visibility = View.GONE
                    buttonSend.visibility = View.VISIBLE
                }, 14000) // 15000 ms = 15 segundos
            } else {
                Toast.makeText(this, "Por favor ingrese un mensaje para enviar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun rxReceived() {
        blue.loadDateRx(object : BluJhr.ReceivedData {
            override fun rxDate(rx: String) {
                if (rx.isNotEmpty()) {
                    // Limpiar la cadena para evitar caracteres no deseados
                    val cleanRx = rx.trim().replace("\n", "").replace("\r", "")

                    // Variable para almacenar el valor de BPM
                    var bpm: Float? = null

                    // Usar expresión regular para buscar el BPM
                    val regex = Regex("BPM:\\s*(\\d+)")
                    val matchResult = regex.find(cleanRx)
                    if (matchResult != null) {
                        bpm = matchResult.groupValues[1].toFloatOrNull()
                    }

                    // Si se ha recibido el valor de BPM, actualizamos el TextView
                    bpm?.let {
                        consola.text = "BPM: $it"
                    }

                    // Si no se recibe un valor válido de BPM, mostramos un mensaje de error

                } else {
                    // Si no se recibe dato válido, mostrar mensaje en consola
                    consola.text = "Esperando datos..."
                }
            }
        })
    }

    // Manejo de los permisos de Bluetooth
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            blue.initializeBluetooth()
        } else {
            Toast.makeText(this, "Permiso denegado, no se puede acceder a Bluetooth", Toast.LENGTH_SHORT).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Manejo de la respuesta del resultado de la actividad
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            if (blue.stateBluetoooth()) {
                devicesBluetooth = blue.deviceBluetooth() // Obtener la lista de dispositivos
                if (devicesBluetooth.isNotEmpty()) {
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, devicesBluetooth)
                    listDeviceBluetooth.adapter = adapter
                    listDeviceBluetooth.visibility = View.VISIBLE
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "No tienes dispositivos Bluetooth vinculados", Toast.LENGTH_SHORT).show()
                }
            } else {
                blue.initializeBluetooth()
            }
        }
    }
}
