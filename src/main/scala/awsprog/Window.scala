package awsprog

import java.awt.Color
import java.awt.EventQueue
import java.awt.Graphics
import java.awt.Point

import javax.swing.JFrame
import javax.swing.JPanel

class Window extends JFrame {
  setTitle("Game Of Life")
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  setVisible(true)
  setExtendedState(java.awt.Frame.MAXIMIZED_BOTH)
  val grid = new Grid(150, 100)
  add(grid)
  
  def update(environment: Array[Array[Boolean]]) {
    grid.clear()
    environment.zipWithIndex.foreach { case (row, rowIndex) =>
      row.zipWithIndex.foreach { case (cell, columnIndex) => 
        if (cell.equals(true))
          grid.fillCell(columnIndex, rowIndex)
      }
    }
    
    EventQueue.invokeLater(new Runnable {
      def run() {
        grid.repaint()
      }
    })
  } 
}
