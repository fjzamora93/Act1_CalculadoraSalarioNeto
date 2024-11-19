package com.unir.calculadorasalarioneto

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class ResultActivity : AppCompatActivity() {

    private lateinit var tvSalarioBruto :TextView;
    private lateinit var tvSalarioNeto :TextView;
    private lateinit var tvIrpf :TextView;
    private lateinit var tvDeducciones :TextView;

    private lateinit var salarioData:SalarioModel;

    companion object{
        const val SALARY_KEY = "SALARY_KEY";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        //Recogemos los putExtra de la actividad anterior
        @Suppress("DEPRECATION")
        this.salarioData = intent.getSerializableExtra(SALARY_KEY) as? SalarioModel ?: SalarioModel();
        println(salarioData.toString());

        initComponents();
        initUI();



        // BOTÓN BACK:  para volver a la actividad principal
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.atras).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java);
            intent.putExtra(SALARY_KEY, salarioData);
            startActivity(intent);
        }
    }

    //Inicializamos los componentes
    private fun initComponents() {
        this.tvSalarioBruto = findViewById<TextView>(R.id.resultadoSalarioBruto);
        this.tvSalarioNeto = findViewById<TextView>(R.id.resultadoNeto);
        this.tvIrpf = findViewById<TextView>(R.id.resultadoIrpf);
        this.tvDeducciones = findViewById<TextView>(R.id.resultadoDeducciones);
    }

    private fun initUI(){
        tvSalarioBruto.text = salarioData!!.salarioBrutoMensual.toString();
        tvSalarioNeto.text = salarioData!!.salarioNetoMensual.toString();
        tvIrpf.text = salarioData!!.retenciones.toString();
        tvDeducciones.text = salarioData!!.deducciones.toString();
    }
}