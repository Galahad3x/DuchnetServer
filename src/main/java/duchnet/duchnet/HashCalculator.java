package duchnet.duchnet;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Used to calculate CRC32 hash of a file
 */
public class HashCalculator {
    /**
     * Get SHA256 of a string
     *
     * @param text the string that represents a password
     * @return a string with the hash
     */
    public static String getStringHash(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
