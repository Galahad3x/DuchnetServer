package duchnet.duchnet.common;

import duchnet.duchnet.models.Description;
import duchnet.duchnet.models.FileName;
import duchnet.duchnet.models.Tag;

import java.util.LinkedList;
import java.util.List;

/**
 * Used to represent data in XML format
 */
public class ContentXML {
    public String hash;
    public List<String> filename;
    public List<String> description;
    public List<String> tag;

    public ContentXML(String hash, List<String> filenames, List<String> descriptions, List<String> tags) {
        this.hash = hash;
        this.filename = filenames;
        this.description = descriptions;
        this.tag = tags;
    }
}
