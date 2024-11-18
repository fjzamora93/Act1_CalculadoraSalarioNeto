package com.unir.calculadorasalarioneto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import kotlin.math.roundToInt

// Heredamos de la clase Serializable para poder pasar esta clase entre actividades con el putExtra.
data class SalarioModel(
    var salarioBruto: Double = 0.0,
    var numeroPagas: Int = 12,
    var numeroHijos: Int = 0,
    var gradoDiscapacidad: Double = 0.0,

    // Atributos sin uso
    var edad: Int = 0,
    var grupoProfesional: Int = 0,
    var estadoCivil: String = "Soltero",

    // Atributos a calcular
    var deducciones: Double = 0.0,
    var retenciones: Double = 0.0,
    var tipo: Double = 0.0,
    var salarioBrutoMensual: Double = 0.0,
    var salarioNeto: Double = 0.0,
    var salarioNetoMensual: Double = 0.0,

) : Serializable {

    /**
     * Calcula y actualiza todos los datos salariales.
     * Llama al resto de funciones de la clase SalarioModel
     */
    fun calcularDatosSalario(){
        this.salarioBrutoMensual = redondear(salarioBruto/12);
        calcularRetenciones();
        calcularDeducciones();
        calcularNetoMensual();
        println("Datos recopilados:  $this");
    }

    /** Calcula y redondea el salario neto */
    fun calcularNetoMensual() {
        this.salarioNeto = redondear((this.salarioBruto - this.retenciones + this.deducciones));
        this.salarioNetoMensual = redondear((this.salarioNeto / 12));
    }

    /** Calcula las deducciones a partir del número de hijos y el grado de discapacidad*/
    fun calcularDeducciones() {
        val deduccionHijos:Double = this.numeroHijos * 50.00;
        val deduccionDiscapacidad: Double = (salarioBruto * 0.05) * gradoDiscapacidad;
        this.deducciones=  redondear(deduccionHijos + deduccionDiscapacidad);
        println("Deducciones calculadas: $this.deducciones");
    }

    /** Aplica las retenciones a partir del tipo calculado.
     * TODO: Incluir cualquier otro parámetro que se utilice en las deducciones aquí*/
    fun calcularRetenciones(){
        this.retenciones  = redondear(salarioBruto * clasificarTipo());
    }

    fun clasificarTipo(): Double {
        val tipo = when {
            salarioBruto <= 12000 -> 0.19
            salarioBruto <= 19999 -> 0.24
            salarioBruto <= 34999 -> 0.30
            salarioBruto <= 49999 -> 0.37
            salarioBruto <= 299999 -> 0.45
            else -> 0.47
        }
        println("Tipo calculado: ${tipo * 100}")
        return tipo
    }

    /** Multiplica por 100 y trunca la parte decimal, redondeado así a 2 decimales */
    fun redondear(numero:Double) : Double{
        return (numero*100).roundToInt() / 100.0;
    }
}