import kotlinx.coroutines.*;

data class Cliente(val tipo: String, val descuento: Double){
    companion object{
        fun fromString(tipo: String):Cliente? = when(tipo.lowercase()){
            "regular" -> Cliente("Regular", 0.05)
            "vip" -> Cliente("VIP", 0.10)
            "premium" -> Cliente("Premium", 0.15)
            else -> null
        }
    }
}

class BookFast(private val catalogo : List<Libro>){
    private val carrito = mutableListOf<Libro>()

    fun mostrarCatalogo(){
        println("Catalogo Disponible:")
        catalogo.forEachIndexed { index, libro ->
            println("${index+1} - ${libro.detalle()}")
        }
    }

    fun agregarAlCarrito(indices: List<Int>) : Boolean{
        try {
            for ( i in indices ){
                if (i in 1..catalogo.size){
                    carrito.add(catalogo[i - 1])
                }else{
                    throw IllegalArgumentException("Seleccion fuera del catalogo")
                }
            }
        }catch (e: Exception){
            var estado : EstadoPedido
            estado = EstadoPedido.Error(e.message ?: "Error Desconocido")
            println("Ocurrio un Error: ${(estado as EstadoPedido.Error).mensaje}")
            return false
        }
        return true
    }

    private fun calcularSubTotal(): Int = carrito.sumOf{ if(it is LibroFisico) it.precioTotal() else it.precio }

    suspend fun procesarPedido(cliente: Cliente){
        var estado: EstadoPedido = EstadoPedido.Pendiente
        println("\nProcesando $estado...")
        estado = EstadoPedido.EnProceso
        delay(2000)
        println("Estado: En $estado...")
        delay(2000)

        try {
            val subtotal = calcularSubTotal()
            val descuento = (subtotal*cliente.descuento).toInt()
            val iva = ((subtotal - descuento) * 0.19).toInt()
            val total = subtotal - descuento + iva

            estado = EstadoPedido.Listo

            println("\n=== RESUMEN DEL PEDIDO ===")
            carrito.forEach { println("- ${it.detalle()}") }
            println("Subtotal: $$subtotal")
            println("Descuento ${cliente.tipo}: -$$descuento")
            println("IVA (19%): $$iva ")
            println("Total: $$total")
            println("Estado Final: $estado")

        }catch (e: Exception){
            estado = EstadoPedido.Error(e.message ?: "Error Desconocido")
            println("Ocurrio un error: ${(estado as EstadoPedido.Error).mensaje}")
        }
    }
}