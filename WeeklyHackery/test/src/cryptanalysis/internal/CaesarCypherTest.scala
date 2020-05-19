package cryptanalysis.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class CaesarCypherTest extends FlatSpec with Matchers with MockitoSugar {

  private val cypherKey: CaesarCypherKey = CaesarCypherKey(1)

  behavior of "encypher"

  it should "rotate text by the key value" in {
    // Given
    val cypher = CaesarCypher

    // When
    val actual = cypher().encypher(cypherKey, "abcdefghijklmnopqrstuvwxyz")

    // Then
    actual should be ("bcdefghijklmnopqrstuvwxyza")
  }

  behavior of "decypher"

  it should "rotate text by the key value negated" in {
    // Given
    val cypher = CaesarCypher

    // When
    val actual = cypher().decypher(cypherKey, "abcdefghijklmnopqrstuvwxyz")

    // Then
    actual should be ("zabcdefghijklmnopqrstuvwxy")
  }
}
