package org.spacecombat

import org.spacecombat.AI.TeamAI

class Team(val size: Int, val hasHumanPlayer: Boolean = false) {
  require(size > 0, "Team must have at least one member!")

  var teamAI: TeamAI = null
  var members: List[SpaceCraft] = Nil

  for (i <- 0 until size)
    members = new SpaceCraft() :: members

  if (hasHumanPlayer)
    members.head.isPlayerControlled = true

  def update() {
    teamAI.update()
    for (member <- members)
      member.update()
  }
}
