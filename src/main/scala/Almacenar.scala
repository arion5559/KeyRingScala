import java.io
import java.io.{File, FileNotFoundException, FileOutputStream, ObjectOutputStream, IOException}

object Almacenar {
  def almacenarObjeto(guardar: Any, archivo: String = "datos\\llavero.txt") = {
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
      case fnfe: FileNotFoundException => fnfe.getMessage
      case ioe: IOException => ioe.getMessage
      case e: Exception => e.getMessage
    }
  }

  def sacarObjeto(nombre: String): Contacto = {
    var devolver: Contacto = null



    devolver
  }
}
