import java.security.KeyPairGenerator
import javax.crypto.{Cipher, KeyGenerator}

object Methods {

  /*
  *create S|A nombre longitud algoritmo
  *create parámetro que indica que se desea crear un nuevo contacto
  *S|A parámetro que indica si se crea una llave simétrica o asimétrica
  *nombre parámetro que indica el nombre del contacto
  *longitud  longitud de la/s llave/s que se crean. Entero
  *algoritmo nombre del algoritmo a usar. Es un texto como por ejemplo "AES" o "RSA"
   */
  def create(simOrAsim: Char, nombre: String, algorithm: String) = {
    var contacto: Contacto = null

    if (simOrAsim == 'S'){
      contacto = new Contacto(name = nombre, llave = KeyGenerator.getInstance(algorithm).generateKey(), algorithm = algorithm)
    } else if (simOrAsim == 'A') {
      contacto = new Contacto(name = nombre, llaves = KeyPairGenerator.getInstance(algorithm).generateKeyPair(), algorithm = algorithm)
    }

    AlmacenarContacto.almacenarObjeto(contacto)
  }

  def decrypt(nombre: String, ficheroEntrada: String): Unit = {
    var contacto: Contacto = null

    contacto = AlmacenarContacto.sacarObjeto(nombre)

    if (contacto.getKey != null) {
      var c: Cipher = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKey)

      var encryptedData: Array[Byte] = c.doFinal()
    }
  }

  def encrypt(nombre: String, ficheroEntrada: String, ficheroSalida: String): Unit = {
    var contacto: Contacto = null
    var c: Cipher = null
    var encryptedData: Array[Byte] = null
    var encryptedDataToString: String = null

    contacto = AlmacenarContacto.sacarObjeto(nombre)

    if (contacto.getKey != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKey)

      encryptedData = c.doFinal()
    } else if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKeys.getPublic)

      encryptedData = c.doFinal()
    } else {
      println("No se ha encontrado el contacto")
    }

    encryptedDataToString = new String(encryptedData)
    GestionDatos.escribirFichero(ficheroSalida, encryptedDataToString)
  }

}
