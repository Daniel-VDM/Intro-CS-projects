HW_SOURCE_FILE = 'hw04.py'

###############
#  Questions  #
###############

def intersection(st, ave):
    """Represent an intersection using the Cantor pairing function."""
    return (st+ave)*(st+ave+1)//2 + ave

def street(inter):
    return w(inter) - avenue(inter)

def avenue(inter):
    return inter - (w(inter) ** 2 + w(inter)) // 2

w = lambda z: int(((8*z+1)**0.5-1)/2)

def taxicab(a, b):
    """Return the taxicab distance between two intersections.

    >>> times_square = intersection(46, 7)
    >>> ess_a_bagel = intersection(51, 3)
    >>> taxicab(times_square, ess_a_bagel)
    9
    >>> taxicab(ess_a_bagel, times_square)
    9
    """
    st1 , st2   = abs(street(a)), abs(street(b))
    ave1, ave2  = abs(avenue(a)), abs(avenue(b))
    return (max(st1,st2) - min(st1,st2)) + (max(ave1,ave2) - min(ave1,ave2))

from math import sqrt, floor
def squares(s):
    """Returns a new list containing square roots of the elements of the
    original list that are perfect squares.

    >>> seq = [8, 49, 8, 9, 2, 1, 100, 102]
    >>> squares(seq)
    [7, 3, 1, 10]
    >>> seq = [500, 30]
    >>> squares(seq)
    []
    """
    #recall doing this step by step. Missed the part where i evaluate what goes into the list
    return [floor(sqrt(x)) for x in s if floor(sqrt(x)) == sqrt(x)]

def g(n):
    """Return the value of G(n), computed recursively.

    >>> g(1)
    1
    >>> g(2)
    2
    >>> g(3)
    3
    >>> g(4)
    10
    >>> g(5)
    22
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'g', ['While', 'For'])
    True
    """
    assert n > 0
    if n <= 3:
        return n
    return g(n-1) + 2 * g(n-2) + 3 * g(n-3)

def g_iter(n):
    """Return the value of G(n), computed iteratively.

    >>> g_iter(1)
    1
    >>> g_iter(2) 
    2
    >>> g_iter(3)
    3
    >>> g_iter(4)
    10
    >>> g_iter(5)
    22
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'g_iter', ['Recursion'])
    True
    """
    assert n > 0
    g = {1:1, 2:2, 3:3}
    if n > 3:
        for i in range(4,n+1):
            #note the syntax for adding a new key to the dictionary
            g[i] = g[i-1] + 2 * g[i-2] + 3 * g[i-3]
    return g[n]


def pingpong(n):
    """Return the nth element of the ping-pong sequence.

    >>> pingpong(7)
    7
    >>> pingpong(8)
    6
    >>> pingpong(15)
    1
    >>> pingpong(21)
    -1
    >>> pingpong(22)
    0
    >>> pingpong(30)
    6
    >>> pingpong(68)
    2
    >>> pingpong(69)
    1
    >>> pingpong(70)
    0
    >>> pingpong(71)
    1
    >>> pingpong(72)
    0
    >>> pingpong(100)
    2
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'pingpong', ['Assign', 'AugAssign'])
    True
    """
    #recall that inorder to concatenate lists, the two arguments must also be lists
    #recall that recursion requires a return on the recursive call if you dont want anything to print through each recursion
    #base case is always what is the simplest case, test what happens when you want 0
    def pong(i, direction, lst):
        if i >= n:
            return lst[i-1]
        if has_seven(i) or i % 7 == 0:
            return pong( i+1, direction * -1, 
                lst + [lst[i-1]+(direction * -1)] )
        return pong( i+1, direction, 
            lst + [lst[i-1]+direction] )
   
    return pong(1,1,[1])

def has_seven(k):
    """Returns True if at least one of the digits of k is a 7, False otherwise.

    >>> has_seven(3)
    False
    >>> has_seven(7)
    True
    >>> has_seven(2734)
    True
    >>> has_seven(2634)
    False
    >>> has_seven(734)
    True
    >>> has_seven(7777)
    True
    """
    if k % 10 == 7:
        return True
    elif k < 10:
        return False
    else:
        return has_seven(k // 10)

def count_change(amount):
    """Return the number of ways to make change for amount.

    >>> count_change(7)
    6
    >>> count_change(10)
    14
    >>> count_change(20)
    60
    >>> count_change(100)
    9828
    """
    #this is a version of count partitions -  a form of tree recursion
    def count(n, cent_Pwr = 0):
        #if you hit 0, you have found a way to take everything in a whole amount
        if n == 0:
            return 1
        #if you go lower than n, it is an invalid combination
        #if your cent value is bigger than the remaining amount, there is no way to successfully take away from the amount
        if n < 0 or 2**cent_Pwr > n:
            return 0
        #Try taking out the current max amount of cents.
        #Then at each recursive step you could take away 2 time more.
        #Add the ways in which each way would work
        return count(n-(2**cent_Pwr), cent_Pwr) + count(n, cent_Pwr + 1)

    return count(amount)


###################
# Extra Questions #
###################

from operator import sub, mul

def make_anonymous_factorial():
    """Return the value of an expression that computes factorial.

    >>> make_anonymous_factorial()(5)
    120
    >>> from construct_check import check
    >>> check(HW_SOURCE_FILE, 'make_anonymous_factorial', ['Assign', 'AugAssign', 'FunctionDef', 'Recursion'])
    True
    """
    #uses the recude function for lists. Not sure how else to do it
    from functools import reduce
    return lambda n: reduce(lambda x,y:x*y,[1]+list(range(1,n+1)))
