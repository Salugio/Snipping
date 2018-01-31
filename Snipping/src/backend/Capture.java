package backend;

import java.awt.AWTException;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Capture {
	
	JFileChooser fileChooser = new JFileChooser();
	
	private FilterForFiles filter = new FilterForFiles();
	private File file = null;
	
	public Capture() {
		fileChooser.addChoosableFileFilter(filter);
	}
	
	/**
	 * Take a screenshot from the whole screen
	 */
	@SuppressWarnings("static-access")
	public void fullScreenShot() {
		BufferedImage bufImage = null; //original screenshot
		BufferedImage bufImageFinale = null; //resized screenshot
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();
		DisplayMode currentDisplayMode = graphicsDevice.getDisplayMode();
		
		int width = currentDisplayMode.getWidth();
		int height = currentDisplayMode.getHeight();
		
		//creating the screenshot
		try {
			bufImage = new Robot().createScreenCapture(new Rectangle(0,0,width,height));
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		//creating the resized screenshot
		bufImageFinale = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//resizing the screenshot
		Graphics2D g = (Graphics2D)bufImageFinale.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bufImage, 0, 0, width, height, null);
		g.dispose();
		
		JOptionPane info = new JOptionPane();
		if(fileChooser.showOpenDialog(null) == fileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if(fileChooser.getFileFilter().accept(file)) {
				try {
					ImageIO.write(bufImageFinale, "png", file);
				} catch (IOException e) {
					e.printStackTrace();
					info.showMessageDialog(null, "Error! Screenshot cannot "
							+ "be saved", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				info.showMessageDialog(null, "Error file type",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	/**
	 * Capture the selected area
	 */
	@SuppressWarnings("static-access")
	public void screenShot(Rectangle screenArea) {
		BufferedImage bufImage = null; //screenshot
		BufferedImage bufImageFinale = null; //resized screenshot
		
		//creating the screenshot
		try {
			bufImage = new Robot().createScreenCapture(screenArea);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		//creating the final screenshot
		bufImageFinale = new BufferedImage(screenArea.width, screenArea.height, BufferedImage.TYPE_INT_RGB);
		
		
		//resizing the screenshot
		Graphics2D g = (Graphics2D) bufImageFinale.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(bufImage, 0, 0, screenArea.width, screenArea.height, null);
		g.dispose();
		
		JOptionPane info = new JOptionPane();
		if(fileChooser.showSaveDialog(null) == fileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			if(file != null && fileChooser.getFileFilter().accept(file)) {
				try {
					ImageIO.write(bufImageFinale, "png", file);
				} catch (IOException e) {					
					e.printStackTrace();
					info.showMessageDialog(null, "Error! Screenshot cannot "
							+ "be saved", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				info.showMessageDialog(null, "Error Screenshot cannot be saved"
						, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	/**
	 * Get the BufferedImage from the whole screen
	 */
	public BufferedImage getScreenShotBuffer() {
		BufferedImage bufImage = null;
		Rectangle screenArea = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width, 
				Toolkit.getDefaultToolkit().getScreenSize().height);
		
		bufImage = new BufferedImage(screenArea.width, screenArea.height,
				BufferedImage.TYPE_INT_RGB);
		
		try {
			bufImage = new Robot().createScreenCapture(screenArea);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		return bufImage;
	}

}