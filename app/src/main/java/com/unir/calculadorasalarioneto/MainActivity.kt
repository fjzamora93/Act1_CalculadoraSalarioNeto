package com.unir.calculadorasalarioneto
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    //Creacion de un companion object que es accesible desde todas las activities (equivalente a un atributo estático de Java)
    //Creo IMC_KEY para asignar el valor del extra en el intent
    companion object{
        const val SALARY_KEY = "SALARY_KEY"
    }

    // Vamos a encapsular todos los datos y operaciones dentro de nuestra clase SalarioModel
    lateinit var salarioData: SalarioModel;

    //Botones y otros elementos visuales
    private lateinit var resetButton:Button;
    private lateinit var calculateButton:Button;

    //Campos del formulario
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
        this.salarioData =  SalarioModel();

        //Definimos 3 metodos (tomemoslo como un buen patron)
        initComponents() //componentes visuales
        initListeners() // event listeners
        initUI() //Configuraciones visuales
    }


    //Buscamos los elementos visuales por su id y se los asignamos a las variables creadas al principio
    private fun initComponents() {
        this.salarioBrutoEditText = findViewById<EditText>(R.id.edtSalarioBrutoAnual);
        this.numeroPagasEditText = findViewById<EditText>(R.id.edtNumeroPagas);
        this.numeroHijosEditText = findViewById<EditText>(R.id.edtNumerHijos);
        this.gradoDiscapacidadEditText = findViewById<EditText>(R.id.edtGradoDiscapacidad);
        this.edadEditText = findViewById<EditText>(R.id.edtEdad);
        this.grupoProfesionalEditText = findViewById<EditText>(R.id.edtGrupoProfesional);
        this.estadoCivilEditText = findViewById<EditText>(R.id.edtEstadoCivil);

        // Inicialización de nuestros botones
        this.resetButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.reset);
        this.calculateButton = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.calcularButton);

    }

    //Generamos los listenners de los eventos que necesitamos
    private fun initListeners() {
        this.calculateButton.setOnClickListener {
            if (validateForm()){
                calculateResult();
                navigateToResult();
            }
        }
        this.resetButton.setOnClickListener {
            resetAll();
        }
    }


    //Funcion para configurar y actualizar los elementos visuales: cardview genero y textos peso y edad
    private fun initUI() {
        TODO("Not yet implemented")
        // Por ejemplo, si pinchamos un botón y queremos que visualmente se actualice, sería aquí.

    }



    // Función para retomar la actividad principal después de salir de ella
    override fun onResume() {
        super.onResume();
        println("Volvemos a la pantalla principal");

        // Recuperamos salarioData desde el Intent
        @Suppress("DEPRECATION")
        val salarioData = intent.getSerializableExtra("SALARY_KEY") as? SalarioModel;

        // Solo establecemos valores si salarioData no es nulo
        if (salarioData != null) {
            println("Recuperamos los datos previos: $salarioData");
            salarioBrutoEditText.setText(salarioData.salarioBruto.toString());
            numeroPagasEditText.setText(salarioData.numeroPagas.toString());
            numeroHijosEditText.setText(salarioData.numeroHijos.toString());
            gradoDiscapacidadEditText.setText(salarioData.gradoDiscapacidad.toString());
        } else {
            println("No se encontraron datos previos");
        }
    }



    private fun calculateResult() {
        // CAMPOS OBLIGATORIOS
        this.salarioData.salarioBruto = salarioBrutoEditText.text.toString().toDoubleOrNull()!!;
        this.salarioData.edad = edadEditText.text.toString().toIntOrNull()!!;
        this.salarioData.grupoProfesional = this.grupoProfesionalEditText.text.toString().toIntOrNull()!!;
        this.salarioData.estadoCivil = this.estadoCivilEditText.text.toString();

        // CAMPOS CON VALOR POR DEFECTO
        this.salarioData.numeroPagas = numeroPagasEditText.text.toString().toIntOrNull() ?: 12;
        this.salarioData.numeroHijos = numeroHijosEditText.text.toString().toIntOrNull() ?: 0;
        this.salarioData.gradoDiscapacidad =
            gradoDiscapacidadEditText.text.toString().toDoubleOrNull() ?: 0.0;

        // Llamamos a la función para realizar los cálculos
        salarioData.calcularDatosSalario();
    }

    //Funcion para navegar a la siguiente activity
    private fun navigateToResult() {
        val intent = Intent(this, ResultActivity::class.java);
        intent.putExtra("SALARY_KEY", this.salarioData);
        this.startActivity(intent);
    }


    /** Retorna true si los campos están validados correctamente */
    private fun validateForm(): Boolean {
        var isValid : Boolean = true;
        var camposObligatorios = arrayOf(
            this.salarioBrutoEditText.text.toString().toDoubleOrNull(),
            this.edadEditText.text.toString().toIntOrNull(),
            this.grupoProfesionalEditText.text.toString().toIntOrNull()
        );
        camposObligatorios.forEachIndexed { index, valor ->
            if (valor == null) {
                println("Valor nulo encontrado en el índice $index")
                this.salarioBrutoEditText.error = "Campo Obligatorio";
                this.edadEditText.error = "Campo obligatorio";
                this.grupoProfesionalEditText.error = "Campo obligatorio";
                isValid = false;
            }
        }
        return isValid;
    }


    /** Resetea todos los campos del formulario */
    private fun resetAll(){
        this.salarioBrutoEditText.setText("");
        this.numeroPagasEditText.setText("");
        this.gradoDiscapacidadEditText.setText("");
        this.numeroHijosEditText.setText("");
        this.grupoProfesionalEditText.setText("");
        this.edadEditText.setText("");
        println("Reseteando campos");

    }

}

