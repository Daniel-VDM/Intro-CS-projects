from lab04 import *

# Q13
def flatten(lst):
    """Returns a flattened version of lst.

    >>> flatten([1, 2, 3])     # normal list
    [1, 2, 3]
    >>> x = [1, [2, 3], 4]      # deep list
    >>> flatten(x)
    [1, 2, 3, 4]
    >>> x = [[1, [1, 1]], 1, [1, 1]] # deep list
    >>> flatten(x)
    [1, 1, 1, 1, 1, 1]
    """
    flat_list = []
    for i in lst:
        if type(i) == list:
            flat_list += flatten(i)
        else:
            flat_list += [i]
    return flat_list

# Q14
def merge(lst1, lst2):
    """Merges two sorted lists.

    >>> merge([1, 3, 5], [2, 4, 6])
    [1, 2, 3, 4, 5, 6]
    >>> merge([], [2, 4, 6])
    [2, 4, 6]
    >>> merge([1, 2, 3], [])
    [1, 2, 3]
    >>> merge([5, 7], [2, 4, 6])
    [2, 4, 5, 6, 7]
    """
    merged = []
    for _ in range(len(lst1)+len(lst2)):
        if len(lst1) == 0:
            merged += [lst2[0]]
            lst2 = lst2[1 :]
        elif len(lst2) == 0:
            merged += [lst1[0]]
            lst1 = lst1[1 :]
        elif lst1[0] > lst2[0]:
            merged += [lst2[0]]
            lst2 = lst2[1 :]
        else:
            merged += [lst1[0]]
            lst1 = lst1[1 :]
    return merged



