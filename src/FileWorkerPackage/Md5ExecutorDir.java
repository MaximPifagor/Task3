package FileWorkerPackage;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Md5ExecutorDir implements IExecutable<String> {
    HashMap<String, BigInteger> hashMap = new HashMap<>();
    BigInteger integer;

    @Override
    public String Process(File file) {
        if (!file.canExecute() || !file.canRead())
            return null;
        if (file.isDirectory()) {
            if (hashMap.containsKey(file.getAbsolutePath()))
                return hashMap.get(file.getAbsolutePath()).toString(16);

            integer = BigInteger.ZERO;
            SubProcess(file);
            hashMap.put(file.getAbsolutePath(), integer);
            Integer i = integer.hashCode();
            integer = BigInteger.ZERO;
            String s = new BigInteger(i.toString()).toString(16);
            return s;
        } else
            return null;
    }

    private void SubProcess(File file) {
        File[] listFiles = file.listFiles();
        BigInteger big = BigInteger.ZERO;
        for (File f : listFiles) {
            if (f.isDirectory()) {
                if (hashMap.containsKey(f.getAbsolutePath()))
                    integer = integer.add(hashMap.get(f.getAbsolutePath()));
                else
                    SubProcess(f);
            } else {
                byte[] b = ReadFile(f);
                BigInteger bigInteger = getHashMD5(b);
                if (bigInteger != null && b != null)
                    integer = integer.add(bigInteger);
            }
        }
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

    private byte[] ReadFile(File file) {
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
