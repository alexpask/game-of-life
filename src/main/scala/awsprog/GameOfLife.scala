package awsprog

import scala.util.Random

import javax.swing.JFrame

class GameOfLife(frame: Window) {
  
  val ymax                           = 100
  val xmax                           = 150
  val probabilityOfLife                 = 0.08
  val generationInterval             = 100
  
  val numberOfNeighboursToCreateLife = 3
  val tooManyNeighbours              = 3
  val tooFewNeighbours               = 2
  
  val alive                          = true
  val dead                           = false
  
  def start() : Unit = {
    val environment = generateStartEnvironment()
    turn(environment, 1)
  }
  
  def turn(environment: Array[Array[Boolean]], generation: Int) {
    Thread.sleep(generationInterval) 
    draw(environment)
    turn(applyRules(environment),next(generation))
  }

  def draw(environment: Array[Array[Boolean]]) {
    frame.update(environment)
  }
  
  def next(generation: Int) : Int = generation + 1 
  
  def applyRules(environment: Array[Array[Boolean]]) : Array[Array[Boolean]] = {
    environment.zipWithIndex.map { case (row, rowIndex) => 
      row.zipWithIndex.map { case (cell, columnIndex) =>
      val livingNeighbours = countLiveNeighbours(rowIndex,columnIndex,environment)
      if (cell.equals(alive))
        applyRulesToLivingCell(livingNeighbours)
      else
        applyRulesToDeadCell(livingNeighbours)
      }
    }
  }
  
  def applyRulesToDeadCell(livingNeighbours: Int) : Boolean = {
    livingNeighbours == numberOfNeighboursToCreateLife
  }
  
  def applyRulesToLivingCell(livingNeighbours: Int) : Boolean = {
    (livingNeighbours >= tooFewNeighbours) && 
    (livingNeighbours <= tooManyNeighbours)
  }
  
  def countLiveNeighbours(y: Int, x: Int, environment: Array[Array[Boolean]]) : Int = {
    checkIsAlive(y, x-1,environment)   + 
    checkIsAlive(y, x+1,environment)   + 
    checkIsAlive(y-1, x-1,environment) + 
    checkIsAlive(y-1, x,environment)   + 
    checkIsAlive(y-1, x+1,environment) + 
    checkIsAlive(y+1, x-1,environment) +
    checkIsAlive(y+1, x,environment)   +
    checkIsAlive(y+1, x+1,environment)
  }
  
  def checkIsAlive(y: Int, x: Int, environment: Array[Array[Boolean]]) : Int = {
    if (withinBounds(y ,0, ymax) && 
        withinBounds(x ,0, xmax) && 
        environment(y)(x).equals(alive))
      1
    else 
      0
  }
  
  def withinBounds(coordinate: Int, min: Int, max: Int) : Boolean = {
    coordinate >= min && coordinate < max
  }

  def generateLife() : Boolean = {
    Random.nextFloat() < probabilityOfLife
  }
  
  def generateStartEnvironment() : Array[Array[Boolean]] = {
      createArray().map { y => y.map { x => generateLife() } }
  } 
  
  def createArray(): Array[Array[Boolean]] = Array.ofDim[Boolean](ymax,xmax)
}

object GameOfLife {
  def main(args: Array[String]) {
    val frame = new Window()
    val game = new GameOfLife(frame)
    game.start()
  }
}