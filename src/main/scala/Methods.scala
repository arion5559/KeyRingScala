import java.security.{KeyPairGenerator, MessageDigest}
import javax.crypto.{Cipher, KeyGenerator}
import scala.collection.mutable

object Methods {
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

      if (GestionDatos.leerFichero(ficheroEntrada).length == 0 || GestionDatos.leerFichero(ficheroEntrada) == null) {
        decryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())
      }

    } else if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKeys.getPublic)

      if (GestionDatos.leerFichero(ficheroEntrada).length == 0 || GestionDatos.leerFichero(ficheroEntrada) == null) {
        decryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())
      }

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

      if (GestionDatos.leerFichero(ficheroEntrada).length == 0 || GestionDatos.leerFichero(ficheroEntrada) == null) {
        encryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())
        GestionDatos.escribirFichero(ficheroSalida, new String(encryptedData))
      }

    } else if (contacto.getKeys != null) {
      c = Cipher.getInstance(contacto.getContactAlgorithm)
      c.init(Cipher.DECRYPT_MODE, contacto.getKeys.getPublic)

      if (GestionDatos.leerFichero(ficheroEntrada).length == 0 || GestionDatos.leerFichero(ficheroEntrada) == null) {
        encryptedData = c.doFinal(GestionDatos.leerFichero(ficheroEntrada).getBytes())
        GestionDatos.escribirFichero(ficheroSalida, new String(encryptedData))
      }

      if (sign) {
        firm(ficheroEntrada, contacto)
      }
    } else {
      println("No se ha encontrado el contacto")
    }
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

  def buscarContacto(nombre: String): Contacto = {
    var contactos: mutable.Buffer[Contacto] = null

    contactos = AlmacenarContacto.buscarContacto(nombre)

    if (contactos.size > 0) {
      println("Introduzca el número del contacto que desea seleccionar")
      var numContacto: Int = scala.io.StdIn.readInt()
      contactos(numContacto)
    }

    null
  }

  def verificarContactoNoExiste(nombre: String): Boolean = {
    AlmacenarContacto.verificarContactoNoExiste(nombre)
  }
}
