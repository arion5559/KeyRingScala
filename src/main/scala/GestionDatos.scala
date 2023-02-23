import java.io.{BufferedReader, BufferedWriter, File, FileNotFoundException, FileOutputStream, FileReader, FileWriter, IOException, InputStreamReader};

object GestionDatos {
  def leerFichero(fichero: String): String = {
    var f: File = null
    var br: BufferedReader = null
    var devolver: String = ""

    try {
      f = new File(fichero)

      br = new BufferedReader(new FileReader(f))

      var linea: String = br.readLine()
      while (linea != null) {
        devolver += linea + "\n"
        linea = br.readLine()
      }

      br.close()
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }

    devolver
  }

  def escribirFichero(fichero: String, info: String) = {
    var f: File = null
    var fw: FileWriter = null

    try {
      f = new File(fichero)

      if (!f.exists()){
        f.createNewFile()
      }

      fw = new FileWriter(f, true)

      fw.write(info)

      fw.flush()

      fw.close()

    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }
  }
}
