#######
# OOP #
#######

class Account:
    """An account has a balance and a holder.

    >>> a = Account('John')
    >>> a.deposit(10)
    10
    >>> a.balance
    10
    >>> a.interest
    0.02

    >>> a.time_to_retire(10.25) # 10 -> 10.2 -> 10.404
    2
    >>> a.balance               # balance should not change
    10
    >>> a.time_to_retire(11)    # 10 -> 10.2 -> ... -> 11.040808032
    5
    >>> a.time_to_retire(100)
    117
    """

    interest = 0.02  # A class attribute

    def __init__(self, account_holder):
        self.holder = account_holder
        self.balance = 0

    def deposit(self, amount):
        """Add amount to balance."""
        self.balance = self.balance + amount
        return self.balance

    def withdraw(self, amount):
        """Subtract amount from balance if funds are available."""
        if amount > self.balance:
            return 'Insufficient funds'
        self.balance = self.balance - amount
        return self.balance

    def time_to_retire(self, amount):
        """Return the number of years until balance would grow to amount."""
        assert self.balance > 0 and amount > 0 and self.interest > 0
        year, curr_balance = 0 , self.balance
        while curr_balance < amount:
            curr_balance += curr_balance * self.interest    #notice self.interest to access interest
            year+= 1
        return year

class FreeChecking(Account):
    """A bank account that charges for withdrawals, but the first two are free!

    >>> ch = FreeChecking('Jack')
    >>> ch.balance = 20
    >>> ch.withdraw(100)  # First one's free
    'Insufficient funds'
    >>> ch.withdraw(3)    # And the second
    17
    >>> ch.balance
    17
    >>> ch.withdraw(3)    # Ok, two free withdrawals is enough
    13
    >>> ch.withdraw(3)
    9
    >>> ch2 = FreeChecking('John')
    >>> ch2.balance = 10
    >>> ch2.withdraw(3) # No fee
    7
    >>> ch.withdraw(3)  # ch still charges a fee
    5
    >>> ch.withdraw(5)  # Not enough to cover fee + withdraw
    'Insufficient funds'
    """
    withdraw_fee = 1
    free_withdrawals = 2

    def withdraw(self, amount):
        """Subtract amount from balance if funds are available."""
        if self.free_withdrawals > 0:
            self.free_withdrawals -=1
            return Account.withdraw(self, amount)
        return Account.withdraw(self, amount + self.withdraw_fee)

###########
# Mobiles #
###########

# Tree definition

def tree(label, branches=[]):
    """Construct a tree with the given label value and a list of branches."""
    for branch in branches:
        assert is_tree(branch), 'branches must be trees'
    return [label] + list(branches)

def label(tree):
    """Return the label value of a tree."""
    return tree[0]

def branches(tree):
    """Return the list of branches of the given tree."""
    return tree[1:]

def is_tree(tree):
    """Returns True if the given tree is a tree, and False otherwise."""
    if type(tree) != list or len(tree) < 1:
        return False
    for branch in branches(tree):
        if not is_tree(branch):
            return False
    return True

def is_leaf(tree):
    """Returns True if the given tree's list of branches is empty, and False
    otherwise.
    """
    return not branches(tree)

def mobile(left, right):
    """Construct a mobile from a left side and a right side."""
    return tree(None, [left, right])

def sides(m):
    """Select the sides of a mobile."""
    return branches(m)

def side(length, mobile_or_weight):
    """Construct a side: a length of rod with a mobile or weight at the end."""
    return tree(length, [mobile_or_weight])

def length(s):
    """Select the length of a side."""
    return label(s)

def end(s):
    """Select the mobile or weight hanging at the end of a side."""
    return branches(s)[0]

def weight(size):
    """Construct a weight of some size."""
    assert size > 0
    return tree(size)

def size(w):
    """Select the size of a weight."""
    return label(w)

def is_weight(w):
    """Whether w is a weight, not a mobile."""
    return is_leaf(w)

def examples():
    t = mobile(side(1, weight(2)),
               side(2, weight(1)))
    u = mobile(side(5, weight(1)),
               side(1, mobile(side(2, weight(3)),
                              side(3, weight(2)))))
    v = mobile(side(4, t), side(2, u))
    return (t, u, v)


def total_weight(m):
    """Return the total weight of m, a weight or mobile.

    >>> t, u, v = examples()
    >>> total_weight(t)
    3
    >>> total_weight(u)
    6
    >>> total_weight(v)
    9
    """
    if is_weight(m):
        return size(m)
    else:
        return sum([total_weight(end(s)) for s in sides(m)])

def balanced(m):
    """Return whether m is balanced.

    >>> t, u, v = examples()
    >>> balanced(t)
    True
    >>> balanced(v)
    True
    >>> w = mobile(side(3, t), side(2, u))
    >>> balanced(w)
    False
    >>> balanced(mobile(side(1, v), side(1, w)))
    False
    >>> balanced(mobile(side(1, w), side(1, v)))
    False
    """
    # Could it be done better?
    check = []

    def balance(m):
        if is_weight(m):
            return size(m)
        torq = []
        for s in sides(m):
            edge = balance(end(s))
            torq += [edge*length(s)]

        nonlocal check
        check = torq

        if torq[0] == torq[1]:
            return total_weight(m)
        else:
            return 0

    balance(m) # sets check to final weight, left = element 1, right = element 2
    return check[0] == check[1]    


def with_totals(m):
    """Return a mobile with total weights stored as the label of each mobile.

    >>> t, _, v = examples()
    >>> label(with_totals(t))
    3
    >>> print(label(t))                           # t should not change
    None
    >>> label(with_totals(v))
    9
    >>> [label(end(s)) for s in sides(with_totals(v))]
    [3, 6]
    >>> [label(end(s)) for s in sides(v)]         # v should not change
    [None, None]
    """
    sub_trees = [side(length(s), with_totals(end(s))) for s in sides(m)]
    return tree(total_weight(m), sub_trees)
         

############
# Mutation #
############

def make_counter():
    """Return a counter function.

    >>> c = make_counter()
    >>> c('a')
    1
    >>> c('a')
    2
    >>> c('b')
    1
    >>> c('a')
    3
    >>> c2 = make_counter()
    >>> c2('b')
    1
    >>> c2('b')
    2
    >>> c('b') + c2('b')
    5
    """
    count = []
    def counter(x):
        count.append(x)
        times = 0
        for i in count:
            if i == x:
                times +=1
        return times
    return counter

def make_fib():
    """Returns a function that returns the next Fibonacci number
    every time it is called.

    >>> fib = make_fib()
    >>> fib()
    0
    >>> fib()
    1
    >>> fib()
    1
    >>> fib()
    2
    >>> fib()
    3
    >>> fib2 = make_fib()
    >>> fib() + sum([fib2() for _ in range(5)])
    12
    """
    d = {0:0, 1:1}
    count = -1
    def fib():
        nonlocal count
        count += 1
        if count < 2:
            return count
        d.update({count: d[count-1] + d[count-2]})
        return d[count]
    return fib

def make_withdraw(balance, password):
    """Return a password-protected withdraw function.

    >>> w = make_withdraw(100, 'hax0r')
    >>> w(25, 'hax0r')
    75
    >>> w(90, 'hax0r')
    'Insufficient funds'
    >>> w(25, 'hwat')
    'Incorrect password'
    >>> w(25, 'hax0r')
    50
    >>> w(75, 'a')
    'Incorrect password'
    >>> w(10, 'hax0r')
    40
    >>> w(20, 'n00b')
    'Incorrect password'
    >>> w(10, 'hax0r')
    "Your account is locked. Attempts: ['hwat', 'a', 'n00b']"
    >>> w(10, 'l33t')
    "Your account is locked. Attempts: ['hwat', 'a', 'n00b']"
    """
    wrong_pw = []

    def correct(pw):
        if pw != password:
            wrong_pw.append(pw)
            return False
        return True

    def withdraw(amount, pw):
        if len(wrong_pw) >= 3:
            return "Your account is locked. Attempts: " + str(wrong_pw)
        
        if not correct(pw):
            return 'Incorrect password'
            
        nonlocal balance
        if amount > balance:
           return 'Insufficient funds'
        balance = balance - amount
        return balance
    return withdraw

def make_joint(withdraw, old_password, new_password):
    """Return a password-protected withdraw function that has joint access to
    the balance of withdraw.

    >>> w = make_withdraw(100, 'hax0r')
    >>> w(25, 'hax0r')
    75
    >>> make_joint(w, 'my', 'secret')
    'Incorrect password'
    >>> j = make_joint(w, 'hax0r', 'secret')
    >>> w(25, 'secret')
    'Incorrect password'
    >>> j(25, 'secret')
    50
    >>> j(25, 'hax0r')
    25
    >>> j(100, 'secret')
    'Insufficient funds'

    >>> j2 = make_joint(j, 'secret', 'code')
    >>> j2(5, 'code')
    20
    >>> j2(5, 'secret')
    15
    >>> j2(5, 'hax0r')
    10

    >>> j2(25, 'password')
    'Incorrect password'
    >>> j2(5, 'secret')
    "Your account is locked. Attempts: ['my', 'secret', 'password']"
    >>> j(5, 'secret')
    "Your account is locked. Attempts: ['my', 'secret', 'password']"
    >>> w(5, 'hax0r')
    "Your account is locked. Attempts: ['my', 'secret', 'password']"
    >>> make_joint(w, 'hax0r', 'hello')
    "Your account is locked. Attempts: ['my', 'secret', 'password']"
    """
    def joint(amount, password):
        if password == new_password:
            return withdraw(amount, old_password)
        return withdraw(amount, password)

    withdraw_string = withdraw(0, old_password)
    if type(withdraw_string) == str:
        return withdraw_string
    return joint


class BadBankAccount(Account):
    """ A subclass of bank account that allows an account holder to overdraw
    once, and then prevents them from withdrawing more money. You should also
    implement the property method overdrawn, which allows an account holder to
    check if they are overdrawn.

    >>> harold_account = BadBankAccount('Harold')
    >>> harold_account.deposit(100)   # depositing my paycheck for the week
    100
    >>> harold_account.withdraw(101)  # buying dinner
    -1
    >>> harold_account.overdrawn
    True
    >>> harold_account.withdraw(100000)
    You have overdrawn, please add more money!
    -1
    >>> harold_account.deposit(10)
    9
    >>> harold_account.overdrawn
    False
    """
    overdrawn = False   # Recall PROPERTY METHODS FOR SOMETHING THAT IS JUST A BOOLEAN CHECK

    def withdraw(self, amount):
        """Decrease the account balance by amount and return the
        new balance.
        """
        if self.overdrawn:
            print("You have overdrawn, please add more money!")
            return         
        
        self.balance = self.balance - amount
        self.overdrawn = True if self.balance < 0 else False    # Reset the boolean!!

        return self.balance
