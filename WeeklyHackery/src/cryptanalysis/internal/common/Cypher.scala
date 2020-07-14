package cryptanalysis.internal.common

trait Cypher[K <: Key] {
  def encypher(plaintext: String): String
  def decypher(cyphertext: String): String
}
