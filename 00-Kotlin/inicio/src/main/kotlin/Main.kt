import java.util.*
import kotlin.collections.ArrayList

fun main() {
    println("Hola mundo")
    //Inmutables (No se ouede reasugbar "=")
    val inmutable: String = "Adrian";
    //inmutable = "Vicente" // ERROR!
    //Mutables
    var mutable: String = "Vicente"
    mutable = "Adrian" //OK
    // val > var
    //Duck typing
    var ejemploVariable = "Adrian Eguez"
    var edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo // ERROR!
    // Variable primitivas
    val nombre: String = "Adrian"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'c'
    val mayorEdad: Boolean = true
    //Clases en Java
    val fechaNacimiento: Date = Date()
    //Switch -ªWhen
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    //Named parameters
    //calcularSueldo(sueldo,tasa,bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    //Uso de clases
    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    /////ESTATICO
    val arregloEstatico: Array <Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);

    //////DINAMICO
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    ////////OPERADORES
    ///RECORRER UN ARREGLO
    val respuestaForEach: Unit = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: ${valorActual }");
        }
    // it significa el elemento iterado
    arregloDinamico.forEach { println("Valor Actual (it): ${it}")}

    ////////OPERADOR MAP ESTE CAMBIA EL ARREGLO
    //// 1) envia el nuevo valor de la iteracion
    //// 2) nos devuelve el nuevo arreglo con valores

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble()+ 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)
}

//Funciones
//void-> unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}") //Template Strings
}
fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null //Opcional (nullable)
    // Variable?->"?" Es Nullable(osea que puede en algun momento ser nulo)
): Double {
    // Int ->Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}
abstract class Numeros(
    //Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int, (parametro (sin modificar acceso))
    //Caso 2) Parametro y propiedad (atributo) (private
    //private var uno:Int (propiedad "instancia.uno")
    protected val numeroUno: Int, //isntancia.numeroUno
    protected val numeroDos: Int, //instancia.numeroDos
    //parametroInutil:String, //PARAMETRO
) {
    init { //bloque constructor primario (opcional)
        this.numeroUno
        this.numeroDos
        //this.parametroInutil //ERROR NO EXISTE
        println("Inicializando")
    }
}

class Suma(
    //Constructor primario
    unoParametro: Int,
    dosParametro: Int,
) : Numeros(
    unoParametro,
    dosParametro
) {
    public val soyPublicoExplicito: String = "Explicito" //Publicos
    val soyPublicoImplicito: String = "Implicito" //Publicas (propiedades, metodos)
    init {
        this.numeroUno
        this.numeroDos
        //this.unoParametro //EROR NO EXISTE
        //this.unoPara
        numeroUno //this.OPCIONAL (propiedades, metodos)
        numeroDos //this.OPCIONAL (propiedades,metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito //this.opcional (propiedades,métodos)
    }
    constructor (//Constructor secundario
        uno:Int?,
        dos:Int
    ):this(
        if(uno==null) 0 else uno,
        dos
    )
    constructor (//Constructor tercero
        uno:Int,
        dos:Int?
    ):this(
        uno,
        if(dos==null) 0 else dos,
    )
    constructor (//Constructor cuarto
        uno:Int?,
        dos:Int?
    ):this(
        if(uno==null) 0 else uno,
        if(dos==null) 0 else dos,
    )

    // public fun sumar():Int (modificar "public" es OPCIONAL)
    fun sumar(): Int {
        val total = numeroUno + numeroDos
        //Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }

    //propiedad de una clase
    companion object { //Comparte entre todos
        //funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }

    }
}