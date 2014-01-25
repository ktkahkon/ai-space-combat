package org.spacecombat

class BombExplosion(val position: Position) {
  var time = 0
  val maxTime = 28

  def update(): Boolean = {
    time += 1
    time < maxTime
  }
}
