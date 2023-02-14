import java.io
import java.io.{File, FileInputStream, FileNotFoundException, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}

object AlmacenarContacto {
  def almacenarObjeto(guardar: Any, archivo: String = "src/main/datos\\llavero.txt") = {
    var out: ObjectOutputStreamScala = null
    var f: File = null

    try {
      f = new File(archivo)
      if (!f.exists()){
        f.createNewFile()
      }

      out = new ObjectOutputStreamScala(new FileOutputStream(f))

      out.writeObject(guardar)

      out.flush()
      out.close()
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }
  }

  def sacarObjeto(nombre: String, archivo: String = "src/main/datos\\llavero.txt"): Contacto = {
    var devolver: Contacto = null
    var outer: ObjectInputStream = null
    var f: File = null
    var out: ObjectOutputStreamScala = null

    try {
      f = new File(archivo)

      outer = new ObjectInputStream(new FileInputStream(f))

      do {
        devolver = outer.readObject().asInstanceOf[Contacto]
      }while(!devolver.getContactName.equals(nombre) && outer.available() > 0)

      if (!devolver.getContactName.equals(nombre)){
        devolver = null
      }
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }

    devolver
  }
}
