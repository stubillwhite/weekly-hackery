package cryptanalysis.internal

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class CaesarCypherTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "encypher"

  it should "rotate text by the key value" in {
    // Given
    val cypher = CaesarCypher(CaesarCypherKey(1))

    // When
    val actual = cypher.encypher("abcdefghijklmnopqrstuvwxyz")

    // Then
    actual should be ("bcdefghijklmnopqrstuvwxyza")
  }

  behavior of "decypher"

  it should "rotate text by the key value negated" in {
    // Given
    val cypher = CaesarCypher(CaesarCypherKey(1))

    // When
    val actual = cypher.decypher("abcdefghijklmnopqrstuvwxyz")

    // Then
    actual should be ("zabcdefghijklmnopqrstuvwxy")
  }
}
