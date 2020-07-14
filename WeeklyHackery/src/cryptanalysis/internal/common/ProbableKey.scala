package cryptanalysis.internal.common

case class ProbableKey[K <: Key](key: K, distance: Double)
