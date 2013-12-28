package org.spacecombat

import scala.math._

class Velocity(var x: Double, var y: Double) {
  var topSpeed = 0.0

  def update(acceleration: Double, angle: Double) {
    x += cos(toRadians(angle)) * acceleration
    y += sin(toRadians(angle)) * acceleration

    val currentSpeed = sqrt(x*x + y*y)
    if (currentSpeed > topSpeed) {
      val ratio = currentSpeed / topSpeed
      x = x / ratio
      y = y / ratio
    }
  }
}
