import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ChecksumUtil {

    public static String generateChecksum(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    // Verify checksum against the current data
    public static boolean verifyChecksum(String data, String originalChecksum) throws NoSuchAlgorithmException {
        String currentChecksum = generateChecksum(data);
        return currentChecksum.equals(originalChecksum);
    }
}
