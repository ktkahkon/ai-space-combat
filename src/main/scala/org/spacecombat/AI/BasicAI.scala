package org.spacecombat.AI

import org.spacecombat.{SpaceCraft, Team}
import scala.util.Random

class BasicAI(team: Team, opposingTeam: Team) extends TeamAI {
  val random = new Random()

  def update() {
    for (member <- team.members) {
      if (!member.isPlayerControlled) {
        val closestOpponent = findClosestOpponent(member)

        if (closestOpponent != None) {
          val dx = closestOpponent.get.position.x - member.position.x
          val dy = closestOpponent.get.position.y - member.position.y

          val angle = math.atan2(dy, dx) * 180 / math.Pi

          if (member.angle > angle)
            member.angle -= member.turnRate
          else
            member.angle += member.turnRate

          if (math.abs(member.angle - angle) < 3) {
            member.mainThrustersAreOn = true
            if (random.nextBoolean())
              member.firingBullets = true
            else
              member.firingBombs = true
          }
          else {
            member.mainThrustersAreOn = false
            member.firingBullets = false
            member.firingBombs = false
          }
        }
      }
    }
  }

  private def findClosestOpponent(craft: SpaceCraft): Option[SpaceCraft] = {
    var result: Option[SpaceCraft] = None

    for (opponent <- opposingTeam.members) {
      if (opponent.isAlive) {
        if (result == None)
          result = Some(opponent)
        else {
          val currentDelta = distanceBetween(craft, result.get)
          val newDelta = distanceBetween(craft, opponent)

          if (newDelta < currentDelta)
            result = Some(opponent)
        }
      }
    }

    result
  }


  private def distanceBetween(craft1: SpaceCraft, craft2: SpaceCraft): Double = {
    val dxCurrent = math.abs(craft1.position.x - craft2.position.x)
    val dyCurrent = math.abs(craft1.position.y - craft2.position.y)
    math.sqrt(dxCurrent * dxCurrent + dyCurrent * dyCurrent)
  }
}
