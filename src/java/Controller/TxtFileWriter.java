package Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author S188219
 */
public class TxtFileWriter {

    public static String fileContents;
    

    public TxtFileWriter(String s) throws IOException {
        createTxtFile(s);
        fileContents = s;

    }

    /**
     * Creates file from server output
     * 
     * @param s
     * @return
     * @throws IOException 
     */
    public static File createTxtFile(String s) throws IOException {

        PrintWriter output = null;
        File file = null;

        try {
            file = new File("taskOutput.txt");
            output = new PrintWriter(new FileWriter(file));
            output.write(s);

            output.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }
        return file;
    }

}
