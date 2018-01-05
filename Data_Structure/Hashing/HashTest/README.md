# -Project-HashTest
This was part 1 of project 2 for Data Structures (CS47B)

************************************************
None of the code was written by me, instead I had to analyse the code.
************************************************

Short Description of the project (Copied from the prompt)
-------------------------------------------------------------
The program in HashTest.java and Hashtable.java reads words from a file and stores them into a hash table of linked lists. The name of the file and the size of the table are specified as command-line arguments. When the input has all been read, the program prints statistics about the hash table:

>- how many items it contains;
>- the length of its longest list;
>- the best possible length of the longest list;
>- the average number of comparisons needed to find each of the items in the table;
>- the standard deviation for the number of comparisons needed to find each of the items in the table.

Find out, by testing each of the four hash functions in Hashtable.java on all hash table sizes between 1040 and 1050, which function performs best and which performs worst on the strings in the file words. In particular, find out what table  sizes the functions work well for and what table sizes lead to poor performance. In general, the lower the average number of comparisons, the better, and for tables with averages approximately equal, the lower the standard deviation, the better.

The four hash functions are given below. In the first three formulas, ck is the kth character of the word to be hashed, where k ranges from 1 to n, the length of the word.

>*A variant on the ETH algorithm:*
>>h0 = 1
>>hk = ck * ((hk-1 mod 257)+1)
>>H = hn mod (table size)
>
>*A variant on the GNU-cpp algorithm:*
>>h0 = 0
>>hk = 4hk-1 + ck
>>H = (last 31 bits of hn) mod (table size)
>
>*A variant on the GNU-cc1 algorithm:*
>>h0 = n
>>hk = 613hk-1 + ck
>>H = (last 30 bits of hn) mod (table size)
>
>*The String hashCode member function.*
************************************************

##### PERSONAL NOTE #####
  
My analysis of the program can bee seen in "47B Hash Exercise - Sheet1.pdf"





