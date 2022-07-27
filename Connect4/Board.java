import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class Board extends JPanel{

//	private int[] gameState = {0,0,0,0,0,0,0,
//							   0,0,0,0,0,0,0,
//							   0,0,0,0,0,0,0,
//							   0,0,0,0,0,0,0,
//							   0,0,0,0,0,0,0,
//							   0,0,0,0,0,0,0,
//							   0,0,0,0,0,0,0};
	
	private Color color1 = Color.yellow;
	private Color color2 = Color.red;
	private Color empty = Color.gray;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		super.setBackground(Color.lightGray);
		
		drawBackground(g);
		

	}
	
	private void drawBackground(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setRenderingHints(rh);
		
		Dimension size = getSize();
		int w = (int) size.getWidth();
		int h = (int) size.getHeight();
		
		g2d.setColor(empty);
		g2d.setStroke(new BasicStroke(5));
		
		// draw dividing lines
		for (int i = 2; i <= w+2; i = i+w/7) {
			for (int j = 1; j <= h+1; j = j+h/6) {
				// g2d.setColor(empty);
				g2d.drawLine(i, 0, i, h);
				g2d.drawLine(0, j, w, j);
			}
		}
		
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 5; j++) {
				switch (App.gameState[((i*7)+j)]) {
				case 1:
					g2d.setColor(color1);
					break;
				case 2:
					g2d.setColor(color2);
					break;
				default:
					g2d.setColor(empty);
					break;
				}
				System.out.println(g2d.getColor());
				int tempw = 2+(i*(w/7));
				int temph = 1+(j*(h/6));
				Ellipse2D e = new Ellipse2D.Double(tempw+10,temph+10,w/7-20,h/6-20);
				g2d.fill(e);
			}
		}	
	}

}
