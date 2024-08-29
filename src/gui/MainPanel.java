package gui;

import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.InputMap;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.hc.core5.http.ParseException;

import main.PlaylistReader;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;



public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private TablePanel panel;
	static JButton done;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		
		setSize(823,491);
		
		keyBindings();
		
		JLabel lblNewLabel = new JLabel("Insert the spotify playlist link");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textField.setColumns(10);
		
		done  = new JButton("Done");
		done.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlaylistReader.link = textField.getText();
				textField.setText("");
				remove(lblNewLabel);
				remove(textField);
				remove(done);
				try {
					TablePanel.trackData = PlaylistReader.readPlaylist();
                    panel.setVisible(true);
                    panel.createTable();
				} catch (ParseException | SpotifyWebApiException | IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
				revalidate();
                repaint();
			}
		});
		done.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		panel = new TablePanel();
		panel.setVisible(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(241)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
							.addGap(52))
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
					.addGap(186))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(342)
					.addComponent(done, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
					.addGap(334))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(done, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(0))
		);
		setLayout(groupLayout);
		
	}
	
	public void keyBindings() {
		InputMap inputMap = this.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = this.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		actionMap.put("enter", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				done.doClick();
				done.requestFocusInWindow();
			}
		});
	}
	
	
}
