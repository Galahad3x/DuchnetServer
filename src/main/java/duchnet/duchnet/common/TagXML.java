package duchnet.duchnet.common;

import java.util.List;

/**
 * Used to represent data in XML format
 */
public class TagXML {
    public String hash;
    public List<String> tag;

    public TagXML(String hash, List<String> tags) {
        this.hash = hash;
        this.tag = tags;
    }
}