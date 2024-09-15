package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXTable;
import main.TrackData;

public class TablePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JXTable table;  
    static List<TrackData> trackData;
    
    private boolean[] columnVisibility = { true, false, true, false, true }; 

    public TablePanel() {
        setSize(823, 491);
        setLayout(new BorderLayout());
        
        UIManager.put("Table.background", new Color(60, 63, 65));            
        UIManager.put("Table.foreground", Color.WHITE);                 
    }
    
    public void createTable() {
        updateTableVisibility();
    }
    
    // Method to update table visibility based on the "Show Every Song" checkbox
    public void updateTableVisibility() {
        boolean showEverySong = MenuBuilder.showEverySongItem.isSelected();
        String[] columnNames = { "Artist(s)", "Album", "Track", "Duration (min)", "Occurrences" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // All cells are non-editable
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {  
                    return Double.class;  
                }
                return super.getColumnClass(columnIndex);
            }
            
        };

        for (TrackData data : trackData) {
            // Filter tracks based on occurrence if "Show Every Song" is false
            if (showEverySong || data.getOccurrence() > 1) {
                Object[] row = { 
                    data.getArtistNames(), 
                    data.getAlbumName(), 
                    data.getTrackName(), 
                    data.getDurationMinutes(), 
                    data.getOccurrence() 
                };     
                model.addRow(row);
            }
        }

        table = new JXTable(model);  
        table.setSortable(true);  

        Font currentFont = table.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() + 2f);  
        table.setFont(newFont);
        table.setPreferredScrollableViewportSize(new Dimension(823, 491)); 
        
        JScrollPane scrollPane = new JScrollPane(table);

        removeAll();  // Clear existing table before adding the new one
        add(scrollPane, BorderLayout.CENTER);
        
        table.getColumnModel().getColumn(3).setCellRenderer(new DurationRenderer());
        
        updateColumnVisibility(columnVisibility);

        revalidate();
        repaint();
    }
    
    private static class DurationRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 1L;

        @Override
        protected void setValue(Object value) {
            if (value instanceof Number) {
                setText(String.format("%.2f", (Number) value));  
            } else {
                super.setValue(value);
            }
        }
    }
    
    public void updateColumnVisibility(boolean[] columnVisibility) {
        this.columnVisibility = columnVisibility; 
        for (int i = 0; i < columnVisibility.length; i++) {
            table.getColumnModel().getColumn(i).setMinWidth(columnVisibility[i] ? 15 : 0);
            table.getColumnModel().getColumn(i).setMaxWidth(columnVisibility[i] ? Integer.MAX_VALUE : 0);
            table.getColumnModel().getColumn(i).setPreferredWidth(columnVisibility[i] ? 100 : 0);
        }
    }

    public boolean[] getColumnVisibility() {
        return columnVisibility;
    }
    
    public List<TrackData> getTrackData() {
        List<TrackData> trackDataList = new ArrayList<>();

        // Loop through the rows in the table model
        for (int i = 0; i < table.getRowCount(); i++) {
            String artistNames = (String) table.getValueAt(i, 0);
            String albumName = (String) table.getValueAt(i, 1);
            String trackName = (String) table.getValueAt(i, 2);
            double duration = (Double) table.getValueAt(i, 3);
            int occurrence = (Integer) table.getValueAt(i, 4);

            // Create a TrackData object for each row
            TrackData trackData = new TrackData(trackName, artistNames, occurrence, albumName, duration);
            trackDataList.add(trackData);
        }

        return trackDataList;
    }
    
    public JXTable getTable() {
    	return table;
    }
    
}

