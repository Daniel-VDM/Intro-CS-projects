package bstproject_main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Daniel Van Der Maden
 */
public class BSTProject_Main 
{
    //Simple Print Function
    public static void print(BinarySearchTree main)
    {
        System.out.println("\n\t**Tree Info**\n\n--The Traverse - In Order--");
        main.printInOrder();
        System.out.println("\n" + "--The Traverse - Pre Order--");
        main.printPreOrder();
        System.out.println("\n" + "--The Traverse - Post Order--");
        main.printPostOrder();
        System.out.println("\n\nThe height is: " + main.height());
        System.out.println("The leaf count is: " + main.leafCount());
        System.out.println("The min weight is: " + main.minWeight());
        System.out.println("The the first name in alphabetical order is: "
                + main.firstAlphaName());
        System.out.println();
    }
    
    //Search tree function
    public static void search_tree (BinarySearchTree main)
    {
        //tools
        Boolean repeat = true;
        Scanner user_input = new Scanner(System.in);
        String user;
        while (repeat)
        {
            System.out.print("What name would you like to search for?"
                    + " Enter '-1' to quit\n>");
            user = user_input.nextLine();
            if (user.equals("-1")) //exit condition check
                repeat = false;
            else
                main.search(user);
            System.out.println();
        }
    }

    //Main
    public static void main(String[] args) 
    {
        String FILENAME = "Names.txt";
        
        //File read tools
        BufferedReader br = null;
		FileReader fr = null;
        //The Data Structure Instantiation
        BinarySearchTree main = new BinarySearchTree();
        
        //Boolean to print stop print if there is an error
        boolean print = true;
        
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
                    main.insert(name, weight);
            }
        } 
        catch (IOException e) 
        {
            System.out.println("\n\t***ERROR***\n\tFile Read error."
                    + "\n\tProgram Terminated.\n");
            print=false;
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
                print=false;
            }
        }
        
        if(print)
        {
            //Prints info of tree
            print(main);
            //Search tree
            search_tree(main);
        }
    }
}
//Output
/**
    Please enter path to desired file.
    >C:\Users\Daniel Van Der Maden\Desktop\Names.txt

            **Tree Info**

    --The Traverse - In Order--
    Abe 199, Annabelle 99, April 117, Bob 156, Bobby 109, Brian 150, Chris 175, Claire 124, Jason 182, Jim 150, Kevin 145, Michael 174, Richard 200, Steven 164, Tom 212, 
    --The Traverse - Pre Order--
    Jim 150, Abe 199, Annabelle 99, April 117, Bob 156, Bobby 109, Brian 150, Chris 175, Claire 124, Jason 182, Kevin 145, Michael 174, Richard 200, Steven 164, Tom 212, 
    --The Traverse - Post Order--
    Abe 199, Annabelle 99, April 117, Bob 156, Bobby 109, Brian 150, Chris 175, Claire 124, Jason 182, Kevin 145, Michael 174, Richard 200, Steven 164, Tom 212, Jim 150, 

    The height is: 6
    The leaf count is: 6
    The min weight is: 99
    The the first name in alphabetical order is: Abe

    What name would you like to search for? Enter '-1' to quit
    >April
            The weight for 'April' is: 117
    What name would you like to search for? Enter '-1' to quit
    >Daniel
            The name 'Daniel' is not found.
    What name would you like to search for? Enter '-1' to quit
    >Jim
            The weight for 'Jim' is: 150
    What name would you like to search for? Enter '-1' to quit
    >-1

    BUILD SUCCESSFUL (total time: 24 seconds)
 */