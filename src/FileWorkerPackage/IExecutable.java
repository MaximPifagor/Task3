package FileWorkerPackage;

import java.io.File;
import java.lang.reflect.Type;

public interface IExecutable<T> {
     String Process(File file);

}