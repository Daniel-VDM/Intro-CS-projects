# -Project-Heap
This was third project for Data Structures (CS47B)

************************************************
The node version of the Heap was written by me.
************************************************

Short Description of the project (Copied from the prompt)
-------------------------------------------------------------
Code that implements a priority queue of int values as a binary max heap is supplied in Heap.java.

Modify the implementation so that it no longer uses an array (or ArrayList, or any other contiguous storage). Instead, your implementation should use explicitly linked heap nodes and must implement all the public operations (constructor, isEmpty, remove, top, add, and display) provided by the array version. The add and remove operations in your revised implementation should still run in time proportional in the worst case to the log of the number of queue elements. The constructor and the isEmpty and top operations should run in constant time.

*Your program tests should include the following sequence of actions on a heap initialized with at least six elements:*

>- Add an element that fills the bottom level of the heap.
>- Add one more element, which will create a new bottom level.
>- Remove an element from the heap.
>- Remove one more element from the heap (this should restore the heap to how it was just after initialization).
>- Add two more elements, and then remove two more elements.


**For more details on the project and its specification take a look at the prompt *(http://www-inst.eecs.berkeley.edu/~selfpace/47b/p3/heap-exercises/)*. Just incase it has been changed, I have included the webfiles for the Fall 2017 version of the project. Its in the folder titled "Project Prompt (Web Files)"**

************************************************

##### PERSONAL NOTE #####
  
I found this project to be interesting because I have only seen array implementation of Heaps prior to this. 

An interesting approach that I took with this was that I used a "tracer" node to keep track of where the next element in the heap would be added. Determining what node was the correct tracer proved to be the hardest of this project. After some research and thinking, I found that using the binary representation of the size of the heap was the best way of finding the tracer. Essentially, whenever I tried to find the tracer, I would iterate through the binary representation of the heap (in string form, not including the first character). The tracer would start off as the root. While iterating through the string, if the character was "0" I would assign the tracer to the left child and if the character was "1" I would assign the tracer to the right child. Once the interation finished, the tracer would be found. Doing this meant that finding the tracer took O(log n). 

Once I had the correct tracer, adding an element would take O(1). However since the tracer would be wrong after the insertion, I would have to refind the tracer, which meant that interting took O(log n). Deletion would also take O(log n) since I would have to refind the tracer after I popped off the heap and re-heapify the heap. *Note: for specificity, it would be O(2 log n) to delete because re-heapify takes O(log n).* So, because I found an efficient way of find the tracer I am able to have the same time complexity as an array based heap, which I find to be fascinating.


************************************************

##### IF YOU WANT TO RUN THE PROGRAM #####

**I included a folder (Titled "Runnable") with all the information and files needed to run it. *Please read the readme.***
