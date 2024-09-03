package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

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
    
    private static void showSettingsDialog() {
        JDialog dialog = new JDialog(App.frame, "Seleziona colonne", true);
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
            layout.createParallelGroup(Alignment.LEADING)
                .addComponent(checkBoxes[0])
                .addComponent(checkBoxes[1])
                .addComponent(checkBoxes[2])
                .addComponent(checkBoxes[3])
                .addComponent(checkBoxes[4])
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(checkBoxes[0])
                .addComponent(checkBoxes[1])
                .addComponent(checkBoxes[2])
                .addComponent(checkBoxes[3])
                .addComponent(checkBoxes[4])
        );
        
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
