object Gestión {
  def main(args: Array[String]) {
    menu()
  }
  
  def menu(): Unit = {
    var opcion: Int = 0
    var salir: Boolean = false
    
    while (!salir) {
      println("1. Añadir contacto")
      println("2. Enviar mensaje")
      println("3. Recibir mensaje")
      println("4. Ver contactos")
      println("5. Buscar contacto")
      println("6. Salir")
      println("Introduzca una opción: ")
      opcion = scala.io.StdIn.readInt()
      
      opcion match {
        case 1 => añadirContacto()
        case 2 => enviarMensaje()
        case 3 => recibirMensaje()
        case 4 => verContactos()
        case 5 => buscarContacto()
        case 6 => salir = true
        case _ => println("Opción no válida")
      }
    }
  }

  def añadirContacto() {
    var nombre: String = ""
    var simOrAsim: Char = ' '
    var algorithm: String = ""
    var longitudLlaves: Int = 0

    // Verifica que no exista otro contacto con el mismo nombre
    do {
      println("Introduzca el nombre del contacto: ")
      nombre = scala.io.StdIn.readLine()

      if (Methods.buscarContacto(nombre) != null) {
        println("Ya existe un contacto con ese nombre")
      }
    } while (Methods.buscarContacto(nombre) != null)

    println("¿Es el contacto simétrico o asimétrico? (S/A)")
    simOrAsim = scala.io.StdIn.readChar()

    println("Introduzca el algoritmo de cifrado: ")
    algorithm = scala.io.StdIn.readLine()

    if (simOrAsim == 'S') {
      println("Introduzca la longitud de la llave: ")
      longitudLlaves = scala.io.StdIn.readInt()
    }

    Methods.create(simOrAsim, nombre, algorithm, longitudLlaves)
  }

  def enviarMensaje() {
    var nombre: String = ""
    var ficheroEntrada: String = ""
    var ficheroSalida: String = ""

    println("Introduzca el nombre del contacto: ")
    nombre = scala.io.StdIn.readLine()

    println("Introduzca el nombre del fichero de entrada: ")
    ficheroEntrada = scala.io.StdIn.readLine()

    println("Introduzca el nombre del fichero de salida: ")
    ficheroSalida = scala.io.StdIn.readLine()

    Methods.encrypt(nombre, ficheroEntrada, ficheroSalida)
  }

  def recibirMensaje() {
    var nombre: String = ""
    var ficheroEntrada: String = ""

    println("Introduzca el nombre del contacto: ")
    nombre = scala.io.StdIn.readLine()

    println("Introduzca el nombre del fichero de entrada: ")
    ficheroEntrada = scala.io.StdIn.readLine()

    Methods.decrypt(nombre, ficheroEntrada)
  }

  def verContactos() {
    Methods.verContactos()
  }

  def buscarContacto() {
    var nombre: String = ""

    println("Introduzca el nombre del contacto: ")
    nombre = scala.io.StdIn.readLine()

    Methods.buscarContacto(nombre)
  }
}
