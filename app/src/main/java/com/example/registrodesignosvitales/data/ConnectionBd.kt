package com.example.registrodesignosvitales.data

import java.sql.DriverManager

object ConnectionBd {

    @JvmStatic
    fun main(args: Array<String>) {
        //val connection = DriverManager.getConnection("jdbc:postgresql://35.215.115.254:5432/dbirpekbf3n8d2", "ugtnawr05tigg", "Amaranto1")
        val connection = DriverManager.getConnection("jdbc:postgresql://localhost/mdmr", "postgres", "Amaranth")
        connection.createStatement().use{ stmt ->
            val query = "Select * from perfil_usuario"
            stmt.executeQuery(query).use { rs ->
                while (rs.next()){
                    val apellido = rs.getString("nombre")
                    println("$apellido")
                }
            }
        }
    }

    fun getNombre(callback: (String) -> Unit) {
        Thread {
            try {
                Class.forName("org.postgresql.Driver")
                var apellido: String = ""

                val connection = DriverManager.getConnection("jdbc:postgresql://localhost/mdmr", "postgres", "Amaranth")

                connection.createStatement().use { stmt ->
                    val query = "SELECT * FROM perfil_usuario"
                    stmt.executeQuery(query).use { rs ->
                        while (rs.next()) {
                            apellido = rs.getString("nombre")
                            println("$apellido")
                        }
                    }
                }

                runOnUiThread {
                    callback(apellido)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }


    private fun runOnUiThread(function: () -> Unit) {

    }

    fun getNombre() {
        TODO("Not yet implemented")
    }

}