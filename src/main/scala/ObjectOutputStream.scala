import java.io.{FileOutputStream, ObjectOutputStream, OutputStream}

class ObjectOutputStreamScala(out: OutputStream) extends ObjectOutputStream(out) {
  def this(fileName: String) {
    this(new FileOutputStream(fileName))
  }

  def this(file: java.io.File) = {
    this(new FileOutputStream(file))
  }

  override def writeStreamHeader(): Unit = {
    //Don't do anything
  }
}
