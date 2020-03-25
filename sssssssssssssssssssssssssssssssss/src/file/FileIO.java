package file;

import java.io.IOException;

public interface FileIO {
     void readFromFile(String fileName)throws IOException;
     void writeFromFile(String fileName)throws IOException;
     void setValue(String key,String value);
     void getValue(String key);

}
