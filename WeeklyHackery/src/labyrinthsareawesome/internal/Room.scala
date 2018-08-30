package labyrinthsareawesome.internal

case class Room(x: Int, y: Int) {
  def +(room: Room): Room = Room(x + room.x, y + room.y)
}
