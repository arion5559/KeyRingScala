import java.io
import java.io.{File, FileInputStream, FileNotFoundException, FileOutputStream, IOException, ObjectInputStream, ObjectOutputStream}
import scala.collection.mutable

object AlmacenarContacto {
  def almacenarObjeto(guardar: Any, archivo: String = "..\\datos\\llavero.ser") = {
    var out: ObjectOutputStreamScala = null
    var outFirst: ObjectOutputStream = null
    var f: File = null

    try {
      f = new File(archivo)
      if (!f.exists()){
        f.createNewFile()
      }

      if (f.length() > 0){
        out = new ObjectOutputStreamScala(new FileOutputStream(f, true))
        out.writeObject(guardar)
      } else {
        outFirst = new ObjectOutputStream(new FileOutputStream(f))
        outFirst.writeObject(guardar)
      }

      out.flush()
      out.close()
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }
  }

  def sacarObjeto(nombre: String, archivo: String = "..\\datos\\llavero.ser"): Contacto = {
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

  def listarContactos(archivo: String = "..\\datos\\llavero.ser") {
    var devolver: String = ""
    var outer: ObjectInputStream = null
    var f: File = null
    var out: ObjectOutputStreamScala = null

    try {
      f = new File(archivo)

      outer = new ObjectInputStream(new FileInputStream(f))

      do {
        println(outer.readObject().asInstanceOf[Contacto].getContactName + "\n")
      }while(outer.available() > 0)
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }
  }

  def buscarContacto(nombre: String, archivo: String = "..\\datos\\llavero.ser"): mutable.Buffer[Contacto] = {
    var devolver: String = ""
    var outer: ObjectInputStream = null
    var f: File = null
    var contacto: Contacto = null
    var contactos: mutable.Buffer[Contacto] = mutable.Buffer[Contacto]()
    var contactosNum: Int = 0

    try {
      f = new File(archivo)

      outer = new ObjectInputStream(new FileInputStream(f))

      do {
        contacto = outer.readObject().asInstanceOf[Contacto]
        contactos += contacto
        devolver = contacto.getContactName
        if (devolver.equals(nombre)){
          contactosNum += 1
          println(contactosNum + ". " + devolver)
        }
      }while(outer.available() > 0)
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }

    if (devolver.equals("")){
      println("No se ha encontrado el contacto")
      contactos = null
    }

    contactos
  }

  def verificarContactoNoExiste(nombre: String, archivo: String = "..\\datos\\llavero.ser"): Boolean = {
    var devolver: String = ""
    var outer: ObjectInputStream = null
    var f: File = null
    var contactosNum: Int = 0

    try {
      f = new File(archivo)
      if (!f.exists()){
        f.createNewFile()
      }

      outer = new ObjectInputStream(new FileInputStream(f))

      do {
        devolver = outer.readObject().asInstanceOf[Contacto].getContactName
        if (devolver.equals(nombre)){
          contactosNum += 1
        }
      }while(outer.available() > 0)
    } catch {
      case fnfe: FileNotFoundException => println(fnfe.getMessage)
      case ioe: IOException => println(ioe.getMessage)
      case e: Exception => println(e.getMessage)
    }

    if (contactosNum == 0){
      true
    }else{
      false
    }
  }
}
