package borg_main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
/**
 * @author Daniel Van Der Maden
 */

public class BORG_Main 
{
    public static void main(String[] args) 
    {
        //Gets path of file
        Scanner user_input = new Scanner (System.in);
        System.out.print("Please enter path to BORG source file."
                + "(ex: D:\\Program.txt) \n>");
        String FILENAME = user_input.nextLine();
        
        //File read tools
        BufferedReader br = null;
        FileReader fr = null;
        //Runtime for BORG
        BORG_Runtime BORG = new BORG_Runtime();
        
        //Tools for source file print at the end.
        String temp = "";
        System.out.println("\n**********BORG OUTPUT**********\n"
                + "\t----------------");
        
        try 
        {
            //File read tools
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);
            
            //Data before adding to list
            String executeString;

            br = new BufferedReader(new FileReader(FILENAME));
           
            int lineCount = 0;
            
            while ((executeString = br.readLine()) != null ) 
            {
                //Used to store line for source print
                temp += executeString + "\n"; 
                lineCount++;
                try
                {
                    BORG.execute(executeString); //execute line
                }
                catch(IOException runTime)
                {
                    System.out.println("*ERROR* at line (" + lineCount
                            + ")-> " + runTime.getMessage());
                }
            }
        } 
        catch (IOException e) 
        {
            System.out.println("\n\t***ERROR***\n\tFile Read error."
                    + "\n\tProgram Terminated.\n");
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
            }
            catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        }
        //Simple ouput of source file
        System.out.println("\t----------------\n**********SOURCE FILE"
                + "**********\n\t----------------");
        System.out.println(temp + "\t----------------");
     }
}
//Output with Sample program
/*
    Please enter path to BORG source file.
    >C:\Users\Daniel Van Der Maden\Desktop\BORG_Main\Program.txt
    **********BORG OUTPUT**********
            ----------------
    BORAMIR IS 25
    LEGOLAS IS 101
    *ERROR* at line (9)-> GANDALF IS UNDEFINED
    BORAMIR * 2 IS 52
    GANDALF IS 49
    BORAMIR IS 26
    *ERROR* at line (19)-> GANDALF IS UNDEFINED
    LEGOLAS IS 1000
    LEGOLAS IS 1000
    LEGOLAS IS 999
            ----------------
    **********SOURCE FILE**********
            ----------------
    COM HERE IS OUR FIRST BORG PROGRAM
    COM WHAT A ROBUST LANGUAGE IT IS
    START
       VAR BORAMIR = 25
       VAR LEGOLAS = 101
       PRINT BORAMIR
       BORAMIR ++
       PRINT LEGOLAS
       PRINT GANDALF
       PRINT BORAMIR * 2
       COM
       COM NESTED BLOCK
       COM
       START
          VAR GANDALF = 49
          PRINT GANDALF
          PRINT BORAMIR
       FINISH
       PRINT GANDALF
       START
          LEGOLAS = 1000
          PRINT LEGOLAS
       FINISH
       PRINT LEGOLAS
       LEGOLAS --
       PRINT LEGOLAS
    FINISH
            ----------------
    BUILD SUCCESSFUL (total time: 0 seconds)
*/

//Output With My test code
/*
    Please enter path to BORG source file.
    >C:\Users\Daniel Van Der Maden\Desktop\BORG_Main\ProgramTest.txt

    **********BORG OUTPUT**********
            ----------------
    ANSWER IS -2147483648
    ARG1 IS 10
    ARG2 IS 3
    ARG1 ^ ARG2 IS 1000
    ANSWER IS 7
    ANSWER IS 8
    *ERROR* at line (17)-> ARG1 IS UNDEFINED
    ANSWER / ANSWER IS 1
    ANSWER IS 8
    newANSWER IS 9
    *ERROR* at line (23)-> ARG2 IS UNDEFINED
    newANSWER * ANSWER IS 72
    ANSWER IS 8
    *ERROR* at line (27)-> newANSWER IS UNDEFINED
    ANSWER IS 3
    decANSWER IS 2
    ANSWER IS 2
            ----------------
    **********SOURCE FILE**********
            ----------------
    COM This is my BORG program
    COM It will do some math and print the answer
    START
       VAR ANSWER
       PRINT ANSWER
       START
          VAR ARG1 = 10
          VAR ARG2 = 3
          PRINT ARG1
          PRINT ARG2
          PRINT ARG1 ^ ARG2
          ANSWER = ARG1 - ARG2
          PRINT ANSWER
          ANSWER ++
          PRINT ANSWER
       FINISH
       PRINT ARG1
       PRINT ANSWER / ANSWER
       PRINT ANSWER
       START
          VAR newANSWER = ANSWER + 1
          PRINT newANSWER
          PRINT ARG2
          PRINT newANSWER * ANSWER
       FINISH
       PRINT ANSWER
       PRINT newANSWER
       ANSWER = ANSWER % 5
       PRINT ANSWER
       VAR decANSWER = ANSWER --
       PRINT decANSWER
       PRINT ANSWER
    FINISH
            ----------------
    BUILD SUCCESSFUL (total time: 0 seconds)
*/