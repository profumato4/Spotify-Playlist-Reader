package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import main.TrackData;

import java.awt.BorderLayout;
import java.util.List;

public class TablePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;

    public TablePanel(List<TrackData> trackData) {
        setSize(823, 491);

        // Column names for the table
        String[] columnNames = { "Track", "Artist(s)", "Occurrences" };

        // Data for the table, overriding DefaultTableModel to make it non-editable
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;  // All cells are non-editable
            }
        };

        // Populate the model with track data
        for (TrackData data : trackData) {
            Object[] row = { data.getTrackName(), data.getArtistNames(), data.getOccurrence() };
            model.addRow(row);
        }

        // Initialize the table with the non-editable model
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scrollPane containing the table to the panel
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
