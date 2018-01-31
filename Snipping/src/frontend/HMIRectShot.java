package frontend;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JWindow;

public class HMIRectShot extends JWindow {

	private static final long serialVersionUID = -1018187577853352130L;
	private DrawPanel dP;
	private final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	private final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	public HMIRectShot(Image image) {
		super();
		dP = new DrawPanel(this, image);
		initHMI();
	}
	
	private void initHMI() {
		setSize(width, height);
		setLocationRelativeTo(null);
		getContentPane().add(dP, BorderLayout.CENTER);
		setFocusable(true);
		requestFocus();
		setVisible(true);
	}

}
