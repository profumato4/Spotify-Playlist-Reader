package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import main.TrackData;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ExportManager {

    // Export to CSV format
    public static void exportToCSV(String filePath, List<TrackData> trackData) throws IOException {
        FileWriter csvWriter = new FileWriter(filePath);
        csvWriter.append("Artist(s),Album,Track,Duration (min),Occurrences\n");

        for (TrackData track : trackData) {
            csvWriter.append(track.getArtistNames()).append(",")
                     .append(track.getAlbumName()).append(",")
                     .append(track.getTrackName()).append(",")
                     .append(String.valueOf(track.getDurationMinutes())).append(",")
                     .append(String.valueOf(track.getOccurrence())).append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    // Export to JSON format
    public static void exportToJSON(String filePath, List<TrackData> trackData) throws IOException {
        Gson gson = new Gson();
        FileWriter jsonWriter = new FileWriter(filePath);
        gson.toJson(trackData, jsonWriter);
        jsonWriter.flush();
        jsonWriter.close();
    }

    // Export to XML format
    public static void exportToXML(String filePath, List<TrackData> trackData) throws Exception {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        
        Element root = document.createElement("Playlist");
        document.appendChild(root);

        for (TrackData track : trackData) {
            
            Element trackElement = document.createElement("Track");

            Element artist = document.createElement("Artist");
            artist.appendChild(document.createTextNode(track.getArtistNames()));
            trackElement.appendChild(artist);

            Element album = document.createElement("Album");
            album.appendChild(document.createTextNode(track.getAlbumName()));
            trackElement.appendChild(album);

            Element trackName = document.createElement("TrackName");
            trackName.appendChild(document.createTextNode(track.getTrackName()));
            trackElement.appendChild(trackName);

            Element duration = document.createElement("Duration");
            duration.appendChild(document.createTextNode(String.valueOf(track.getDurationMinutes())));
            trackElement.appendChild(duration);

            Element occurrences = document.createElement("Occurrences");
            occurrences.appendChild(document.createTextNode(String.valueOf(track.getOccurrence())));
            trackElement.appendChild(occurrences);

            root.appendChild(trackElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(filePath);
        transformer.transform(domSource, streamResult);
    }
}
