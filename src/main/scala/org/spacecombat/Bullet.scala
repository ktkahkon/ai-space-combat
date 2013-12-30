package org.spacecombat

class Bullet extends MovableObject {
  var currentLifeTime = 500
  val damage = 80

  def update() {
    currentLifeTime -= 1
    updatePosition()
  }

  def isAlive(): Boolean = currentLifeTime > 0
}
