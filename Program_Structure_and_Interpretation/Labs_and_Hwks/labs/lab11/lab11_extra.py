# Extra Questions

from lab11 import *

# Q6
def hailstone(n):
    """
    >>> for num in hailstone(10):
    ...     print(num)
    ...
    10
    5
    16
    8
    4
    2
    1
    """
    while True:
        yield n
        if n % 2 == 0:
            n = n//2
        else:
            n = n*3+1
        # Genrators must have some stop case!
        if n == 1:
            yield n
            return



# Q7
def repeated(t, k):
    """Return the first value in iterable T that appears K times in a row.

    >>> s = [3, 2, 1, 2, 1, 4, 4, 5, 5, 5]
    >>> repeated(trap(s, 7), 2)
    4
    >>> repeated(trap(s, 10), 3)
    5
    >>> print(repeated([4, None, None, None], 3))
    None
    """
    assert k > 1
    i = iter(t)

    count = 1
    prev = next(i)

    while True:
        if count == k:
            return prev
        try:
            curr = next(i)
            if curr == prev:
                count += 1
            else:
                prev = curr
                count = 1
        except StopIteration:
            return



# Q8
def merge(s0, s1):
    """Yield the elements of strictly increasing iterables s0 and s1, removing
    repeats. Assume that s0 and s1 have no repeats. You can also assume that s0
    and s1 represent infinite sequences.

    >>> m = merge([0, 2, 4, 6, 8, 10, 12, 14], [0, 3, 6, 9, 12, 15])
    >>> type(m)
    <class 'generator'>
    >>> list(m)
    [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15]
    >>> def big(n):
    ...    k = 0
    ...    while True: yield k; k += n
    >>> m = merge(big(2), big(3))
    >>> [next(m) for _ in range(11)]
    [0, 2, 3, 4, 6, 8, 9, 10, 12, 14, 15]
    """
    i0, i1 = iter(s0), iter(s1)
    e0, e1 = next(i0, None), next(i1, None)
    while True:
        if e1 == None and e0 == None:
            return
        elif e0 == e1:
            yield e1
            e0, e1 = next(i0, None), next(i1, None)
        elif e0 == None and e1 != None:
            yield e1
            e1 = next(i1, None)
        elif e1 == None and e0 != None:
            yield e0
            e0 = next(i0, None)
        else:
            if e0 < e1:
                yield e0
                e0 = next(i0, None)
            else:
                yield e1
                e1 = next(i1, None)

# Q9
def remainders_generator(m):
    """
    Takes in an integer m, and yields m different remainder groups
    of m.

    >>> remainders_mod_four = remainders_generator(4)
    >>> for rem_group in remainders_mod_four:
    ...     for _ in range(3):
    ...         print(next(rem_group))
    0
    4
    8
    1
    5
    9
    2
    6
    10
    3
    7
    11
    """
    def mods(remainder):
        numer = 0
        while True:
            if numer % m == remainder:
                yield numer
            numer +=1

    remainder = -1
    for _ in range(m):
        remainder += 1
        yield mods(remainder)
    raise StopIteration


# Q10
def zip_generator(*iterables):
    """
    Takes in any number of iterables and zips them together.
    Returns a generator that outputs a series of lists, each
    containing the nth items of each iterable.
    >>> z = zip_generator([1, 2, 3], [4, 5, 6], [7, 8])
    >>> for i in z:
    ...     print(i)
    ...
    [1, 4, 7]
    [2, 5, 8]
    """
    iterators = [iter(x) for x in iterables]

    while True:
        try:
            yield [next(x) for x in iterators]
        except StopIteration:
            return