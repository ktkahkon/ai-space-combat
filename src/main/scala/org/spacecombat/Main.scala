package org.spacecombat

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.{CacheHint, Scene, Group}
import javafx.scene.image.{ImageView, Image}
import javafx.animation.AnimationTimer
import javafx.event.EventHandler
import javafx.scene.input.{KeyCode, KeyEvent}
import org.spacecombat.AI.BasicAI
import javafx.scene.canvas.Canvas

object Main {
  def main(args: Array[String]) {
    Application.launch(classOf[Main], args: _*)
  }
}

class Main extends Application {
  override def start(primaryStage: Stage) {
    val root = new Group
    val scene = new Scene(root, 1280, 720)
    primaryStage.setScene(scene)
    primaryStage.setTitle("AI Experiment: Space Combat")

    val image = new Image("background1.jpg")
    val imageView = new ImageView()
    imageView.setImage(image)
    root.getChildren.add(imageView)

    val canvas = new Canvas(1280, 720)
    root.getChildren.add(canvas)

    primaryStage.show()

    val team1 = new Team(TeamAlpha, size = 4, hasHumanPlayer = true)
    val team2 = new Team(TeamBeta, size = 4)

    team1.teamAI = new BasicAI(team1, team2)
    team2.teamAI = new BasicAI(team2, team1)

    GameWorld.addTeamAlpha(team1)
    GameWorld.addTeamBeta(team2)

    val gameWorldVisualizer = new GameWorldVisualizer(canvas)

    val gameLoop = new AnimationTimer() {
      def handle(p1: Long) {
        GameWorld.update()

        if (team1.hasHumanPlayer)
          updatePlayer(team1.members.head)

        gameWorldVisualizer.draw()
      }
    }

    initializeKeyboardControls(team1, primaryStage)
    gameLoop.start()
  }

  private def updatePlayer(player: SpaceCraft) {
    if (Controls.left)
      player.angle -= player.turnRate

    if (Controls.right)
      player.angle += player.turnRate


    player.firingBullets = Controls.fireBullet
    player.mainThrustersAreOn = Controls.up
    player.breakingThrustersAreOn = Controls.down
  }

  private def initializeKeyboardControls(team1: Team, primaryStage: Stage) {
    val keyHandler = new EventHandler[KeyEvent] {
      def handle(event: KeyEvent) {
        val code = event.getCode

        if (code == KeyCode.LEFT && event.getEventType == KeyEvent.KEY_PRESSED) {
          Controls.left = true
        }
        else if (code == KeyCode.RIGHT && event.getEventType == KeyEvent.KEY_PRESSED) {
          Controls.right = true
        }
        else if (code == KeyCode.LEFT && event.getEventType == KeyEvent.KEY_RELEASED) {
          Controls.left = false
        }
        else if (code == KeyCode.RIGHT && event.getEventType == KeyEvent.KEY_RELEASED) {
          Controls.right = false
        }
        else if (code == KeyCode.UP && event.getEventType == KeyEvent.KEY_RELEASED) {
          Controls.up = false
        }
        else if (code == KeyCode.UP && event.getEventType == KeyEvent.KEY_PRESSED) {
          Controls.up = true
        }
        else if (code == KeyCode.DOWN && event.getEventType == KeyEvent.KEY_RELEASED) {
          Controls.down = false
        }
        else if (code == KeyCode.DOWN && event.getEventType == KeyEvent.KEY_PRESSED) {
          Controls.down = true
        }
        else if (code == KeyCode.SHIFT && event.getEventType == KeyEvent.KEY_PRESSED) {
          Controls.fireBullet = true
        }
        else if (code == KeyCode.SHIFT && event.getEventType == KeyEvent.KEY_RELEASED) {
          Controls.fireBullet = false
        }
      }
    }

    if (team1.hasHumanPlayer) {
      primaryStage.getScene.setOnKeyPressed(keyHandler)
      primaryStage.getScene.setOnKeyReleased(keyHandler)
    }
  }
}
