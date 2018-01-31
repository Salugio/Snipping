package frontend;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import backend.*;

public class HMIScreenShot extends JFrame {

	private static final long serialVersionUID = -4994305878859179583L;
	private HMIRectShot rectShot = null;
	private static final Capture capt = new Capture();
	
	private JMenuBar menu = new JMenuBar();
	private JMenu screenShot = new JMenu("Screenshot");
	private JMenuItem shotFullScreen = new JMenuItem("Full Screenshot"),
			rectScreenShot = new JMenuItem("Rectangular shot"), quit = new JMenuItem("Quit");
	private JButton leave = new JButton("Leave");
	
	private JMenu about = new JMenu("Help");
	private JMenuItem aboutItem = new JMenuItem("About");
	private JMenuItem help = new JMenuItem("Help");
	
	@SuppressWarnings("unused")
	private JLabel lblHelp;
	
	public HMIScreenShot() {
		super();
	}
	
	private void initHMI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Screenshot");
		setResizable(false);
		
		shotFullScreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Point p = getLocation();
				setLocation(1000, 1000);
				capt.fullScreenShot();
				setLocation(p.x, p.y);
			}			
		});
		shotFullScreen.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
		screenShot.add(shotFullScreen);
		
		rectScreenShot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Point p = getLocation();
				setLocation(1000, 1000);
				Image image = capt.getScreenShotBuffer();
				rectShot = new HMIRectShot(image);
				setLocation(p.x, p.y);
				toFront();
			}
		});
		rectScreenShot.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		screenShot.add(rectScreenShot);
		
		quit.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
		screenShot.addSeparator();
		screenShot.add(quit);
		screenShot.setMnemonic('C');
		
		leave.setToolTipText("Undo shot");
		leave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rectShot != null) {
					closeRectShot();
				}
			}
		});
		lblHelp = new JLabel("Select a shot type", SwingConstants.RIGHT);
		
		help.addActionListener(new ActionListener() {			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();
				String mess = "Choose a shot type";
				mess += " 1. Full Screenshot";
				mess += " 2. Area Screanshot";
				jop.showMessageDialog(null, mess, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		aboutItem.addActionListener(new ActionListener() {			
			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane jop = new JOptionPane();
				String mess = "Java Screenshot creator";
				mess += "Simple solo project";
				jop.showMessageDialog(null, mess, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		menu.add(screenShot);
		menu.add(leave);
		about.add(help);
		about.addSeparator();
		about.add(aboutItem);
		menu.add(about);
		setJMenuBar(menu);
		getContentPane().add(help, BorderLayout.CENTER);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void closeRectShot() {
		rectShot.dispose();
	}
	
	public static void main(String[] args) {
		final HMIScreenShot ss = new HMIScreenShot();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ss.initHMI();
			}
		});
	}
	
}



















