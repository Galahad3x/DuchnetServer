package duchnet.duchnet.common;

import java.util.List;

/**
 * Used to represent data in XML format
 */
public class FilenameXML {
    public String hash;
    public List<String> filename;

    public FilenameXML(String hash, List<String> filenames) {
        this.hash = hash;
        this.filename = filenames;
    }
}