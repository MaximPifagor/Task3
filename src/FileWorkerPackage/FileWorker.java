package FileWorkerPackage;

import javax.swing.text.html.HTMLDocument;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileWorker {
    private final File RootDirectory;
    private final Path RoorDirectoryPath;
    private boolean isRecursive;
    private HashMap<String, String> PathToHashTable;

    FileWorker(String path) {
        RootDirectory = new File(path);
        RoorDirectoryPath = Paths.get(RootDirectory.toURI());
        isRecursive = false;
        PathToHashTable = new HashMap<>();
    }

    public void setIsRecursive(boolean newState) {
        isRecursive = newState;
    }

    public boolean getIsRecursive() {
        return isRecursive;
    }

    public File getCurrentDirectory() {
        File file = new File(RootDirectory.getPath());
        return file;
    }

    public void execute(IExecutable iExecutable) {
        PathToHashTable = new HashMap<>();
        SubExecute(iExecutable, RootDirectory);
    }

    public HashMap<String, String> getPathToHashTable() {
        HashMap<String, String> newMap = new HashMap<>();
        for (Map.Entry<String, String> e : PathToHashTable.entrySet()) {
            String keyFile = e.getKey();
            String valueString = e.getValue();
            newMap.put(new String(keyFile), new String(valueString));
        }
        return newMap;
    }

    private void SubExecute(IExecutable iExecutable, File startDirectory) {
        File[] files = startDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory() && isRecursive)
                this.SubExecute(iExecutable, files[i]);

            String result = iExecutable.Process(files[i]);
            if (result != null) {
                Path path1 = Paths.get(files[i].toURI());
                Path path2 = RoorDirectoryPath.relativize(path1);
                PathToHashTable.put(path2.toString(), result);
            }


        }
    }


    public static void main(String[] args) throws Exception {
        FileWorker fileWorker = new FileWorker("C:\\Users\\макс\\Desktop\\test");
        fileWorker.setIsRecursive(true);
        fileWorker.execute(new Md5ExecutorFile());
        HashMap<String, String> map = fileWorker.getPathToHashTable();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));
        System.out.println();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            bufferedWriter.write(entry.getKey()+ ": "+ entry.getValue()+".");
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}