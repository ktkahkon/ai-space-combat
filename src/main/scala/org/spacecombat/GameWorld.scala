package org.spacecombat

import scala.collection.mutable
import scala.util.Random

class GameWorld(val teamAlpha: Team, val teamBeta: Team) {
  require(!teamBeta.hasHumanPlayer, "The human player must be on team alpha!")

  val bullets = new mutable.HashSet[Bullet]
  val bombs = new mutable.HashSet[Bomb]
  val random = new Random()

  randomizeStartingPositions()

  def update() {
    teamAlpha.update()
    teamBeta.update()

    for (bullet <- bullets)
      bullet.updatePosition()

    for (bomb <- bombs)
      bomb.updatePosition()
  }

  private def randomizeStartingPositions() {
    for (member <- teamAlpha.members) {
      member.position.x = random.nextInt(1000) + 100
      member.position.y = random.nextInt(600) + 80
    }

    for (member <- teamBeta.members) {
      member.position.x = random.nextInt(1000) + 100
      member.position.y = random.nextInt(600) + 80
    }
  }

}
