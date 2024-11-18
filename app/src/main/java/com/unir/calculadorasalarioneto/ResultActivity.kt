package com.unir.calculadorasalarioneto

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        //Recogemos el textView
        val tvSalarioBruto = findViewById<TextView>(R.id.resultadoSalarioBruto);
        val tvSalarioNeto = findViewById<TextView>(R.id.resultadoNeto);
        val tvIrpf = findViewById<TextView>(R.id.resultadoIrpf);
        val tvDeducciones = findViewById<TextView>(R.id.resultadoDeducciones);


        //Recogemos los putExtra de la actividad anterior
        @Suppress("DEPRECATION")
        val salarioData = intent.getSerializableExtra("salarioData") as? SalarioModel;
        println(salarioData.toString());


        tvSalarioBruto.text = salarioData!!.salarioBrutoMensual.toString();
        tvSalarioNeto.text = salarioData!!.salarioNetoMensual.toString();
        tvIrpf.text = salarioData!!.retenciones.toString();
        tvDeducciones.text = salarioData!!.deducciones.toString();


        // BOTÓN BACK:  para volver a la actividad principal
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.atras).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            intent.putExtra("salarioData", salarioData);
            intent.putExtra("restoreData", true);
            startActivity(intent);
        }
    }
}