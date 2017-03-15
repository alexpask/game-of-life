package awsprog;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;


public class Grid extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 3155541099893860655L;
  private List<Point> fillCells;
  private final int ygridmax;
  private final int xgridmax;
  private final int interval = 10;

  public Grid(int ymax, int xmax) {
    this.ygridmax = ymax * interval;
    this.xgridmax = xmax * interval;
    fillCells = new ArrayList<Point>(25);
  }

  @Override
  protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      for (Point fillCell : fillCells) {
          int cellX = interval + (fillCell.x * interval);
          int cellY = interval + (fillCell.y * interval);
          g.setColor(Color.RED);
          g.fillRect(cellX, cellY, interval, interval);
      }
      g.setColor(Color.BLACK);
      g.drawRect(interval, interval, ygridmax, xgridmax);

      for (int i = interval; i <= ygridmax; i += interval) {
          g.drawLine(i, interval, i, xgridmax + interval);
      }

      for (int i = interval; i <= xgridmax; i += interval) {
          g.drawLine(interval, i, ygridmax + interval, i);
      }
  }
  
  public void clear() {
    fillCells.clear();
  }

  public void fillCell(int x, int y) {
      fillCells.add(new Point(x, y));
  }
}
