ai-space-combat
===============

Artificial intelligence experiments for 2D space combat with Newtonian physics.

This game/AI experiment is build with Scala and JavaFX. To compile and run the program you need to have: 
* Java 7 JDK (or newer) 
* [sbt 0.13.1](http://www.scala-sbt.org/) (or newer)
* environment variable JAVA_HOME pointing to the JDK directory 

To run the program, move to the cloned repository and run command:

        sbt run

Game info
---------

Currently the game starts a 2 vs 2 match by default and one of the ships is controlled by a human player. Different game options will become available later. As the project is in its early stages, the current AI is just a placeholder (i.e., it is very simple).

Controlling the spacecraft
--------------------------

The controls are as follows:

* Up arrow: accelerate
* Left arrow: rotate left
* Right arrow: rotate right
* Down arrow: accelerate in the opposite direction
* Shift: Fire bullets
* Ctrl: fire bombs