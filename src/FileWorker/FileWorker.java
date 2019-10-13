package FileWorker;

import java.io.File;

public class FileWorker {
    private final File directory;
    private boolean isRecursive;

    public FileWorker(String path) {
        directory = new File(path);
        isRecursive = false;
    }

    public FileWorker(String path, boolean isRecursive) {
        directory = new File(path);
        this.isRecursive = isRecursive;
    }

    public boolean isRecursive() {
        return isRecursive;
    }

    public String getDirectoryPath() {
        return directory.getAbsolutePath();
    }

    public void execute(IExecutable iExecutable) {
        SubExecute(iExecutable, directory);
    }

    private void SubExecute(IExecutable iExecutable, File startDirectory) {
        File[] files = startDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory() && isRecursive) {
                this.SubExecute(iExecutable, files[i]);
                continue;
            }
            iExecutable.execute(files[i]);
        }
    }
}