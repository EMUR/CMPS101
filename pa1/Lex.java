import java.io.*;

/**
 * Created by eyad murshid on 9/26/16.
 */
public class Lex {

    // main: open the input file, call functions and write the output file
    // Pre: The arguments must be 2
    public static void main(String[] args) throws IOException {
        if (args.length != 2)
            System.err.println("You must pass two arguments. The input file name and the output file name.");

        String[] fileContent;
        List L;
        String inputFileName = String.format("%s.txt",args[0]);
        String outputFileName = String.format("%s.txt",args[1]);

        fileContent = readFile(inputFileName);

        L = buildList(fileContent);
        exportFile(outputFileName,fileContent,L);



    }


    // exportFile: Write the output file to disk
    // Pre: The program failed to write the file to the computer
    public static void exportFile(String name, String[] fileContent, List L)
    {

        try {
            PrintWriter out = new PrintWriter(name);
            L.moveFront();

            for(int i = 0; i < fileContent.length; i++)
            {
                out.println(fileContent[L.get()]);
                L.moveNext();
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    // buildList: Build and return the list
    // Pre: None
    public static List buildList(String[] fileContent)
    {
        List L = new List();
        L.append(0);

        for (int i = 1; i < fileContent.length; i++) {

            L.moveFront();

            while(L.index() >= 0){

                int x = L.get();

                if(fileContent[i].compareTo(fileContent[x]) <  0)
                {
                    if(L.index() == 0)
                    {
                       L.prepend(i);
                        break;
                    }

                    L.insertBefore(i);
                    break;
                }
                    L.moveNext();
            }
            if(L.index() < 0)
            {
                L.append(i);
            }
        }

    return L;
    }




    // readFile: Open the inout file, build an array string and return it
    // Pre: The program failed to find the file to open
    public static String[] readFile(String inputFileName) throws IOException {
        File file = new File(inputFileName);
        RandomAccessFile reader = null;

        reader = new RandomAccessFile(file,"r");
        int lines = 0;
        while (reader.readLine() != null) lines++;

        String [] fileContent = new String[lines];
        reader.seek(0);

        try {
            String text = null;

            for(int i =0; i < lines; i++)
            {
                fileContent[i] = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }

        return fileContent;
    }
}
