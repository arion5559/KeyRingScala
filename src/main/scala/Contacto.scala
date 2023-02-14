import java.security.KeyPair
import javax.crypto.{Cipher, SecretKey}
import javax.security

class Contacto (name: String, llaves: KeyPair = null,
                llave:SecretKey = null, algorithm: String) {
  private var contactName: String = name
  private val keys:KeyPair = llaves
  private val key:SecretKey = llave
  private val contactAlgorithm: String = algorithm

  def getContactName: String = contactName
  def setContactName(newName: String) = {
    contactName = newName
  }

  def getKeys: KeyPair = keys

  def getKey: SecretKey = key

  def getContactAlgorithm: String = algorithm
}
