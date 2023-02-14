import java.io.{BufferedReader, File, FileNotFoundException, IOException, InputStreamReader};

object GestionDatos {
  def sacarInfo(fichero: String) = {
    var f: File = null
    var br: BufferedReader = null

    try {
      f = new File(fichero)

      br = new BufferedReader(new FileInputStreamReader(f))
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }

  }
}
