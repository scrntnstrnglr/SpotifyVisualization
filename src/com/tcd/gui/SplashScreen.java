package com.tcd.gui;

import java.awt.Color;
import java.awt.Window.Type;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SplashScreen {

	private final String image, message;
	private static JFrame frame, messageFrame;

	public SplashScreen(String image, String message) throws IOException {
		this.image = image;
		this.message = message;
		createFrame();
	}

	private void createFrame() throws IOException {
		ImageIcon imageIcon = new ImageIcon(image); // load the image to a imageIcon
		frame = new JFrame();
		frame.setType(Type.UTILITY);
		frame.setBounds(100, 100, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(false);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel(imageIcon);
		lblNewLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
		frame.getContentPane().add(lblNewLabel);

		messageFrame = new JFrame();
		messageFrame.setType(Type.UTILITY);
		messageFrame.setBounds(0, 0, imageIcon.getIconWidth(), 20);
		messageFrame.setLocationRelativeTo(null);
		messageFrame.setUndecorated(true);
		messageFrame.setBackground(new Color(0, 0, 0, 0));
		messageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messageFrame.setVisible(false);
		messageFrame.getContentPane().setLayout(null);

		JLabel lblText = new JLabel(message, SwingConstants.CENTER);
		lblText.setBounds(0, 0, imageIcon.getIconWidth(), 20);
		lblText.setForeground(new Color(255,255,255,255));
		messageFrame.setLocation(messageFrame.getLocation().x,
				messageFrame.getLocation().y + imageIcon.getIconHeight() / 2 + 10);
		messageFrame.getContentPane().add(lblText);

	}

	public JFrame getFrame() {
		return frame;
	}

	public void setVisible(boolean visibility) {
		frame.setVisible(visibility);
		messageFrame.setVisible(visibility);
	}

	public void dispose() {
		frame.dispose();
		messageFrame.dispose();
	}

	public static void main(String args[]) throws IOException {
		SplashScreen splash = new SplashScreen("img\\loading_gifs\\musicloading_small.gif", "Loading...");
		splash.setVisible(true);
	}

}
