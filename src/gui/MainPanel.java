package gui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.hc.core5.http.ParseException;

import main.PlaylistReader;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;



public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		
		setSize(823,491);
		
		JLabel lblNewLabel = new JLabel("Insert the spotify playlist link");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Done");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlaylistReader.link = textField.getText();
				removeAll();
				repaint();
				try {
					PlaylistReader.readPlaylist();
				} catch (ParseException | SpotifyWebApiException | IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
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
					.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
					.addGap(334))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(98)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(53)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(162, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
	}
}
