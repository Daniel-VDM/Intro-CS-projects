package heap;
import java.util.*;

/**
 * This is the main that test the two different type of heaps
 * 
 * @author Daniel Van Der Maden
 */
public class Heap
{
    public static void main(String[] args)
    {
        if (args.length != 1)
            System.exit(1);
        
        //Choose type of test
        String choose = args[0];
        
        if (choose.equals("Large")){
            for(int i = 100 ; i < 100000; i*=2){
                System.out.println("Size is: " + i + "\n");
                System.out.println("!!--REGULAR HEAP--!!");
                testNormalHeap(i);
                System.out.println("\n\n!!--NODE HEAP--!!");
                testNodeHeap(i);
                System.out.println("\n--------------------\n");
            }
        } else if (choose.equals("Small")){
            int size = 12;
            System.out.println("Size is: " + size + "\n");
            System.out.println("!!--REGULAR HEAP--!!");
            testNormalHeap(size);
            System.out.println("\n\n!!--NODE HEAP--!!");
            testNodeHeap(size);
            System.out.println("\n--------------------\n");
        }
        
    }
    
    //Test Code for the Default (Array based) heap
    public static void testNormalHeap(int size){
        ArrHeap h = new ArrHeap(size);
        Random r = new Random();
        
        //Adding to Heap
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 1; ; i++)
        {
            try
            {
                int newElement = r.nextInt();
                newElement = newElement < 0 ? ((-newElement) % 100)
                    : (newElement % 100);
                h.add(newElement);
            }
            catch (Exception e)
            {
                System.out.println("Heap filled up at insertion " + i);
                break;
            }
        }
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to build: " + (end - start) + " ms\n");
        
        //Removing from heap
        start = Calendar.getInstance().getTimeInMillis();
        for (int i = 1; ; i++)
        {
            try
            {
                h.remove();
            }
            catch (Exception e)
            {
                System.out.println("Heap emptied at removal " + i);
                break;
            }
        }
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to remove: " + (end - start) + " ms");
    }
    
    //Test Code for the Node based Heap
    public static void testNodeHeap(int size){
        NodeHeap h = new NodeHeap();
        Random r = new Random();
        
        //Adding to heap
        long start = Calendar.getInstance().getTimeInMillis();
        for (int i = 1;i <= size ; i++)
        {
            int newElement = r.nextInt();
            newElement = newElement < 0 ? ((-newElement) % 100)
                : (newElement % 100);
            h.add(newElement);
        }
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Heap filled up");
        System.out.println("Time to build: " + (end - start) + " ms\n");

        //Removing from heap
        start = Calendar.getInstance().getTimeInMillis();
        for (int i = 1; ; i++)
        {
            try
            {
                h.remove();
            }
            catch (Exception e)
            {
                System.out.println("Heap emptied at removal " + i);
                break;
            }
        }
        end = Calendar.getInstance().getTimeInMillis();
        System.out.println("Time to remove: " + (end - start) + " ms");
    }
}
