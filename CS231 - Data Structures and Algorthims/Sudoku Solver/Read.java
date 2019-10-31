import java.io.*;

public class Read {

	public static boolean read(String filename) {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing filename to the constructor
            FileReader reader = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the FileReader variable to the constructor
            BufferedReader buffer = new BufferedReader(reader);

            // assign to a variable of type String line the result of calling the readLine method of your BufferedReader object.
            String line = buffer.readLine();
            // start a while loop that loops while line isn't null
            while(line != null) {
                // assign to an array of type String the result of calling split on the line with the argument "[ ]+"
                String[] lines = line.split("[ ]+");
                // displays lines
                for(String temp : lines) 
                    System.out.print(temp);
                // print the String (line)
                System.out.println(line);
                // print the size of the String array (you can use .length)
                //System.out.println(lines.length);
                // assign to line the result of calling the readLine method of your BufferedReader object.
                line = buffer.readLine();
            }
            // call the close method of the BufferedReader
            buffer.close();
            // return true
            return true;
        }
        catch(FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename );
        }
        catch(IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }

        return false;
    }


	public static void main(String[] args) {
		for(int i = 0;i < args.length;i++) {
			Read.read(args[i]);
			System.out.println();
		}	
	}
}