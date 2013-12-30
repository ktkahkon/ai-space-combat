package org.spacecombat

import scala.math._

class SpaceCraft(teamID: TeamIdentifier, val maxEnergy: Double = 1800.0, val energyRecoveryRate: Double = 0.5, val turnRate: Double = 2.5, val accelerationRate: Double = 0.04) extends MovableObject {
  var energy = maxEnergy
  var mainThrustersAreOn = false
  var breakingThrustersAreOn = false
  var isAlive = true
  var isPlayerControlled = false
  var firingBullets = false
  var currentBulletCoolDown = 0
  val bulletCoolDownValue = 10
  var firingBombs = false

  velocity.topSpeed = 4.5
  angle = 0.0
  radius = 12.0

  def update() {
    energy += energyRecoveryRate

    if (energy > maxEnergy)
      energy = maxEnergy

    if (mainThrustersAreOn)
      velocity.update(accelerationRate, angle)

    if (breakingThrustersAreOn)
      velocity.update(-accelerationRate, angle)

    updateFiring()

    updatePosition()
  }

  private def updateFiring() {
    if (currentBulletCoolDown > 0)
      currentBulletCoolDown -= 1

    if (firingBullets && currentBulletCoolDown == 0) {
      currentBulletCoolDown = bulletCoolDownValue
      val bullet = new Bullet
      bullet.position.x = this.position.x
      bullet.position.y = this.position.y

      bullet.velocity.x = this.velocity.x + cos(toRadians(angle)) * 5.0
      bullet.velocity.y = this.velocity.y + sin(toRadians(angle)) * 5.0


      teamID match {
        case TeamAlpha => GameWorld.bulletsTeamAlpha += bullet
        case TeamBeta => GameWorld.bulletsTeamBeta += bullet
      }
    }
  }
}
