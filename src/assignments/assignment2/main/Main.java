package assignments.assignment2.main;

import assignments.assignment2.app.DataManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    private static final String FILE_PATH = "src/assignments/assignment2/main/file";

    public static void main(String[] args) throws IOException {
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
            DataManager dataManager = new DataManager();
            String s;
            while (!((s = bufferedReader.readLine()) == null)){
                dataManager.executeConsoleData(s);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e){
            System.out.println("Please, check validness of file path in Main cons. ");
        }
    }
}
