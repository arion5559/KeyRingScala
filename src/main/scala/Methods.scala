import java.security.{KeyPairGenerator, MessageDigest}
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
  def create(simOrAsim: Char, nombre: String, algorithm: String, longitudLlaves: Int = 0) = {
    var contacto: Contacto = null
    var keyGenerator: KeyGenerator = null
    var keyPairGenerator: KeyPairGenerator = null

    if (simOrAsim == 'S'){
      if (longitudLlaves > 0) {
        keyGenerator = KeyGenerator.getInstance(algorithm)
        keyGenerator.init(longitudLlaves)
      } else {
        keyGenerator = KeyGenerator.getInstance(algorithm)
      }
      contacto = new Contacto(name = nombre, llave = keyGenerator.generateKey(), algorithm = algorithm)
    } else if (simOrAsim == 'A') {
      if (longitudLlaves > 0) {
        keyPairGenerator = KeyPairGenerator.getInstance(algorithm)
        keyPairGenerator.initialize(longitudLlaves)
      } else {
        keyGenerator = KeyGenerator.getInstance(algorithm)
      }
      contacto = new Contacto(name = nombre, llaves = KeyPairGenerator.getInstance(algorithm).generateKeyPair(), algorithm = algorithm)
    }

    AlmacenarContacto.almacenarObjeto(contacto)
  }

  def decrypt(nombre: String, ficheroEntrada: String): Unit = {
    var contacto: Contacto = null
    var decryptedData: Array[Byte] = null
    var c: Cipher = null

    contacto = AlmacenarContacto.sacarObjeto(nombre)

    if (contacto.getKey != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKey)

      decryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())
    } else if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKeys.getPublic)

      decryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())

      verify(ficheroEntrada, contacto.getContactName)
    } else {
      println("No se ha encontrado el contacto")
    }

    if (decryptedData != null) {
      println("Mensaje descifrado: " + new String(decryptedData))
    }
  }

  def encrypt(sign: Boolean, nombre: String, ficheroEntrada: String, ficheroSalida: String): Unit = {
    var contacto: Contacto = null
    var c: Cipher = null
    var encryptedData: Array[Byte] = null
    var encryptedDataToString: String = null

    contacto = AlmacenarContacto.sacarObjeto(nombre)

    if (contacto.getKey != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKey)

      encryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())

      if (sign) {
        firm(ficheroEntrada, contacto)
      }

      GestionDatos.escribirFichero(ficheroSalida, new String(encryptedData))
    } else if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKeys.getPublic)

      encryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())

      GestionDatos.escribirFichero(ficheroSalida, new String(encryptedData))

      if (sign) {
        firm(ficheroEntrada, contacto)
      }
    } else {
      println("No se ha encontrado el contacto")
    }

    encryptedDataToString = new String(encryptedData)
    GestionDatos.escribirFichero(ficheroSalida, encryptedDataToString)
  }

  def firm(ficheroEntrada: String, contacto: Contacto) {
    var c: Cipher = null
    var firma: Array[Byte] = null
    var md: MessageDigest = null
    var firmaToString: String = null

    if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.ENCRYPT_MODE, contacto.getKeys.getPrivate)

      md = MessageDigest.getInstance(contacto.getContactAlgorithm)
      md.update(GestionDatos.leerFichero(ficheroEntrada).getBytes())

      firma = c.doFinal(md.digest())

      firmaToString = new String(firma)

      GestionDatos.escribirFichero(ficheroEntrada, firmaToString)
    } else {
      println("No se ha encontrado el contacto")
    }
  }

  def list(): Unit = {
    AlmacenarContacto.listarContactos()
  }

  def verify(nombre: String, ficheroEntrada: String): Unit = {
    var contacto: Contacto = null
    var c: Cipher = null
    var firma: Array[Byte] = null
    var md: MessageDigest = null
    var firmaToString: String = null

    contacto = AlmacenarContacto.sacarObjeto(nombre)

    if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKeys.getPublic)

      md = MessageDigest.getInstance(contacto.getContactAlgorithm)
      md.update(GestionDatos.leerFichero(ficheroEntrada).getBytes())

      firma = c.doFinal(md.digest())

      firmaToString = new String(firma)

      if (firmaToString.equals(GestionDatos.leerFichero(ficheroEntrada))) {
        println("La firma es correcta")
      } else {
        println("La firma no es correcta")
      }
    } else {
      println("No se ha encontrado el contacto")
    }
  }

  def verContactos() {
    AlmacenarContacto.listarContactos()
  }

  def buscarContacto(nombre: String) {
    AlmacenarContacto.buscarContacto(nombre)
  }
}
