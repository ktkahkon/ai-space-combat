package org.spacecombat

import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

class GameWorldVisualizer(canvas: Canvas) {
  val graphicContext = canvas.getGraphicsContext2D

  def draw() {
    graphicContext.clearRect(0, 0, 1280, 720)

    for (spaceCraft <- GameWorld.teamAlpha.members) {
      if (spaceCraft.isAlive) {
        if (spaceCraft.isPlayerControlled) {
          drawSpaceCraft(spaceCraft, Color.rgb(7,214,138))
          drawEnergyBar(spaceCraft.energy, spaceCraft.maxEnergy, Color.rgb(50, 100, 70, 0.7))
        }
        else {
          drawSpaceCraft(spaceCraft, Color.rgb(7,214,198))
          if (!GameWorld.humanPlayerIsInGame)
            drawMiniEnergyBar(spaceCraft, Color.YELLOW)
        }
      }
    }

    for (spaceCraft <- GameWorld.teamBeta.members)
      if (spaceCraft.isAlive) {
        drawSpaceCraft(spaceCraft, Color.rgb(167,25,75))
        if (!GameWorld.humanPlayerIsInGame)
          drawMiniEnergyBar(spaceCraft, Color.YELLOW)
      }

    for (projectile <- GameWorld.projectilesTeamAlpha) {
      projectile match {
        case bullet: Bullet => drawBullet(bullet, Color.WHITE)
        case bomb: Bomb => drawBomb(bomb, Color.LIGHTYELLOW)
        case _ =>
      }
    }

    for (projectile <- GameWorld.projectilesTeamBeta) {
      projectile match {
        case bullet: Bullet => drawBullet(bullet, Color.PALEVIOLETRED)
        case bomb: Bomb => drawBomb(bomb, Color.RED)
        case _ =>
      }
    }

    for (explosion <- GameWorld.bombExplosions) {
      drawExplosion(explosion)
    }
  }

  private def drawExplosion(explosion: BombExplosion) {
    val trans = graphicContext.getTransform
    var t: Double = (1.0 * explosion.time) / explosion.maxTime
    t = 1.0 - t
    val color = Color.rgb(250, 252, 254, t)
    graphicContext.setFill(color)
    graphicContext.translate(explosion.position.x, explosion.position.y)
    val size = explosion.time * 2.5
    val offset = size / 2
    graphicContext.fillOval(-offset, -offset, size, size)
    graphicContext.setTransform(trans)
  }

  private def drawSpaceCraft(craft: SpaceCraft, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(craft.position.x, craft.position.y)
    graphicContext.rotate(craft.angle)
    graphicContext.fillPolygon(Array(-6.0, -6.0, 10.0), Array(-7.0, 7.0, 0.0), 3)
    graphicContext.setTransform(trans)
  }

  private def drawBullet(bullet: Bullet, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(bullet.position.x, bullet.position.y)
    graphicContext.fillRect(-2, -2, 2, 2)
    graphicContext.setTransform(trans)
  }

  private def drawEnergyBar(energy: Double, maxEnergy: Double, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(140, 50)
    val length = (energy / maxEnergy) * 1000
    graphicContext.fillRect(0, 0, length, 10)
    graphicContext.setTransform(trans)
  }

  private def drawBomb(bomb: Bomb, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(bomb.position.x, bomb.position.y)
    graphicContext.fillOval(-2, -2, 4, 4)
    graphicContext.setTransform(trans)
  }

  private def drawMiniEnergyBar(craft: SpaceCraft, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(craft.position.x - 20, craft.position.y + 25)
    val length = (craft.energy / craft.maxEnergy) * 50
    graphicContext.fillRect(0, 0, length, 4)
    graphicContext.setTransform(trans)
  }
}
