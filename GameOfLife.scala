import scala.util.Random

class GameOfLife {
  
  val ymax                           = 30
  val xmax                           = 80
  val seed                           = 0.1
  val generationInterval             = 100
  
  val numberOfNeighboursToCreateLife = 3
  val tooManyNeighbours              = 3
  val tooFewNeighbours               = 2
  
  val alive                          = "X"
  val dead                           = "." 
  
  def start() : Unit = {
    val environment = generateStartEnvironment()
    turn(environment, 1)
  }
  
  def turn(environment: Array[Array[String]], generation: Int) {
    Thread.sleep(generationInterval) 
    draw(environment, generation)
    turn(applyRules(environment),next(generation))
  }

  def draw(environment: Array[Array[String]], generation: Int) {
    println("Generation: " + generation)
    environment.foreach { y => y.foreach { print }
    println
    }
    println  
    println
    println
  }
  
  def next(generation: Int) : Int = generation + 1 
  
  def applyRules(environment: Array[Array[String]]) : Array[Array[String]] = {
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
  
  def applyRulesToDeadCell(livingNeighbours: Int) : String = {
    aliveOrDead(livingNeighbours == numberOfNeighboursToCreateLife)
  }
  
  def applyRulesToLivingCell(livingNeighbours: Int) : String = {
    aliveOrDead((livingNeighbours >= tooFewNeighbours) && 
                (livingNeighbours <= tooManyNeighbours))
  }
  
  def countLiveNeighbours(y: Int, x: Int, environment: Array[Array[String]]) : Int = {
    checkIsAlive(y, x-1,environment)   + 
    checkIsAlive(y, x+1,environment)   + 
    checkIsAlive(y-1, x-1,environment) + 
    checkIsAlive(y-1, x,environment)   + 
    checkIsAlive(y-1, x+1,environment) + 
    checkIsAlive(y+1, x-1,environment) +
    checkIsAlive(y+1, x,environment)   +
    checkIsAlive(y+1, x+1,environment)
  }
  
  def checkIsAlive(y: Int, x: Int, environment: Array[Array[String]]) : Int = {
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

  def generateLife() : String = {
    aliveOrDead(Random.nextFloat() < seed) 
  }
  
  def generateStartEnvironment() : Array[Array[String]] = {
      createArray().map { y => y.map { x => generateLife() } }
  } 
  
  def createArray(): Array[Array[String]] = Array.ofDim[String](ymax,xmax)
  
  def aliveOrDead(shouldBeAlive: Boolean) : String = {
    if (shouldBeAlive) 
      alive
    else 
      dead  
  }
}

object GameOfLife {
  def main(args: Array[String]) {
    val game = new GameOfLife()
        game.start()
  }
}