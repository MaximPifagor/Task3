package FileWorker;

import java.io.File;

public interface IExecutable<T> {
     void execute(File file);
}