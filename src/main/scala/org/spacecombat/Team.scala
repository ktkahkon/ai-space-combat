package org.spacecombat

import org.spacecombat.AI.TeamAI
import scala.util.Random

class Team(val teamID: TeamIdentifier, val size: Int, val hasHumanPlayer: Boolean = false) {
  require(size > 0, "Team must have at least one member!")

  var teamAI: TeamAI = null
  var members: List[SpaceCraft] = Nil
  val random = new Random()

  for (i <- 0 until size)
    members = new SpaceCraft(teamID) :: members

  if (hasHumanPlayer)
    members.head.isPlayerControlled = true

  randomizeStartingPositions()

  def update() {
    teamAI.update()
    for (member <- members)
      member.update()
  }

  private def randomizeStartingPositions() {
    for (member <- members) {
      member.position.x = random.nextInt(1000) + 100
      member.position.y = random.nextInt(600) + 80
    }
  }
}
