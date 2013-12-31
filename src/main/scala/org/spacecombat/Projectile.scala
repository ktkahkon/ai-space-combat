package org.spacecombat

trait Projectile extends MovableObject {
  var currentLifeTime = 0
  var damage = 0

  def update() {
    currentLifeTime -= 1
    updatePosition()
  }

  def isAlive(): Boolean = currentLifeTime > 0
}
