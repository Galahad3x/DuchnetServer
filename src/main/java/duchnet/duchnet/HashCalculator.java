package duchnet.duchnet;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

/**
 * Used to calculate CRC32 hash of a file
 */
public class HashCalculator {
    /**
     * Get the hash of a file
     *
     * @param file the file
     * @return CRC32 hash of a file
     */
    public static String getFileHash(File file) throws IOException {
        // If file is more than 100 MB
        if (file.length() / (1000 * 1000) > 100) {
            System.out.println("WARNING: " + file.getName() + " is larger than 100MB, hash might be slow");
        }
        FileInputStream fis = new FileInputStream(file);
        CheckedInputStream checkedInputStream = new CheckedInputStream(fis, new CRC32());
        byte[] buffer = new byte[1000 * 1000];
        while (checkedInputStream.read(buffer, 0, buffer.length) >= 0) {
            assert true;
        }
        return Long.toHexString(checkedInputStream.getChecksum().getValue());
    }

    /**
     * Get SHA256 of a string
     * @param text the string that represents a password
     * @return a string with the hash
     */
    public static String getStringHash(String text) {
        return DigestUtils.sha256Hex(text);
    }
}
