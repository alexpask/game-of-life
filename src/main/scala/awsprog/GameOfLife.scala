package awsprog

import scala.util.Random

import javax.swing.JFrame

class GameOfLife(frame: Window) {
  
  val ymax                           = 100
  val xmax                           = 150
  val probabilityOfLife              = 0.08
  val generationInterval             = 100
  
  val numberOfNeighboursToCreateLife = 3
  val tooManyNeighbours              = 3
  val tooFewNeighbours               = 2
  
  val alive                          = true
  val dead                           = false
  
  def start() : Unit = {
    val firstGeneration = generateStartingGeneration()
    turn(firstGeneration)
  }
  
  def turn(generation: Generation) {
    Thread.sleep(generationInterval) 
    draw(generation)
    turn(next(applyRules(generation)))
  }

  def draw(generation: Generation) {
    frame.update(generation)
  }
  
  def next(generation: Generation): Generation = {
    new Generation(generation.land, generation.turn + 1) 
  }
  
  def applyRules(generation: Generation) : Generation = {
    val environment = generation.land.zipWithIndex.map { case (row, rowIndex) => 
      row.zipWithIndex.map { case (cell, columnIndex) =>
      val livingNeighbours = countLiveNeighbours(rowIndex,columnIndex,generation.land)
      if (cell.equals(alive))
        applyRulesToLivingCell(livingNeighbours)
      else
        applyRulesToDeadCell(livingNeighbours)
      }
    }
    new Generation(environment, generation.turn)
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
  
  def generateStartingGeneration() : Generation = {
      new Generation (createArray().map { y => y.map { x => generateLife() } }, 1)
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
