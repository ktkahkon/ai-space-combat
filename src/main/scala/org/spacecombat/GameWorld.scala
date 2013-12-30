package org.spacecombat

import scala.collection.mutable

object GameWorld {
  val leftWall = 30
  val rightWall = 1250
  val topWall = 40
  val bottomWall = 680
  var teamAlpha: Team = null
  var teamBeta: Team = null
  val bulletsTeamAlpha = new mutable.HashSet[Bullet]
  val bulletsTeamBeta = new mutable.HashSet[Bullet]
  val bombs = new mutable.HashSet[Bomb]
  val bulletsToBeRemoved = new mutable.HashSet[Bullet]

  def update() {
    teamAlpha.update()
    teamBeta.update()

    for (bullet <- bulletsTeamAlpha) {
      bullet.update()
      if (collidingWithWalls(bullet) || !bullet.isAlive() || collidingWithTeam(bullet, teamBeta))
        bulletsToBeRemoved += bullet
    }

    for (bullet <- bulletsToBeRemoved)
      bulletsTeamAlpha -= bullet
    bulletsToBeRemoved.clear()


    for (bullet <- bulletsTeamBeta) {
      bullet.update()
      if (collidingWithWalls(bullet) || !bullet.isAlive() || collidingWithTeam(bullet, teamAlpha))
        bulletsToBeRemoved += bullet
    }

    for (bullet <- bulletsToBeRemoved)
      bulletsTeamBeta -= bullet
    bulletsToBeRemoved.clear()
  }

  // TODO: make constant variables for game area bounds
  private def collidingWithWalls(obj: MovableObject): Boolean = {
    obj.position.x <= GameWorld.leftWall || obj.position.x >= GameWorld.rightWall || obj.position.y <= GameWorld.topWall || obj.position.y >= GameWorld.bottomWall
  }

  def addTeamAlpha(team: Team) {
    teamAlpha = team
  }

  def addTeamBeta(team: Team) {
    require(!team.hasHumanPlayer, "The human player must be on Team Alpha!")
    teamBeta = team
  }

  private def collidingWithTeam(bullet: Bullet, team: Team): Boolean = {
    for (member <- team.members) {
      if (member.isAlive) {
        if (member.distanceTo(bullet) < member.radius)
          return true
      }
    }

    false
  }
}
