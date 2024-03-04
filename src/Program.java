import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Program {
    private String narq;
    private ArrayList<ArrayList<String>> data;

    public Program(String narq) {
        this.narq = narq;
        this.data = new ArrayList<>();
        loadProgram();
    }

    private void loadProgram() {
        String currDir = Paths.get("").toAbsolutePath().toString() + "/files/";
        String nameComplete = currDir + narq;
        Path path2 = Paths.get(nameComplete);
        try (Scanner sc = new Scanner(Files.newBufferedReader(path2, Charset.defaultCharset()))) {
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                // System.out.println(line);
                String[] tokens = line.split(" ");
                for(int i = 0; i < tokens.length; i++){
                    System.out.println(i +": " + tokens[i]);
                }
                data.add(new ArrayList<>(Arrays.asList(tokens)));
                System.out.println("Nova iteracao");
            }
        } catch (IOException e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }

    public void renumber() {
        HashMap<String, String> numMap = new HashMap<>();
        for(int i = 0; i < data.size(); i++){
            String old_line = data.get(i).get(0);
            String new_line = Integer.toString((i+1)*10);
            numMap.put(old_line, new_line);
        }
        for(ArrayList<String> aux: data){
            for(int i = 0; i < aux.size(); i++){
                String check = aux.get(i);
                if(numMap.containsKey(check)){
                    aux.set(i, numMap.get(check));
                }
            }
        }
        // System.out.println(data);
        writeProgram();
    }

    private void writeProgram() {
        String currDir = Paths.get("").toAbsolutePath().toString() + "/renum_files/";
        String[] old_name = narq.split(".bas");
        String nameComplete = currDir + old_name[0] + "-rn.bas";
        Path path = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            for(ArrayList<String> aux: data){
                String line = "";
                for(String tmp: aux){
                    line += tmp + " ";
                }
                writer.println(line);
            }
        } catch (Exception e) {
            System.err.format("Erro de E/S: %s%n", e);
        }
    }
}