package cryptanalysis.internal

case class ProbableKey[K <: Key](key: K, distance: Double)
