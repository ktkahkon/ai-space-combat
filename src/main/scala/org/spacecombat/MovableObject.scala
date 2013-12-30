package org.spacecombat

trait MovableObject {
  val position = new Position(0.0, 0.0)
  var velocity = new Velocity(0.0, 0.0)
  var angle = 0.0
  var radius = 0.0

  def updatePosition() {
    position.x += velocity.x
    position.y += velocity.y

    if (position.x < GameWorld.leftWall) {
      position.x = GameWorld.leftWall
      velocity.x = -velocity.x * 0.7
    }
    else if (position.x > GameWorld.rightWall) {
      position.x = GameWorld.rightWall
      velocity.x = -velocity.x * 0.7
    }

    if (position.y < GameWorld.topWall) {
      position.y = GameWorld.topWall
      velocity.y = -velocity.y * 0.7
    }
    else if (position.y > GameWorld.bottomWall) {
      position.y = GameWorld.bottomWall
      velocity.y = -velocity.y * 0.7
    }
  }

  def distanceTo(secondObject: MovableObject): Double = {
    val dx = this.position.x - secondObject.position.x
    val dy = this.position.y - secondObject.position.y
    math.sqrt(dx*dx + dy*dy)
  }
}
