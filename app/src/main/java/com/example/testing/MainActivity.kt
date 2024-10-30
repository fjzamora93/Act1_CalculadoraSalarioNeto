package com.example.testing

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

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

        // Referencias a los elementos de la interfaz para la suma
        val input1 = findViewById<TextInputEditText>(R.id.input1)
        val input2 = findViewById<TextInputEditText>(R.id.input2)
        val button = findViewById<Button>(R.id.button2)
        val output = findViewById<TextView>(R.id.textView2)

        // Configura el botón para que haga la suma
        button.setOnClickListener {
            // Obtén los valores de entrada como cadenas de texto
            val value1 = input1.text.toString()
            val value2 = input2.text.toString()

            // Convierte las entradas a números y realiza la suma
            val sum = (value1.toDoubleOrNull() ?: 0.0) + (value2.toDoubleOrNull() ?: 0.0)

            // Muestra el resultado en el TextView
            output.text = "Resultado: $sum"
        }

    }
}