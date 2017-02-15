import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class csvReader {
    public csvReader(){}
    public String[][] readCsv(String s) {
        File file = new File(s + ".csv");
        return readCsv(file);
    }
    public String[][] readCsv(File file){
        BufferedReader read=null;
        String[] temp={""};
        try{
            read=new BufferedReader(new FileReader(file));
            temp = (read.readLine()).split(",");
        }catch (IOException e){System.out.println(e);}
        String[][] array=new String[1][temp.length];
        for(int i=0;i<temp.length;i++){
            array[0][i]=temp[i];
        }
        try{read.close();} catch (Exception e){}
        int lines=findLines(file);
        try{
            read=new BufferedReader(new FileReader(file));
            read.readLine();
        }catch (Exception e){}
        try{
            for(int i=1;i<lines;i++){
            array=append(array,(read.readLine()).split(","));
            }
        }catch (Exception e){return array;}
        return array;
    }
    private int findLines(File file){
        int lines=1;
        BufferedReader read=null;
        try{read=new BufferedReader(new FileReader(file));}catch (Exception e){}
        while(true){
            try{
                if(read.readLine()==null){return lines;}
            }
            catch (Exception e){return lines;}
            lines++;
        }
    }
    private String[][] append(String[][] array, String[] addition){
        String[][] temp=new String[array.length+1][array[0].length];
        for(int i=0;i<array.length;i++){
            for(int i2=0;i2<array[0].length;i2++){
                temp[i][i2]=array[i][i2];
            }
        }
        for(int i=0;i<array[0].length;i++){
            temp[temp.length-1][i]=addition[i];
        }
        return temp;
    }
}