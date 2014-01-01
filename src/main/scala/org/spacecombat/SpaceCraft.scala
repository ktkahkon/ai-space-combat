package org.spacecombat

import scala.math._

class SpaceCraft(teamID: TeamIdentifier, val maxEnergy: Double = 1800.0, val energyRecoveryRate: Double = 1.5, val turnRate: Double = 2.5, val accelerationRate: Double = 0.04) extends MovableObject {
  var energy = maxEnergy
  var mainThrustersAreOn = false
  var breakingThrustersAreOn = false
  var isAlive = true
  var isPlayerControlled = false
  var firingBullets = false
  var firingBombs = false
  var currentBulletCoolDown = 0
  var currentBombCoolDown = 0
  val bulletCoolDownValue = 10
  val bombCoolDownValue = 50
  val energyCostToFireBullet = 30
  val energyCostToFireBomb = 100

  velocity.topSpeed = 4.5
  angle = 0.0
  radius = 12.0

  def update() {
    energy += energyRecoveryRate

    if (energy > maxEnergy)
      energy = maxEnergy
    else if (energy <= 0)
      isAlive = false

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

    if (currentBombCoolDown > 0)
      currentBombCoolDown -= 1

    if (firingBullets && currentBulletCoolDown == 0 && energy > energyCostToFireBullet) {
      currentBulletCoolDown = bulletCoolDownValue
      energy -= energyCostToFireBullet

      val bullet = new Bullet
      bullet.position.x = this.position.x
      bullet.position.y = this.position.y

      bullet.velocity.x = this.velocity.x + cos(toRadians(angle)) * 5.0
      bullet.velocity.y = this.velocity.y + sin(toRadians(angle)) * 5.0


      teamID match {
        case TeamAlpha => GameWorld.projectilesTeamAlpha += bullet
        case TeamBeta => GameWorld.projectilesTeamBeta += bullet
      }
    }

    // TODO: Similar code as above. Refactor.
    if (firingBombs && currentBombCoolDown == 0 && energy > energyCostToFireBomb) {
      currentBombCoolDown = bombCoolDownValue
      energy -= energyCostToFireBomb

      val bomb = new Bomb
      bomb.position.x = this.position.x
      bomb.position.y = this.position.y

      bomb.velocity.x = this.velocity.x + cos(toRadians(angle)) * 5.0
      bomb.velocity.y = this.velocity.y + sin(toRadians(angle)) * 5.0

      teamID match {
        case TeamAlpha => GameWorld.projectilesTeamAlpha += bomb
        case TeamBeta => GameWorld.projectilesTeamBeta += bomb
      }
    }
  }

  def takeDamage(damage: Int) {
    energy -= damage
    if (energy < 0) {
      isAlive = false
      if (isPlayerControlled)
        GameWorld.humanPlayerIsInGame = false
    }
  }
}
