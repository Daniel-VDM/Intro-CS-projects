package program2fromfile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * @author Daniel Van Der Maden
 */
public class Program2FromFile {
   
    public static void main(String[] args) 
    {
        //Gets path of file
        Scanner user_input = new Scanner (System.in);
        System.out.print("Please enter path directory of file\n>");
        String FILENAME = "Names.txt";
        
        //File read tools
        BufferedReader br = null;
		FileReader fr = null;
        //The Data Structure Instantiation
        TwoLinkList main = new TwoLinkList();

		try 
        {
            //File read tools
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            
            //Data before adding to list
            String SWeight, name;
            int weight;

            br = new BufferedReader(new FileReader(FILENAME));

            while ((name = br.readLine()) != null 
                    && (SWeight = br.readLine()) != null ) 
            {
                //Parce the input into an int
                weight = Integer.parseInt(SWeight);
                //Data Validation
               	if(weight < 0 || name.length() == 0 
                        || !name.matches("[a-zA-Z]+"))
                System.err.println("** Error with Data Format or Info **");
                else
                    main.addToList(name, weight);
            }

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        finally 
        {
            try 
            {
                //Close buffer & file streams
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
                
                //Print the list with both order
                System.out.println("--Output--");
                main.print('w');
                main.print('n');
            }
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
    }
}
//output
/*
    Please enter path directory of file
    >C:\Users\Daniel Van Der Maden\Desktop\Program2FromFile\dist\Names.txt
    --Output--
    Weight order: Annabelle - 99, Bobby - 109, April - 117, Claire - 124, Kevin - 145, Jim - 150, Brian - 150, Bob - 156, Steven - 164, Michael - 174, Chris - 175, Jason - 182, Abe - 199, Richard - 200, Tom - 212, 
    Name order: Abe - 199, Annabelle - 99, April - 117, Bob - 156, Bobby - 109, Brian - 150, Chris - 175, Claire - 124, Jason - 182, Jim - 150, Kevin - 145, Michael - 174, Richard - 200, Steven - 164, Tom - 212, 
    BUILD SUCCESSFUL (total time: 6 seconds)
*/