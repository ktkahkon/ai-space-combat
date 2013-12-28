package org.spacecombat

trait MovableObject {
  val position = new Position(0.0, 0.0)
  var velocity = new Velocity(0.0, 0.0)
  var angle = 0.0
  var radius = 0.0

  def updatePosition() {
    position.x += velocity.x
    position.y += velocity.y

    if (position.x < 30) {
      position.x = 30
      velocity.x = -velocity.x * 0.7
    }
    else if (position.x > 1250) {
      position.x = 1250
      velocity.x = -velocity.x * 0.7
    }

    if (position.y < 40) {
      position.y = 40
      velocity.y = -velocity.y * 0.7
    }
    else if (position.y > 680) {
      position.y = 680
      velocity.y = -velocity.y * 0.7
    }
  }
}
