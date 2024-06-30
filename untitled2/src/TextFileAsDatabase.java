import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TextFileAsDatabase {
    public  List<ArrayList<String>> readData(File file) throws IOException {
        // the list that we goona store our data in

        ArrayList<String> dataList = new ArrayList<>();
        List<ArrayList<String>> datas = new ArrayList<>();
        FileReader reader = new FileReader(file);

        // reading the file character by character;
        String text = "";
        int ch = reader.read();
        while (ch != -1) {
            text += (char) ch;
            ch = reader.read();
        }
        // firing data to our list
        String data = "";
        int n =0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != ';') {
                data += text.charAt(i);
            } else {
                n++;
                dataList.add(data);
                data = "";
                if (n == 4 ){
                    datas.add(dataList);

                    n=0;
                    dataList = new ArrayList<>();
                }
            }
        }
        return  datas;
    }

  public void writeData(File file, String data) throws IOException {


            Files.write(Paths.get(file.getAbsolutePath()),(data).getBytes(), StandardOpenOption.APPEND);

    }




}