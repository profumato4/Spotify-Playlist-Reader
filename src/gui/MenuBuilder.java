package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class MenuBuilder {
	
	public static void createMenu() {
		
		UIManager.put("MenuBar.background", Color.BLACK);
		UIManager.put("MenuBar.foreground", Color.WHITE);
		UIManager.put("Menu.background", Color.BLACK);
		UIManager.put("Menu.foreground", Color.WHITE);
		UIManager.put("MenuItem.background", Color.BLACK);
		UIManager.put("MenuItem.foreground", Color.WHITE);
		UIManager.put("Separator.foreground", Color.WHITE);
		UIManager.put("Separator.background", Color.WHITE);
		
		JMenuBar menuBar = new JMenuBar();
		App.frame.setJMenuBar(menuBar);
		
		JMenu optionMenu = new JMenu("Option");
		menuBar.add(optionMenu);
		
		JMenuItem back = new JMenuItem("Go Back to Main Menu");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.panel.setVisible(false);
				MainPanel.done.setVisible(true);
				MainPanel.lblNewLabel.setVisible(true);
				MainPanel.textField.setVisible(true);
			}
		});
		optionMenu.add(back);
	}
	
}
