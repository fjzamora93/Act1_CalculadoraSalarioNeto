package com.unir.calculadorasalarioneto
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    // Si no se especifican, numero Hijos y grado de discapacidad se inicializarán en 0.
    // El resto de campos se inicializarán en nulo (vamos a forzar a que sean OBLIGATORIOS).
    var salarioBruto: Double? = null;
    var numeroPagas: Int? = 12;
    var numeroHijos:Int? = 0;
    var gradoDiscapacidad:Double? = 0.0;
    var edad : Int?= null;
    var grupoProfesional : Int? = null;
    var estadoCivil: String = "Soltero";

    // Declaramos las propiedades de nuestra actividad, pero usamos lateinit para indicar que las inicializaremos después.
    lateinit var salarioData: SalarioModel;
    private lateinit var salarioBrutoEditText: EditText;
    private lateinit var numeroPagasEditText: EditText;
    private lateinit var numeroHijosEditText: EditText;
    private lateinit var gradoDiscapacidadEditText: EditText;
    private lateinit var edadEditText:EditText;
    private lateinit var grupoProfesionalEditText:EditText;
    private lateinit var estadoCivilEditText:EditText;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        enableEdgeToEdge();
        setContentView(R.layout.activity_main);

        // Asignamos un valor a cada propiedad
        this.salarioData =  SalarioModel();
        this.salarioBrutoEditText = findViewById<EditText>(R.id.edtSalarioBrutoAnual);
        this.numeroPagasEditText = findViewById<EditText>(R.id.edtNumeroPagas);
        this.numeroHijosEditText = findViewById<EditText>(R.id.edtNumerHijos);
        this.gradoDiscapacidadEditText = findViewById<EditText>(R.id.edtGradoDiscapacidad);
        this.edadEditText = findViewById<EditText>(R.id.edtEdad);
        this.grupoProfesionalEditText = findViewById<EditText>(R.id.edtGrupoProfesional);
        this.estadoCivilEditText = findViewById<EditText>(R.id.edtEstadoCivil);


        // BOTÓN DE RESET: restablecemos los campos
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.reset).setOnClickListener {
            this.salarioBrutoEditText.setText("");
            this.numeroPagasEditText.setText("");
            this.gradoDiscapacidadEditText.setText("");
            this.numeroHijosEditText.setText("");
            this.grupoProfesionalEditText.setText("");
            this.edadEditText.setText("");
            println("Reseteando campos");

        }

        // BOTÓN DE CALCULAR: transición a la siguiente actividad
        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.calcularButton).setOnClickListener {
            // CAMPOS OBLIGATORIOS
            this.salarioBruto = salarioBrutoEditText.text.toString().toDoubleOrNull();
            this.edad = edadEditText.text.toString().toIntOrNull();
            this.grupoProfesional = this.grupoProfesionalEditText.text.toString().toIntOrNull();
            this.estadoCivil = this.estadoCivilEditText.text.toString()

            // CAMPOS CON VALOR POR DEFECTO
            this.numeroPagas = numeroPagasEditText.text.toString().toIntOrNull() ?: 12;
            this.numeroHijos = numeroHijosEditText.text.toString().toIntOrNull() ?: 0;
            this.gradoDiscapacidad = gradoDiscapacidadEditText.text.toString().toDoubleOrNull() ?: 0.0;

            if (validarFormulario())  {
                salarioData.salarioBruto = this.salarioBruto as Double;
                salarioData.numeroPagas = this.numeroPagas as Int;
                salarioData.numeroHijos = this.numeroHijos as Int;
                salarioData.gradoDiscapacidad = this.gradoDiscapacidad as Double;
                salarioData.edad = this.edad as Int;
                salarioData.estadoCivil = this.estadoCivil as String;
                salarioData.grupoProfesional = this.grupoProfesional as Int;

                // Llamamos a la función para realizar los cálculos
                salarioData.calcularDatosSalario();

                //Navega a la siguiente View pasándole los datos que queremos llevar
                val intent = Intent(this, ResultActivity::class.java);
                intent.putExtra("salarioData", salarioData);
                println("Salario Data: " + salarioData.toString());
                startActivity(intent);
            }
        }
    }

    // Función para remotar la actividad principal
    override fun onResume() {
        super.onResume()
        println("Volvemos a la pantalla principal")

        // Recuperamos salarioData desde el Intent
        @Suppress("DEPRECATION")
        val salarioData = intent.getSerializableExtra("salarioData") as? SalarioModel

        // Solo establecemos valores si salarioData no es nulo
        if (salarioData != null) {
            println("Recuperamos los datos previos: $salarioData")

            salarioBrutoEditText.setText(salarioData.salarioBruto.toString())
            numeroPagasEditText.setText(salarioData.numeroPagas.toString())
            numeroHijosEditText.setText(salarioData.numeroHijos.toString())
            gradoDiscapacidadEditText.setText(salarioData.gradoDiscapacidad.toString())
        } else {
            println("No se encontraron datos previos")
        }
    }

    fun validarFormulario(): Boolean {
        var validadorDeCampos : Boolean = true;
        var camposObligatorios = arrayOf(
            this.salarioBruto,
            this.edad,
            this.grupoProfesional
        );
        camposObligatorios.forEachIndexed { index, valor ->
            if (valor == null) {
                println("Valor nulo encontrado en el índice $index")
                salarioBrutoEditText.error = "Campo Obligatorio";
                edadEditText.error = "Campo obligatorio";
                grupoProfesionalEditText.error = "Campo obligatorio";
                validadorDeCampos = false;
            }
        }

        if (this.estadoCivil == "") estadoCivil = "Soltero";
        return validadorDeCampos;
    }

}

