package org.spacecombat

import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color

class GameWorldVisualizer(world: GameWorld, canvas: Canvas) {
  val graphicContext = canvas.getGraphicsContext2D

  def draw() {
    graphicContext.clearRect(0, 0, 1280, 720)

    for (spaceCraft <- world.teamAlpha.members)
      drawSpaceCraft(spaceCraft, Color.GREENYELLOW)

    for (spaceCraft <- world.teamBeta.members)
      drawSpaceCraft(spaceCraft, Color.ROSYBROWN)
  }

  private def drawSpaceCraft(craft: SpaceCraft, color: Color) {
    val trans = graphicContext.getTransform()
    graphicContext.setFill(color)
    graphicContext.translate(craft.position.x, craft.position.y)
    graphicContext.rotate(craft.angle)
    graphicContext.fillRect(-10,-10,20,20)
    graphicContext.fillRect(0,-5,17,10)
    graphicContext.setTransform(trans)
  }
}
