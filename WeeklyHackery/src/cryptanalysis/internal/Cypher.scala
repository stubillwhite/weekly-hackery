package cryptanalysis.internal

trait Key {}

trait Cypher[K <: Key] {
  def encypher(key: K, plaintext: String): String
  def decypher(key: K, cyphertext: String): String
}
