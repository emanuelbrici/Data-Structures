Emanuel Brici
SID: 11395261
email: emanuel.brici@gmail.com

Files:
SplaySymbolTable.java
Binomial.java
Node.java
README.txt

The above files build a SplaySymbolTable.

This project has two main components that will build
and search a Splay BST for nodes that need to be
placed into the tree or nodes that are being looked
for. Insert finds the correct place that a new
node needs to be placed makes a new node and inserts
it with the desired key and value. This node is then
splayed to the top of the tree. Search looks for a
desired node in the tree. If it finds this node it
splays it to the root and then returns the value.
If the node is not in the tree search will splay the
last node that it was at.

There is a very major difference between running the
binomial class with memoizetion and non-memoizetion.
The time with it binomial memoized took .035 seconds
complete. Comparing that with the non-memoized time,
which was a total of 155 seconds, a major difference
can be seen in computation times. When binomial was
ran with memoizetion and n = 35 there was a total of 2509
comparisons and 2014 rotations.

To run my code make sure that the above files are
all located in the same java project. The node is
constructed in its own class so this one must not
be forgotten. To run the code you need to save the
files and then run it through your IDE. To run
Binomial.java with it memoized line 52 needs to be
true and if you wish to run in non-memoized line
52 should = false. Save the class then run it in
whatever preference you please.