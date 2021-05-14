import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class MessageTransformer {
public static void main(String args[]) throws Exception{
    File folder = new File("CHANGE THIS TO PATH OF FOLDER CONTAING FILES"); 
    File[] listOfFiles = folder.listFiles(); 
    String name = ""; 
    for(int i=0;i<listOfFiles.length;i++) { 
        String data = ""; 
        File myObj = new File(listOfFiles[i].toString()); 
        Path p = Paths.get(listOfFiles[i].toString());
        name = p.getFileName().toString();
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) { 
            String d = myReader.nextLine();
            data = data + d + "\n";
        }
        myReader.close();
        String array[] = data.split("Body:\n"); 
        String body = array[1]; 
        char[] bodyChar = body.toCharArray(); 
        int count = 0;
        for(int j = 0;j<bodyChar.length;j++){ 
            if(Character.isDigit(bodyChar[j])){ 
                count+=1;
                if(count==10){ 
                    data = replace(data); 
                    array = data.split("Body:\n"); 
                    body = array[1]; 
                    String[] lines = body.split("\n"); 
                    body = "";
                    for(int k=0;k<lines.length;k++){
                        body = body + reverseWord(lines[k])+"\n"; 
                    }
                    array[1] = body;
                    data = array[0]+"Body:\n"+array[1];
                }
            }
            else{ 
                count=0;
            }
        }
        if (data.contains("domain.com")) { 
            data = replace(data); 
        }
        if (data.contains("SECURE:")) { 
            body = array[1]; 
            String[] lines = body.split("\n"); 
            body = "";
            for(int k=0;k<lines.length;k++){
                body = body + reverseWord(lines[k])+"\n"; /
            }
            array[1] = body;
            data = array[0]+"Body:\n"+array[1];  
        }

        try { 
            FileWriter myWriter = new FileWriter(name); 
            myWriter.write(data); 
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
    public static String replace(String data){ 
        data = data.replace("$", "e"); 
        data = data.replace("^", "y"); 
        data = data.replace("&", "u"); 
        return data; 
    }
    public static String reverseWord(String s){ 
        StringBuilder input1 = new StringBuilder();
        String[] array1 = s.split(" "); 
        String reverseWord = "";
        for(int j =array1.length-1;j>=0;j--){ 
            reverseWord = reverseWord+array1[j]+" "; 
        }
        input1.append(reverseWord); 
        input1.reverse(); 
        reverseWord = input1.toString();
        return reverseWord; 
    }
}
