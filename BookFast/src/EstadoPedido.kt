sealed class EstadoPedido{
    object Pendiente : EstadoPedido(){
        override fun toString(): String = "Pendiente"
    }

    object EnProceso : EstadoPedido() {
        override fun toString(): String = "En Proceso"
    }

    object Listo : EstadoPedido() {
        override fun toString(): String = "Listo"
    }

    data class Error(val mensaje: String) : EstadoPedido(){
        override fun toString() = "Error: $mensaje"
        }
}

