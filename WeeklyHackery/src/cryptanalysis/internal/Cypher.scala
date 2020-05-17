package cryptanalysis.internal

trait Key {}

// TODO: Key should just be part of the method call
trait Cypher[T <: Key] {
  def encypher(plaintext: String): String
  def decypher(cyphertext: String): String
}
