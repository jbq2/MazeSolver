import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class fileRead {
    public static void main(String[] args) {

        File in = new File("fileRead.java");
        File out = new File("copy.txt");

        try (Scanner scan = new Scanner(in)) {
            PrintWriter pw = new PrintWriter(out);

            while (scan.hasNext()) {
                pw.println(scan.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
