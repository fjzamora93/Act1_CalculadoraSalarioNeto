package com.unir.calculadorasalarioneto
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    var salarioBruto: Double? = null;
    var numeroPagas: Int? = null;
    var numeroHijos:Int? = 0;
    var gradoDiscapacidad:Double? = 0.0;

    // Declaramos una propiedad que vamos a inicializar más tarde (lateinit)
    lateinit var salarioData: SalarioModel;

    private lateinit var salarioBrutoEditText: EditText;
    private lateinit var numeroPagasEditText: EditText;
    private lateinit var numeroHijosEditText: EditText;
    private lateinit var gradoDiscapacidadEditText: EditText;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_main);

        // Asignamos un valor a todas nuesras propiedades en el OnCreate
        this.salarioData =  SalarioModel();
        this.salarioBrutoEditText = findViewById<EditText>(R.id.edtSalarioBrutoAnual);
        this.numeroPagasEditText = findViewById<EditText>(R.id.edtNumeroPagas);
        this.numeroHijosEditText = findViewById<EditText>(R.id.edtNumerHijos);
        this.gradoDiscapacidadEditText = findViewById<EditText>(R.id.edtGradoDiscapacidad);


        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.reset).setOnClickListener {
            this.salarioBrutoEditText.setText("");
            this.numeroPagasEditText.setText("");
            this.gradoDiscapacidadEditText.setText("");
            this.numeroHijosEditText.setText("");
            println("Reseteando campos");

        }


        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.calcularButton).setOnClickListener {

            this.numeroHijos = numeroHijosEditText.text.toString().toIntOrNull() ?: 0;
            this.gradoDiscapacidad = gradoDiscapacidadEditText.text.toString().toDoubleOrNull() ?: 0.0;
            var salarioBruto = salarioBrutoEditText.text.toString().toDoubleOrNull();
            val numeroPagas = numeroPagasEditText.text.toString().toIntOrNull();

            // Comprobamos que el salario bruto y el número de pagas NO son nulos
            if (salarioBruto != null && numeroPagas != null){
                salarioData.salarioBruto = salarioBruto as Double;
                salarioData.numeroPagas = numeroPagas;
                salarioData.numeroHijos = numeroHijos as Int;
                salarioData.gradoDiscapacidad = gradoDiscapacidad as Double;

                // Llamamos a la función para realizar los cálculos
                salarioData.calcularDatosSalario();

                //Navega a la siguiente View pasándole los datos que queremos llevar
                val intent = Intent(this, ResultActivity::class.java);
                intent.putExtra("salarioData", salarioData);
                println("Salario Data: " + salarioData.toString());
                startActivity(intent);

            } else {
                println("Estos campos son obligatorios");
                salarioBrutoEditText.error = "Campo Obligatorio";
                numeroPagasEditText.error = "Campo obligatorio";
            }
        }
    }

    override fun onResume() {
        super.onResume()
        println("Volvemos a la pantalla principal")

        val restoreData: Boolean = intent.getBooleanExtra("restoreData", false)

        // Recuperamos salarioData desde el Intent
        @Suppress("DEPRECATION")
        val salarioData = intent.getSerializableExtra("salarioData") as? SalarioModel

        if (salarioData != null) {
            println("Recuperamos los datos previos: $salarioData")

            // Solo establecemos valores si salarioData no es nulo
            salarioBrutoEditText.setText(salarioData.salarioBruto.toString())
            numeroPagasEditText.setText(salarioData.numeroPagas.toString())
            numeroHijosEditText.setText(salarioData.numeroHijos.toString())
            gradoDiscapacidadEditText.setText(salarioData.gradoDiscapacidad.toString())
        } else {
            println("No se encontraron datos previos")
        }
    }

}

