package com.edafa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class EndConversationHandler {

    public static boolean saveConversationState(String userName, StringBuilder currentCoverstion) {
        HashMap<String, String> historyFrequency;
        HashMap<String, String> currentFrequency;
        StringBuilder stringBuilder = FileHandler.readFile("users/"+userName +"ALL"+".json");
        if (stringBuilder != null) {
            historyFrequency = FileHandler.getJsonMap(stringBuilder);
        } else {
            historyFrequency = new HashMap<>();
        }
        historyFrequency=freqOfWords(historyFrequency,currentCoverstion);
        currentFrequency=freqOfWords(new HashMap<>(),currentCoverstion);

        if(FileHandler.writeJson(historyFrequency,"users/"+userName+"freqALL"+".json")
                &&FileHandler.writeJson(currentFrequency,"users/"+userName+"freqLast"+".json")&& saveConversationText(currentCoverstion,userName)){
            return true;
        }
        return false;
    }

    private static boolean saveConversationText(StringBuilder currentConversation, String userName){
        try{
        File userFile = new File("users/"+userName+".txt");
        userFile.createNewFile(); // if file already exists will do nothing
        FileOutputStream oFile = new FileOutputStream(userFile, true);
        oFile.write(currentConversation.toString().getBytes());
        return true;
    }
        catch (IOException e){
            e.printStackTrace();
        return false;
        }
    }





    private static HashMap<String,String> freqOfWords(HashMap<String,String> statMap,StringBuilder currentCoverstion){
        String[] lines=currentCoverstion.toString().split("\n");
        String[] words;
        for (int i = 0; i < lines.length; i++) {
            words=lines[i].split("[^a-zA-Z0-9]");
            for (String word:words) {
                if(statMap.containsKey(word)){
                    statMap.replace(word,String.valueOf(Integer.parseInt(statMap.get(word))+1));
                }
                else statMap.put(word,"1");
            }
        }
        return statMap;
    }


}


