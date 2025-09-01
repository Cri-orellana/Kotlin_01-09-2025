open class Libro(val titulo: String,val precio: Int,val autor: String){
    init {
        require(value = precio > 0){"El precio no puede ser negativo (-)."}
    }
    open fun detalle(): String = "$titulo de $autor - $$precio"
}

class LibroFisico(titulo: String,precio: Int,autor: String,val costoEnvio: Int) : Libro(titulo,precio,autor){
    //init {
    //    require(value = costoEnvio > 0){"El precio no puede ser negativo (-)."}
    //}
    override fun detalle(): String {
        return "Fisico : $titulo de $autor - $$precio con precio envio $costoEnvio"
    }

    fun precioTotal() : Int = precio+costoEnvio
}

class LibroDigital(titulo: String,precio: Int,autor: String,val formato: String) : Libro(titulo,precio,autor){
    //init {
      //  require(value = formato "PDF" or "ePub"){"Solo puede ser formato PDF o ePub"}
    //}

    override fun detalle(): String {
        return "Digital : $titulo de $autor - $$precio formato $formato"
    }
}