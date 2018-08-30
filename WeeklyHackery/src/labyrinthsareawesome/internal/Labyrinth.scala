package labyrinthsareawesome.internal

case class Labyrinth(width: Int,
                     height: Int,
                     passages: Set[Passage],
                     start: Room,
                     end: Room)