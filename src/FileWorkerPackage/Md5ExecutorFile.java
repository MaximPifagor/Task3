package FileWorkerPackage;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class Md5ExecutorFile implements IExecutable<String> {
    private String result;


    @Override
    public String Process(File file) {
        if (!file.canExecute() || !file.canRead())
            return null;
        if (file.isDirectory())
            return null;

        byte[] b = GetBytesArray(file);
        String hashtext = getHashMD5(b).toString(16);

        return hashtext;
    }

    private BigInteger getHashMD5(byte[] b) {
        BigInteger bigInt;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            if (b != null)
                m.update(b);
            byte[] digest = m.digest();
            bigInt = new BigInteger(1, digest);

        } catch (Exception e) {
            bigInt = null;
        }
        return bigInt;
    }

    private byte[] GetBytesArray(File file) {
        List<Byte> bytes = new ArrayList<>();
        byte[] b;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                int i;
                while ((i = fileInputStream.read()) != -1) {
                    bytes.add((byte) i);
                }
                b = new byte[bytes.size()];
                for (int j = 0; j < bytes.size(); j++) {
                    b[j] = (byte) bytes.get(j);
                }
            } finally {
                fileInputStream.close();
            }
        } catch (Exception e) {
            b = null;
        }

        return b;
    }
}

