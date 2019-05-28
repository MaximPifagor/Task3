package FileWorkerPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class MainFW {
    public static void main(String[] args) throws Exception {
        FileWorker fileWorker = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        fileWorker.setIsRecursive(true);
        fileWorker.execute(new Md5ExecutorFile());
        HashMap<String, String> map = fileWorker.getPathToHashTable();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src\\FileWorkerPackage\\output.txt"));
        System.out.println();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            bufferedWriter.write(entry.getKey()+ ": "+ entry.getValue()+".");
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}
