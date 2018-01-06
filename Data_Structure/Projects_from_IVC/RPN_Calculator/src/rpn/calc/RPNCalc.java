package rpn.calc;
import java.util.Scanner;

/**
 * @author Daniel Van Der Maden
 */
public class RPNCalc 
{
    public static void main(String[] args) 
    {
        Scanner user_input = new Scanner (System.in), in;
        Stack main = new Stack();
       
        //Initilization for dividing vars & temp for exit repeat condition
        double first, second, tempD = -1;
        //Keep Track of numbers parced - used for exit of repeat loop
        int numParced;
        //Initilization for oper compare later
        String tempS;
        //flags for printing and repeating
        boolean print, repeat = true;
        
        while(repeat)
        {
            //reset important tools of program
            System.out.print(">");
            in = new Scanner(user_input.nextLine()).useDelimiter(" ");
            print = true;
            numParced = 0;
            
            //Parcer starts here - using blank space as a delimiter
            while (in.hasNext())
            {
                if (in.hasNextDouble())
                {
                    //Temp & numParced here for repeat loop exit check later
                    tempD = in.nextDouble();
                    main.push(tempD);
                    numParced++;
                }
                else 
                {
                    //temp string needed because in.next().equals(arg) wont work
                    //if statments dont work with parcers?
                    tempS = in.next();  

                    //Comparing String Object
                    //logic: ( (operators || empty string) && nodecount) to pass
                    //       OR  just equal to pass
                    //       Needed so that '1 1 + =' wont throw an error
                    if( ((tempS.equals("+") || tempS.equals("-") 
                        || tempS.equals("*")|| tempS.equals("/")
                        || tempS.equals("")) && main.getNodeCount()>=2)
                        || tempS.equals("=") ) //"=" does nothing
                    {
                        switch (tempS) 
                        {
                        case "+":
                            main.push(main.pop() + main.pop());
                            break;
                        case "-":
                            main.push(-(main.pop()) + main.pop());
                            break;
                        case "/":
                            first = main.pop();
                            second = main.pop();
                            if(first == 0.0)
                            {
                                System.err.println("Divide by zero error");
                                print = false;
                            }
                            else
                                main.push(second / first);
                            break;
                        case "*":
                            main.push(main.pop()* main.pop());
                            break;
                        }
                    }
                    else
                    {
                        System.err.println("Operator Error");
                        print = false;
                    }
                }
            }

            //use numParced instead of getNodeCount() because '1 0 *' would
            //cause program to stop unintentionally
            if (tempD == 0.0 && numParced ==1)
                repeat = print = false;
            
            if(main.getNodeCount()>=2)
                System.err.println("Not enough operators");
            else if (print)
                System.out.println("= " + main.pop());
            
            //Clear the stack before each repeat
            while(main.getNodeCount()>=1) 
                main.pop();
        }
    }
}
//Output
/*
    >10 15 + =
    = 25.0
    >10 15 -
    = -5.0
    >2.5 3.5 +
    = 6.0
    >10 0 / =
    Divide by zero error
    >10 20 * /
    Operator Error
    >12 20 30 /
    Not enough operators
    >-10 -30 - =
    = 20.0
    >100 10 50 25 / * - -2 / =
    = -40.0
    >0
    BUILD SUCCESSFUL (total time: 1 minute 6 seconds)
*/