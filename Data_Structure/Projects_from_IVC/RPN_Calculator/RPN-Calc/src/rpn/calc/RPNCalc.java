package rpn.calc;
import java.util.Scanner;
public class RPNCalc 
{
    public static void main(String[] args) 
    {
        Scanner user_input = new Scanner (System.in);
        Stack main = new Stack();
        String input = user_input.nextLine(); //delim filter
        Scanner in = new Scanner(input).useDelimiter(" "); //delim param
       
        //Initilization for dividing vars
        double first, second;
        //Initilization for Operator
        String tempS;
        //Var for printing or not
        boolean print = true;
        
        while (in.hasNext())
        {
            if (in.hasNextDouble())
                main.push(in.nextDouble());
            else 
            {
                tempS = in.next();  //needed b.c switch wont work with in.next()
                
                if(tempS.equals("+") || tempS.equals("-") //obj equal for string
                    || tempS.equals("*")|| tempS.equals("/")
                    || tempS.equals("") )
                {
                    switch (tempS) 
                    {
                    case "+":
                        if(main.getNodeCount()>=2)
                            main.push(main.pop() + main.pop());
                        else
                        {
                            System.err.println("Too Many Operators");
                            print = false;
                        }
                        break;
                    case "-":
                        if(main.getNodeCount()>=2)
                            main.push(-(main.pop()) + main.pop());
                        else
                        {
                            System.err.println("Too Many Operators");
                            print = false;
                        }
                        break;
                    case "/":
                        if(main.getNodeCount()>=2)
                        {
                            first = main.pop();
                            second = main.pop();
                            if(first == 0.0)
                            {
                                System.err.println("Divide by zero error");
                                print = false;
                            }
                            else
                                main.push(second / first);
                        }
                        else
                        {
                            System.err.println("Too Many Operators");
                            print = false;
                        }
                        break;
                    case "*":
                        if(main.getNodeCount()>=2)
                            main.push(main.pop()* main.pop());
                        else
                        {
                            System.err.println("Too Many Operators");
                            print = false;
                        }
                        break;
                    }
                }
                else
                {
                    System.err.println("Invalid Operator");
                    print = false;
                }
            }
        }
        in.close();
        
        if(main.getNodeCount()>=2)
            System.err.println("Not enough operators");
        else if (print)
            System.out.println("= " + main.pop());
    }
    
}
