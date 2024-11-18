package com.unir.calculadorasalarioneto

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Recogemos el textView

        val tvSalarioBruto = findViewById<TextView>(R.id.resultadoSalarioBruto);
        val tvSalarioNeto = findViewById<TextView>(R.id.resultadoNeto);
        val tvIrpf = findViewById<TextView>(R.id.resultadoIrpf);
        val tvDeducciones = findViewById<TextView>(R.id.resultadoDeducciones);


        //Recoger los putExtra
        @Suppress("DEPRECATION")
        val salarioData = intent.getSerializableExtra("salarioData") as? SalarioModel;
        println(salarioData.toString());


        tvSalarioBruto.text = salarioData!!.salarioBrutoMensual.toString();
        tvSalarioNeto.text = salarioData!!.salarioNetoMensual.toString();
        tvIrpf.text = salarioData!!.retenciones.toString();
        tvDeducciones.text = salarioData!!.deducciones.toString();


        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.atras).setOnClickListener {
            println("Volviendo atrás");
            val intent = Intent(this, MainActivity::class.java);
            intent.putExtra("salarioData", salarioData);
            intent.putExtra("restoreData", true);
            startActivity(intent);
        }

    }
}