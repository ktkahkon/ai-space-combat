package org.spacecombat

class SpaceCraft(val maxEnergy: Double = 1800.0, val energyRecoveryRate: Double = 0.5, val turnRate: Double = 2.5, val accelerationRate: Double = 0.04) extends MovableObject {
  var energy = maxEnergy
  var mainThrustersAreOn = false
  var breakingThrustersAreOn = false
  var isAlive = true
  var isPlayerControlled = false

  velocity.topSpeed = 3.2
  angle = 0.0

  def update() {
    energy += energyRecoveryRate

    if (energy > maxEnergy)
      energy = maxEnergy

    if (mainThrustersAreOn)
      velocity.update(accelerationRate, angle)

    if (breakingThrustersAreOn)
      velocity.update(-accelerationRate, angle)

    updatePosition()
  }
}
