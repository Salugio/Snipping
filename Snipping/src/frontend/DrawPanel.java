package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JWindow;
import backend.*;

public class DrawPanel extends JPanel {

	private static final long serialVersionUID = -633559249713451960L;
	private ArrayList<Point> pointsList = new ArrayList<>();
	private Rectangle selectArea;
	private final Color colorPointer = Color.RED;
	private final static Capture capt = new Capture();
	private Image image = null;
	private JWindow parent = null;
	
	
	public DrawPanel(JWindow w, Image img) {
		super();
		parent = w;
		this.image = img;
		addingMouseListener();
	}
	
	public void setImage(Image img) {
		this.image = img;
		repaint();
	}
	
	public DrawPanel(String path) throws IOException {
		super();
		setImage(path);
	}
	
	public void setImage(String path) throws IOException {
		try {
			this.image = ImageIO.read(new File(path));
			repaint();
		} catch(IOException e) {
			throw new IOException(path + "cannot be found", e);
		}
	}
	
	public Image getImage() {
		return image;
	}
	
	public final void addingMouseListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if(selectArea != null && (selectArea.getWidth() * selectArea.getHeight() != 0)) {
					capt.screenShot(selectArea);
					parent.dispose();
				} else {
					pointsList.clear();
					repaint();
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				pointsList.add(new Point(e.getX(), e.getY()));
				repaint();
			}
		});
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		if(image != null) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.setColor(colorPointer);
			
			if(pointsList.size() > 1) {
				int x  = (int) pointsList.get(0).getX();
				int y  = (int) pointsList.get(0).getY();
				int xn = (int) pointsList.get(pointsList.size() - 1).getX();
				int yn = (int) pointsList.get(pointsList.size() - 1).getY();
				
				if(xn - x > 0 && yn - y > 0) {
					selectArea = new Rectangle(x, y, xn-x, yn-y);
					g.drawRect(x, y, xn-x, yn-y);
				} else {
					if(xn-x < 0 && yn-y > 0) {
						selectArea = new Rectangle(xn, y, x-xn, yn-y);
						g.drawRect(xn, y, x-xn, yn-y);
					} else {
						selectArea = new Rectangle(xn, yn, x-xn, y-yn);
						g.drawRect(xn, yn, x-xn, y-yn);
					}
				}
			}
		}
		g.dispose();
	}
}
