import java.security.KeyPair
import javax.crypto.Cipher
import javax.security

class Contacto (name: String, llaves: KeyPair) {
  private var contactName: String = name
  private val keys:KeyPair = llaves
}
