import java.util.Date

fun main(){
    println("Hola mundo")
    //Inmutables (no se puede re asignar "=" )
    val inmutable: String = "Mateo"

    //Mutables
    var mutable: String = "Mateo"
    mutable = "Sebastian" //ok
    // VAL + VAR

    // Duck Typing
    var ejemploVariable = "Mateo Davalos"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()

    // Variable primitiva
    val nombre: String = "Mateo"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true
    // clases en JAVa
    val fechaNacimiento: Date = Date()

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        ("C") -> {
            println("casado")
        }
        "S" -> {
            println("soltero")
        }
        else -> {
            println("no sabemos")
        }
    }
    val esSoltero =(estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00, 20.00)
    calcularSueldo(sueldo, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00,tasa=14.00)


    abstract class NumerosJava{
        protected val numeroUno:Int
        private val numeroDos: Int
        constructor(
            uno:Int,
            dos:Int
        ){
            this.numeroUno = uno
            this.numeroDos = dos
            println("Inicializando")
        }
    }
        abstract class Numeros(
            protected val numeroUno: Int,
            protected val numeroDos: Int,
            //parametroInutil: String, // parametro
        ){
            init{
                this.numeroUno
                this.numeroDos
                println("Inicializando")
            }
        }


    class Suma(
    unoParametro: Int,
    dosParametro: Int,
    ): Numeros(
        unoParametro,
        dosParametro,
    ){
        public val soyPublicoExplicito: String = "Explicito"
        val soyPublicoImplicito: String = "Implicito"
        init {
            this.numeroUno
            this.numeroDos
            numeroUno // opcional poner this
            numeroDos
            this.soyPublicoExplicito
            soyPublicoImplicito
        }

        // public fun sumar
        fun sumar():Int{
            val total = numeroUno + numeroDos
            agregarHistorial(total)
            return total
        }
        companion object{
            val pi = 3.14
            fun elevarAlCuadrado(num:Int):Int{...}
            val historialSumas =  arrayListOf<Int>()
            fun agregarHistorial(valorTotalSuma:Int){
                historialSumas.add(valorTotalSuma)
            }
    }

}

// void Unit
fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}")
}
fun calcularSueldo(
    sueldo:Double,
    tasa: Double = 12.00,
    bonoEspecial:Double? = null
): Double{
    if (bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

