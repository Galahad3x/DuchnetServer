package duchnet.duchnet.common;

import java.util.List;

/**
 * Used to represent data in XML format
 */
public class DescriptionXML {
    public String hash;
    public List<String> description;

    public DescriptionXML(String hash, List<String> descriptions) {
        this.hash = hash;
        this.description = descriptions;
    }
}
