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
          drawSpaceCraft(spaceCraft, Color.BLUEVIOLET)
          drawEnergyBar(spaceCraft.energy, spaceCraft.maxEnergy, Color.GREEN)
        }
        else {
          drawSpaceCraft(spaceCraft, Color.GREENYELLOW)
          if (!GameWorld.humanPlayerIsInGame)
            drawMiniEnergyBar(spaceCraft, Color.YELLOW)
        }
      }
    }

    for (spaceCraft <- GameWorld.teamBeta.members)
      if (spaceCraft.isAlive) {
        drawSpaceCraft(spaceCraft, Color.ROSYBROWN)
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
  }

  private def drawSpaceCraft(craft: SpaceCraft, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(craft.position.x, craft.position.y)
    graphicContext.rotate(craft.angle)
    graphicContext.fillRect(-10, -10, 20, 20)
    graphicContext.fillRect(0, -5, 17, 10)
    graphicContext.setTransform(trans)
  }

  private def drawBullet(bullet: Bullet, color: Color) {
    val trans = graphicContext.getTransform
    graphicContext.setFill(color)
    graphicContext.translate(bullet.position.x, bullet.position.y)
    graphicContext.fillRect(-3, -3, 3, 3)
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
    graphicContext.fillRect(-6, -6, 6, 6)
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
