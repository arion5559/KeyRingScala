import java.security.KeyPair
import javax.crypto.Cipher
import javax.security

class Contacto (name: String, llaves: KeyPair, algorithm: String) {
  private var contactName: String = name
  private val keys:KeyPair = llaves
  private val contactAlgorithm: String = algorithm
}
