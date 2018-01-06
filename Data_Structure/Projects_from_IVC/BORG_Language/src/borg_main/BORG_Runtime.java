package borg_main;
import java.util.Scanner;
import java.io.IOException;
import java.util.NoSuchElementException;
/**
 * @author Daniel Van Der Maden
 */
public class BORG_Runtime 
{
    //Keeps Track of Scope
    //at initilization, scope info is saved to hash entry.
    private int scope;
    private final HashTable hashTable = new HashTable();

    //Default Constructor
    BORG_Runtime()
    {
        //Starts with -1, meaning there is no scope in play
        this.scope = -1;
    }
    
    /**
     * Main execute line
     * @param line one line of code from file
     * @throws IOException if syntax error or unsupported feature
     */
    public void execute(String line) throws IOException
    {
        Scanner s = new Scanner(cleanLine(line)).useDelimiter(" ");
        String varName = "COM", temp;
        //Try - Catch to ignore extra blank lines at the end of files
        try
        {
            varName = s.next(); 
        }
        catch (NoSuchElementException nsee)
        {
            throw new IOException ("Blank Line");
        }
        
        switch (varName) 
        {
            case "COM":
                break; //Do nothing for comments
            case "START":
                scope++; //keep track of scope
                break;
            case "FINISH":
                //Do a check for out-of-scope data and delete if out-of-scope
                hashTable.deleteNotInScope(--scope);
                break;
            case "VAR": //Initilization of a var
                varName = s.next(); 
                
                //Used if no value was set at initialization
                //integer minvalue used as pseudo-null place holders
                if (!s.hasNext())
                    hashTable.add(varName,Integer.MIN_VALUE,scope); 
                else
                {
                    //Set data for varName
                    if(!s.next().equals("="))
                        throw new IOException("Var initilization is "
                                + "not supported");
                    else
                    {
                        //Check to make sure there is something after '='
                        if (s.hasNext())
                            temp = s.next(); //String AFTER '='
                        else
                            throw new IOException("Cannot set value of " 
                                    + varName );

                        //Sets var without operations
                        if ( !s.hasNext())
                            hashTable.add(varName, getIntFrom(temp), scope);
                        //Sets var with operations
                        else
                        {
                            String oper = s.next(), var2;
                            //Checks for increment/decrement operators
                            if (!s.hasNext())
                            {
                                unaryMath(temp + " " + oper);
                                hashTable.add(varName, getIntFrom(temp)
                                        , scope);
                                break;
                            }
                            
                            //If its not an increment/decrement, then do 
                            //Binary math on the expression before seeting
                            //varName equal to it
                            //If error with doing math it deletes
                            //the useless hash entry
                            var2 = s.next();
                            hashTable.add(varName,Integer.MIN_VALUE,scope);
                            try
                            {
                                binaryMath(varName, temp + " " + oper 
                                        + " " + var2);
                            }
                            catch(IOException IOE)
                            {
                                hashTable.delete(varName);
                            }
                        }
                    }
                }    
                break;
            case "PRINT":
                varName = s.next();
                                
                //Check for operators / xtra tokens
                if (s.hasNext()) 
                {
                    printAction(varName, s.nextLine());
                    break;
                } 
                //Print with NO operators on print data
                else if(hashTable.contains(varName))
                    System.out.println(varName + " IS " 
                            + hashTable.getData(varName) );
                else
                    throw new IOException(varName +" IS UNDEFINED");
                break;
            default:
                //Check if its in hash table and sends it to method that deals
                //with operation on vars only
                if(hashTable.contains(varName))
                    unaryMath(varName + s.nextLine());
                else
                    throw new IOException("Syntax Error or unknown var");
        }
    }
    
    /**
     * print with operations on data, uses temp variable to perform binary math 
     * operations on it
     * @param varName variable name
     * @param rhs rest of the string after variable name
     * @throws IOException if operation is not supported
     */
    private void printAction(String varName, String rhs)throws IOException
    {
        Scanner s = new Scanner(rhs).useDelimiter(" ");
        String dumbVar = " ", oper, a2;
        try
        {
            oper = s.next();
        }
        catch(NoSuchElementException NSEE)
        {
            throw new IOException("printAction parce error for " + varName);
        }
        
        unaryMath(varName + " " + oper);
        
        if(s.hasNext())
        {
            a2=s.next();
            hashTable.add(dumbVar, Integer.MIN_VALUE, scope);
            binaryMath(dumbVar, varName + " " + oper + " " + a2);
            System.out.println(varName + " " + oper + " " + a2 + " IS "
                    + hashTable.getData(dumbVar));
            hashTable.delete(dumbVar);
        }
        
    }
    
    /**
     * Performs Unary math
     * @param line string line of expression
     * @throws IOException 
     */
    private void unaryMath(String line)throws IOException
    {
        Scanner s = new Scanner(line).useDelimiter(" ");
        String varName = "", oper = "";
        try
        {
            varName=s.next();
            oper=s.next();
        }
        catch(NoSuchElementException NSEE)
        {
            throw new IOException("Unable to parce data for unaryMath");
        }
        
        switch(oper)
        {
            case "++":
                hashTable.replace(varName, hashTable.getData(varName)+1);
                break;
            case "--":
                hashTable.replace(varName, hashTable.getData(varName)-1);
                break;
            case "=":
                if(s.hasNext())
                {
                    String temp = s.next();
                    //Checks for 2 tokens after '=' to determine that its
                    //a binary operation
                    if (s.hasNext())
                        binaryMath(varName, (temp + s.nextLine()) );
                    else 
                        hashTable.replace(varName, getIntFrom(temp));
                }                 
                break;
            default:
                break;
        }
    }
    
    
    
    /**
     * performs binary math 
     * @param VarName of where all the math is going to be stored
     * @param Expression the BINARY math expression ie:(a + b)
     * @throws IOException if unsupported syntax / arguments
     */
    private void binaryMath(String VarName, String Expression)throws IOException
    {
        Scanner s = new Scanner(Expression).useDelimiter(" ");
        String oper = "";
        int arg1,arg2;
        
        //Try-catch to make sure we have enough args/operators
        //getIntFrom method is used to determine what type of data is being
        //parced, int or variable refference.
        try 
        {
            arg1 = getIntFrom(s.next());
            oper = s.next();
            arg2 = getIntFrom(s.next());
        }
        catch (NoSuchElementException NSEE)
        {
            throw new IOException("Error parcing arg and operator for "
                    + "binary math on " + VarName);
        }
		
        //Throws error, if theres anything after the last operator
        if (s.hasNext())
            throw new IOException("Can not set value of " + VarName 
                    + ". BORG only supports binary operations");
        
        int temp;
        switch(oper)
        {
            case "+":
                temp= arg1 + arg2;
                hashTable.replace(VarName, temp);
                break;
            case "-":
                temp= arg1 - arg2;
                hashTable.replace(VarName, temp);
                break;
            case "*":
                temp= arg1 * arg2;
                hashTable.replace(VarName, temp);
                break;
            case "%":
                temp= arg1 % arg2;
                hashTable.replace(VarName, temp);
                break;
            case "/":
                temp= arg1 / arg2;
                hashTable.replace(VarName, temp);
                break;
            case "^":
                temp = (int)Math.pow(arg1, arg2);
                hashTable.replace(VarName, temp);
                break;
            default:
                 throw new IOException("Invalid Operator for Binary Math");
        }
    }
 
    /**
     * Gets the correct data format
     * @param tbdData the data that is to be determined
     * @return the int data type value of tbdData
     * @throws IOException if unsupported data
     */
    private int getIntFrom (String tbdData) throws IOException
    {
        if(hashTable.contains(tbdData))
            return hashTable.getData(tbdData);
        else if (tbdData.matches("[0-9]+"))
            return Integer.parseInt(tbdData);
        else
            throw new IOException("Unknown Data for " + tbdData);
    }
    
    /**
     * removes all tabs/spaces from start of line
     * @param input the string line
     * @return the string without the extra spaces/tabs
     */
    private static String cleanLine(String input)
    {
        //Takes Care of Tabs or extra space at the start of line
        int startIndex, temp;
        for(startIndex = 0; startIndex < input.length(); startIndex++)
        {
            temp = (int)input.charAt(startIndex);
            if(temp >= 65 && temp <=90)
                break;
        }
        return input.substring(startIndex);
    }
    
}
/**
 * PERSONAL NOTES:
 * 
 *  Program DOES NOT support floating point variable.
 *  RESERVED INT DATA: Integer.MIN_VALUE
 */