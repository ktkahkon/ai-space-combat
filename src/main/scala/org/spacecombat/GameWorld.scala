package org.spacecombat

import scala.collection.mutable

object GameWorld {
  val leftWall = 30
  val rightWall = 1250
  val topWall = 40
  val bottomWall = 680
  var teamAlpha: Team = null
  var teamBeta: Team = null
  val projectilesTeamAlpha = new mutable.HashSet[Projectile]
  val projectilesTeamBeta = new mutable.HashSet[Projectile]
  val projectilesToBeRemoved = new mutable.HashSet[Projectile]

  // TODO: Handling bullets and bombs is similar. Refactor to common projectile/weapon handling.
  def update() {
    teamAlpha.update()
    teamBeta.update()
    updateProjectiles()
  }

  private def updateProjectiles() {
    for (projectile <- projectilesTeamAlpha) {
      projectile.update()
      if (collidingWithWalls(projectile) || !projectile.isAlive() || collidingWithTeam(projectile, teamBeta))
        projectilesToBeRemoved += projectile
    }

    for (projectile <- projectilesToBeRemoved)
      projectilesTeamAlpha -= projectile
    projectilesToBeRemoved.clear()


    for (projectile <- projectilesTeamBeta) {
      projectile.update()
      if (collidingWithWalls(projectile) || !projectile.isAlive() || collidingWithTeam(projectile, teamAlpha))
        projectilesToBeRemoved += projectile
    }

    for (projectile <- projectilesToBeRemoved)
      projectilesTeamBeta -= projectile
    projectilesToBeRemoved.clear()
  }

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

  private def collidingWithTeam(projectile: Projectile, team: Team): Boolean = {
    for (member <- team.members) {
      if (member.isAlive) {
        if (member.distanceTo(projectile) < member.radius) {
          member.takeDamage(projectile.damage)
          return true
        }
      }
    }

    false
  }
}
