package org.spacecombat

import scala.collection.mutable

object GameWorld {
  val leftWall = 0
  val rightWall = 1280
  val topWall = 0
  val bottomWall = 720
  var teamAlpha: Team = null
  var teamBeta: Team = null
  val projectilesTeamAlpha = new mutable.HashSet[Projectile]
  val projectilesTeamBeta = new mutable.HashSet[Projectile]
  val projectilesToBeRemoved = new mutable.HashSet[Projectile]
  val bombExplosions = new mutable.HashSet[BombExplosion]()
  var humanPlayerIsInGame = false
  val explosionsToBeRemoved = new mutable.HashSet[BombExplosion]

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

    for (projectile <- projectilesToBeRemoved) {
      projectilesTeamAlpha -= projectile
      if (projectile.isInstanceOf[Bomb]) {
        bombExplosions.add(new BombExplosion(projectile.position))
      }
    }
    projectilesToBeRemoved.clear()


    for (projectile <- projectilesTeamBeta) {
      projectile.update()
      if (collidingWithWalls(projectile) || !projectile.isAlive() || collidingWithTeam(projectile, teamAlpha))
        projectilesToBeRemoved += projectile
    }

    for (projectile <- projectilesToBeRemoved) {
      projectilesTeamBeta -= projectile
      if (projectile.isInstanceOf[Bomb]) {
        bombExplosions.add(new BombExplosion(projectile.position))
      }
    }
    projectilesToBeRemoved.clear()


    for (explosion <- bombExplosions) {
      if (!explosion.update())
        explosionsToBeRemoved += explosion
    }

    for (e <- explosionsToBeRemoved)
      bombExplosions -= e
    explosionsToBeRemoved.clear()
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
    var colliding = false
    for (member <- team.members) {
      if (member.isAlive) {
        if (member.distanceTo(projectile) < member.radius) {
          member.takeDamage(projectile.damage)
          colliding = true
        }
        else {
          projectile match {
            case b: Bomb =>
              val distance = member.distanceTo(projectile)
              if (distance < member.radius + b.blastRadius) {
                if (!bombCanGetCloser(b, member)) {
                  val modifier =
                    if (distance < member.radius + (b.blastRadius / 3))
                      1.0
                    else
                      distance / (member.radius + b.blastRadius)
                  val damage = projectile.damage * modifier
                  member.takeDamage(damage.toInt)
                  colliding = true
                }
              }
            case _ =>
          }
        }
      }
    }

    colliding
  }

  private def bombCanGetCloser(bomb: Bomb, obj: MovableObject): Boolean = {
    val dummyBomb = new Bomb
    dummyBomb.position.x = bomb.position.x
    dummyBomb.position.y = bomb.position.y
    dummyBomb.velocity.x = bomb.velocity.x
    dummyBomb.velocity.y = bomb.velocity.y
    dummyBomb.updatePosition()

    val distance1 = obj.distanceTo(bomb)
    val distance2 = obj.distanceTo(dummyBomb)
    distance2 < distance1
  }
}
