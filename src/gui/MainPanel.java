package gui;

import javax.swing.JPanel;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
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
    public static JTextField textField;
    public static TablePanel panel;
    public static JButton done;
    public static JLabel lblNewLabel;
    public static LoadingPanel loadingPanel;  

    /**
     * Create the panel.
     */
    public MainPanel() {

        setSize(823,491);
        
        keyBindings();
        
        lblNewLabel = new JLabel("Insert the spotify playlist link");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblNewLabel.setForeground(Color.WHITE);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 25));
        textField.setColumns(10);
        textField.setForeground(Color.WHITE);

        done  = new JButton("Done");
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            	PlaylistReader.link = textField.getText();
            	textField.setText("");
            	
                new Thread(() -> {
                    try {
                        PlaylistReader.playlistID();
                        if (!PlaylistReader.playlistId.isEmpty()) {
                        	loadingPanel.setVisible(true);
                            lblNewLabel.setVisible(false);
                            textField.setVisible(false);
                            done.setVisible(false);
                            TablePanel.trackData = PlaylistReader.readPlaylist();

                            panel.setVisible(true);
                            panel.createTable();
                        }else {
                        	JOptionPane.showMessageDialog(App.frame, "Insert a valid spotify playlist link", "Invalid link",
                					JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (SpotifyWebApiException | IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    } catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (com.formdev.flatlaf.json.ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally {
                    	loadingPanel.setVisible(false);
                    }
                    revalidate();
                    repaint();
                }).start();
            }
        });
        done.setFont(new Font("Tahoma", Font.PLAIN, 25));
        done.setForeground(Color.WHITE);

        panel = new TablePanel();
        panel.setVisible(false);

        loadingPanel = new LoadingPanel(); 
        loadingPanel.setVisible(false); 

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
                .addComponent(loadingPanel, GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE) 
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
                .addComponent(loadingPanel, GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE) 
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
			}
		});
	}
    
}
