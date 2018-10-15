package playthedragon.internal

object Action extends Enumeration {
  type Action = Value
  val Attack, Buff, Cure, Debuff = Value

  val DragonActions = Set(Attack, Buff, Cure, Debuff)
  val KnightActions = Set(Attack)
}
