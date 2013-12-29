package org.spacecombat

class Bullet extends MovableObject {
  var currentLifeTime = 500

  def update() {
    currentLifeTime -= 1
    updatePosition()
  }

  def isAlive(): Boolean = currentLifeTime > 0
}
