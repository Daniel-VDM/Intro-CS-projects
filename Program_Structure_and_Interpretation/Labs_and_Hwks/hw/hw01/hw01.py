from operator import add, sub

def a_plus_abs_b(a, b):
    """Return a+abs(b), but without calling abs.

    >>> a_plus_abs_b(2, 3)
    5
    >>> a_plus_abs_b(2, -3)
    5
    """
    if b < 0:
        f = lambda x,y:x+(-1*y)
    else:
        f = lambda x,y:x+y
    return f(a, b)

def two_of_three(a, b, c):
    """Return x*x + y*y, where x and y are the two largest members of the
    positive numbers a, b, and c.

    >>> two_of_three(1, 2, 3)
    13
    >>> two_of_three(5, 3, 1)
    34
    >>> two_of_three(10, 2, 8)
    164
    >>> two_of_three(5, 5, 5)
    50
    """
    return max(a*a + b*b, a*a + c*c, b*b + c*c)

def largest_factor(n):
    """Return the largest factor of n that is smaller than n.

    >>> largest_factor(15) # factors are 1, 3, 5
    5
    >>> largest_factor(80) # factors are 1, 2, 4, 5, 8, 10, 16, 20, 40
    40
    >>> largest_factor(13) # factor is 1 since 13 is prime
    1
    """
    from math import sqrt , floor
    n = abs(n) #takes care of negative numbers
    i, max_factor = floor(sqrt(n)),1
    while i < n:
        if(n%i == 0):
            max_factor = i
        i +=1
    return max_factor



def if_function(condition, true_result, false_result):
    """Return true_result if condition is a true value, and
    false_result otherwise.

    >>> if_function(True, 2, 3)
    2
    >>> if_function(False, 2, 3)
    3
    >>> if_function(3==2, 3+2, 3-2)
    1
    >>> if_function(3>2, 3+2, 3-2)
    5
    """
    if condition:
        return true_result
    else:
        return false_result


def with_if_statement():
    """
    >>> with_if_statement()
    1
    """
    if c():
        return t()
    else:
        return f()

def with_if_function():
    return if_function(c(), t(), f()) #during the function call it will throw an error b/c of 'f()' call

def c():
    return True

def t():
    return 1 

def f():
    1/0 #to throw an error on purpose

def hailstone(n):
    """Print the hailstone sequence starting at n and return its
    length.

    >>> a = hailstone(10)
    10
    5
    16
    8
    4
    2
    1
    >>> a
    7
    """
    n = abs(n) # What about negative numbers?
    length = 1 # 1 b/c the arg counts as one, thus min length is always 1
    while n>1:
        print(n)
        if (n%2 == 0): #even check
            n = n//2
        else: #not even so must be odd
            n = n*3+1
        length += 1
    print(n)    #print when n = 1
    return length


