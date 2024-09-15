package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import main.TrackData;
import utils.ExportManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class MenuBuilder {

	public static JCheckBoxMenuItem showEverySongItem;

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
		
		JMenu downloadMenu = new JMenu("Download");
		menuBar.add(downloadMenu);
		
		JMenuItem exportData = new JMenuItem("Export Data");
		exportData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showExportDialog();
			}
		});
		downloadMenu.add(exportData);
		
		JMenuItem exportAudio = new JMenuItem("Export Audio");
		exportAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		downloadMenu.add(exportAudio);
		
		showEverySongItem = new JCheckBoxMenuItem("Show Every Song", true);
		showEverySongItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainPanel.panel.updateTableVisibility();
			}
		});
		optionMenu.add(showEverySongItem);

		JMenuItem settings = new JMenuItem("Settings");
		settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showSettingsDialog();
			}
		});
		optionMenu.add(settings);

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

	private static void showExportDialog() {
	    String[] formats = {"CSV", "JSON", "XML"};
	    String format = (String) JOptionPane.showInputDialog(
	        App.frame,
	        "Choose export format:",
	        "Export Data",
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        formats,
	        formats[0]
	    );

	    if (format != null) {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setDialogTitle("Specify a file to save");

	        String defaultFileName = "playlist." + format.toLowerCase();
	        fileChooser.setSelectedFile(new File(defaultFileName));

	        int userSelection = fileChooser.showSaveDialog(App.frame);
	        if (userSelection == JFileChooser.APPROVE_OPTION) {
	            File fileToSave = fileChooser.getSelectedFile();
	            
	            String filePath = fileToSave.getAbsolutePath();
	            if (!filePath.endsWith("." + format.toLowerCase())) {
	                filePath += "." + format.toLowerCase();
	            }

	            File file = new File(filePath);
	            try {
	                if (!file.exists()) {
	                    if (!file.createNewFile()) {
	                        throw new IOException("Unable to create file");
	                    }
	                } else if (!file.canWrite()) {
	                    throw new IOException("No write permission for this file");
	                }

	                exportPlaylistData(filePath, format); 

	                if (file.length() == 0) {
	                    file.delete();
	                    throw new IOException("No data to export");
	                }

	            } catch (IOException e) {
	                JOptionPane.showMessageDialog(App.frame, "Error: " + e.getMessage(), "File Save Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }
	}



	private static void exportPlaylistData(String filePath, String format) throws IOException {

		if (MainPanel.panel.getTable() != null) {

			List<TrackData> trackData = MainPanel.panel.getTrackData();

			switch (format) {
			case "CSV":
				ExportManager.exportToCSV(filePath, trackData);
				break;
			case "JSON":
				ExportManager.exportToJSON(filePath, trackData);
				break;
			case "XML":
				try {
					ExportManager.exportToXML(filePath, trackData);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(App.frame, "Error exporting to XML", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			default:
				JOptionPane.showMessageDialog(App.frame, "Unknown format selected", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private static void showSettingsDialog() {
		JDialog dialog = new JDialog(App.frame, "Select column", true);
		dialog.setLayout(new BorderLayout());

		JPanel checkBoxPanel = new JPanel();
		GroupLayout layout = new GroupLayout(checkBoxPanel);
		checkBoxPanel.setLayout(layout);

		boolean[] columnVisibility = MainPanel.panel.getColumnVisibility();

		JCheckBox[] checkBoxes = new JCheckBox[5];
		checkBoxes[0] = new JCheckBox("Artist(s)", columnVisibility[0]);
		checkBoxes[1] = new JCheckBox("Album", columnVisibility[1]);
		checkBoxes[2] = new JCheckBox("Track", columnVisibility[2]);
		checkBoxes[3] = new JCheckBox("Duration (min)", columnVisibility[3]);
		checkBoxes[4] = new JCheckBox("Occurrences", columnVisibility[4]);

		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING).addComponent(checkBoxes[0]).addComponent(checkBoxes[1])
						.addComponent(checkBoxes[2]).addComponent(checkBoxes[3]).addComponent(checkBoxes[4]));

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(checkBoxes[0]).addComponent(checkBoxes[1])
				.addComponent(checkBoxes[2]).addComponent(checkBoxes[3]).addComponent(checkBoxes[4]));

		dialog.add(checkBoxPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean[] newColumnVisibility = new boolean[5];
				for (int i = 0; i < checkBoxes.length; i++) {
					newColumnVisibility[i] = checkBoxes[i].isSelected();
				}
				MainPanel.panel.updateColumnVisibility(newColumnVisibility);
				dialog.dispose();
			}
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		dialog.add(buttonPanel, BorderLayout.SOUTH);

		dialog.pack();
		dialog.setLocationRelativeTo(App.frame);
		dialog.setVisible(true);
	}
}
