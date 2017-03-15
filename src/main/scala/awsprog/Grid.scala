package awsprog

import java.awt.Color
import java.awt.Graphics
import java.awt.Point

import javax.swing.JPanel

class Grid(ymax: Int, xmax: Int) extends JPanel {
  val interval: Int = 10
  val xgridMax = xmax * interval
  val ygridMax = ymax * interval
  var cells: List[Point] = List()
  
  override def paintComponent(graphic: Graphics): Unit = {
    super.paintComponent(graphic)
    cells.foreach { cell => 
     val cellX = interval + (cell.x * interval)
     val cellY = interval + (cell.y * interval)
     graphic.setColor(Color.RED)
     graphic.fillRect(cellX,cellY, interval, interval)
    }
    graphic.setColor(Color.BLACK)
    graphic.drawRect(interval, interval, ygridMax, xgridMax)
    
    (interval until (ygridMax + interval) by interval)
      .foreach(y => graphic.drawLine(y, interval, y, xgridMax + interval))
      
    (interval until (xgridMax + interval) by interval)
      .foreach(x => graphic.drawLine(interval, x, ygridMax + interval, x))
  }
  
  def clear(): Unit =  cells = List()

  def fillCell(x: Int, y: Int): Unit =  cells = cells.+:(new Point(x,y))
}
