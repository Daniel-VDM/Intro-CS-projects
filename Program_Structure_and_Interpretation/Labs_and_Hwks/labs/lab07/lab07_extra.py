from lab07 import *

# Q6
def cumulative_sum(t):
    """Mutates t where each node's root becomes the sum of all entries in the
    corresponding subtree rooted at t.

    >>> t = Tree(1, [Tree(3, [Tree(5)]), Tree(7)])
    >>> cumulative_sum(t)
    >>> t
    Tree(16, [Tree(8, [Tree(5)]), Tree(7)])
    """
    for b in t.branches:
        cumulative_sum(b)
    t.label = sum([b.label for b in t.branches]) + t.label

# Q7
def reverse_other(t):
    """Reverse the entries of every other level of the tree using mutation.

    >>> t = Tree(1, [Tree(2), Tree(3), Tree(4)])
    >>> reverse_other(t)
    >>> t
    Tree(1, [Tree(4), Tree(3), Tree(2)])
    >>> t = Tree(1, [Tree(2, [Tree(5, [Tree(7), Tree(8)]), Tree(6)]), Tree(3)])
    >>> reverse_other(t)
    >>> t
    Tree(1, [Tree(3, [Tree(5, [Tree(8), Tree(7)]), Tree(6)]), Tree(2)])
    """
    def reverse_children(t):
        lst = []
        for b in t.branches:
            lst.append(b.label)
        for b in t.branches:
            b.label = lst.pop()

    def reverse(t, level = 0):
        if level % 2 == 0:
            reverse_children(t)
        for b in t.branches:
            reverse(b,level+1)
    reverse(t)

# Q8
def deep_map_mut(fn, link):
    """Mutates a deep link by replacing each item found with the
    result of calling fn on the item.  Does NOT create new Links (so
    no use of Link's constructor)

    Does not return the modified Link object.

    >>> link1 = Link(3, Link(Link(4), Link(5, Link(6))))
    >>> deep_map_mut(lambda x: x * x, link1)
    >>> print(link1)
    <9 <16> 25 36>
    """
    if link is Link.empty:
        return
    if type(link.first) == Link:
        deep_map_mut(fn, link.first)
    else:
        link.first = fn(link.first)
    deep_map_mut(fn, link.rest)

# Q9
def has_cycle(link):
    """Return whether link contains a cycle.

    >>> s = Link(1, Link(2, Link(3)))
    >>> s.rest.rest.rest = s
    >>> has_cycle(s)
    True
    >>> t = Link(1, Link(2, Link(3)))
    >>> has_cycle(t)
    False
    >>> u = Link(2, Link(2, Link(2)))
    >>> has_cycle(u)
    False
    """
    lst = []
    def check(link):
        if link is Link.empty:
            return False
        if link in lst:
            return True
        else:
            lst.append(link)
            return check(link.rest)
    return check(link)


def has_cycle_constant(link):
    """Return whether link contains a cycle.
ui
    >>> s = Link(1, Link(2, Link(3)))
    >>> s.rest.rest.rest = s
    >>> has_cycle_constant(s)
    True
    >>> t = Link(1, Link(2, Link(3)))
    >>> has_cycle_constant(t)
    False
    """
    first_link = link
    def check(link):
        if link is Link.empty:
            return False
        if link is first_link:
            return True
        else:
            return check(link.rest)
    return link is Link.empty or check(link.rest)
