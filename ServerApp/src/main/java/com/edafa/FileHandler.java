package com.edafa;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {

    public static HashMap<String,String> getJsonMap(StringBuilder stringBuilder){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(stringBuilder.toString(), HashMap.class);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*returns: string builder object contains a file content
    * returns: null if file is not found
    * */
    public static StringBuilder readFile(String fileName){
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            StringBuilder stringBuilder=new StringBuilder();
            while (scanner.hasNext()){
                stringBuilder.append(scanner.next());
            }
            return stringBuilder;
        } catch (IOException e) {
            System.out.println("File not Found");
            return null;
        }

    }


    public static Boolean writeJson(HashMap<String,String> mapToWrite,String fileName){
        ObjectMapper mapper=new ObjectMapper();
        try {
            mapper.writeValue(new File(fileName), mapToWrite);
            return true;
        } catch (IOException e) {
            System.out.println("can't write the file");
            return false;
        }

    }
}
