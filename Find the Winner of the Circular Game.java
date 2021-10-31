/* There are n friends that are playing a game. The friends are sitting in a circle and are numbered from 1 to n in clockwise order. More formally, moving clockwise from the ith friend brings you to the (i+1)th friend for 1 <= i < n, and moving clockwise from the nth friend brings you to the 1st friend.

The rules of the game are as follows:

Start at the 1st friend.
Count the next k friends in the clockwise direction including the friend you started at. The counting wraps around the circle and may count some friends more than once.
The last friend you counted leaves the circle and loses the game.
If there is still more than one friend in the circle, go back to step 2 starting from the friend immediately clockwise of the friend who just lost and repeat.
Else, the last friend in the circle wins the game.
Given the number of friends, n, and an integer k, return the winner of the game.

 

Example 1:


Input: n = 5, k = 2
Output: 3
Explanation: Here are the steps of the game:
1) Start at friend 1.
2) Count 2 friends clockwise, which are friends 1 and 2.
3) Friend 2 leaves the circle. Next start is friend 3.
4) Count 2 friends clockwise, which are friends 3 and 4.
5) Friend 4 leaves the circle. Next start is friend 5.
6) Count 2 friends clockwise, which are friends 5 and 1.
7) Friend 1 leaves the circle. Next start is friend 3.
8) Count 2 friends clockwise, which are friends 3 and 5.
9) Friend 5 leaves the circle. Only friend 3 is left, so they are the winner.
Example 2:

Input: n = 6, k = 5
Output: 1
Explanation: The friends leave in this order: 5, 4, 6, 2, 3. The winner is friend 1.
 

Constraints:

1 <= k <= n <= 500 */

/*Note : I realized I used the word kill instead of loose. It's just because I was thinking of the original Josephus problem when writing it. To comply exactly to the leetcode problem, just replace kill by loose when you read it.

Solution to the problem in Java
First approach (Naive)
The first approach could be to just translate the problem we have into code. Let's assume we define a classe Node with a num value (the integer 1, 2, 3, ...) and a prev and next values, which both are of type Node. Then we would have a class which represents what we have, a number as label, a follower and a previous.

class Node{
    final int num;
    Node next = null;
    Node prev = null;
    
    public Node(int num) {
        this.num = num;
    }
}
Then, what happend when we kill a Node ? We simply set the previous of the follower to be the previous of the current Node and same for the follower of the previous :

public void kill(){
    prev.next = next;
    next.prev = prev;
}
So now we have defined the Node with the behavior, let's start by initializing the nodes in the solution :

class Solution {
    public int findTheWinner(int n, int k) {
	    // We set the first node and keep it (to be able to set it as the follower of the last node we will create)
        Node firstNode = new Node(1);
		// This is just part of our loop
        Node prevNode = firstNode;
        for(int i=2; i<=n; i++) {
            Node currentNode = new Node(i);
            currentNode.prev = prevNode;
            prevNode.next = currentNode;
            prevNode = currentNode;
        }
		// Then set the last and first nodes
        firstNode.prev = prevNode;
        prevNode.next = firstNode;
    }
}
Now, what we have to do is pretty easy. Start from the last Node (the one before the first) and count k each time, and kill the Node on which we are. As the current Node will be the one killed, its next will be the one that we will start with. So we are still before the first one to start with :

Node currentNode = prevNode;
for(int i=0; i<n; i++) {
	for(int j=0; j<k; j++) {
		currentNode = currentNode.next;
	}
	currentNode.kill();
}
All we have to do finally is to return the num of the next node, the only one surviving.

return currentNode.next.num;
So the final solution is :

class Solution {
    public int findTheWinner(int n, int k) {
        Node firstNode = new Node(1);
        Node prevNode = firstNode;
        for(int i=2; i<=n; i++) {
            Node currentNode = new Node(i);
            currentNode.prev = prevNode;
            prevNode.next = currentNode;
            prevNode = currentNode;
        }
        firstNode.prev = prevNode;
        prevNode.next = firstNode;
        
        
        Node currentNode = prevNode;
        for(int i=0; i<n; i++) {
            for(int j=0; j<k; j++) {
                currentNode = currentNode.next;
            }
            currentNode.kill();
        }
        return currentNode.next.num;
    }
}

class Node{
    final int num;
    Node next = null;
    Node prev = null;
    
    public Node(int num) {
        this.num = num;
    }
    
    public void kill(){
        prev.next = next;
        next.prev = prev;
    }
}
Complexity
Time complexity is O(n × k) as we count k times for the n turns.
Space complexity is O(k) as we create k nodes for playing the game

Second Approach (Code optimization using Data Structure)
Remember good code is based in three criterias :

Time complexity
Space complexity
Readability
A good first step could be to reduce the code drastically, to make it more readable. What we can do here is using LinkedList instead of our Node custom class. The logic will just to pass the first value to the last one until we arrive to the one to kill. Let's see with n=5 and k=2 how we will perform.

=== Initialization ===
1 => 2 => 3 => 4 => 5
=== First round ===
1) we put the 1 to the end :
2 => 3 => 4 => 5 => 1
2) Second step, meaning current step = k, we just poll the node :
3 => 4 => 5 => 1
=== Second round ===
1) We put 3 at the end
4 => 5 => 1 => 3
2) We poll 4
5 => 1 => 3
=== Third round ===
1) We put 5 at the end
1 => 3 => 5
2) We poll 1
3 => 5
=== Forth round ===
1) We put 3 at the end
5 => 3
2) We poll 5
3
Now that it is clear, the question could be, how to put the first value to the last position in a LinkedList. Actually, it's quite easy : polling it and adding to the LinkedList: linkedList.add(linkedList.poll())

That being said, let's translate this into code :

class Solution {
    public int findTheWinner(int n, int k) {
	    // Initialisation of the LinkedList
        LinkedList<Integer> participants = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
		    participants.add(i);
		}
		
		int lastKilled = 0;
		// Run the game
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k-1; j++) {
			    participants.add(participants.poll());
			}
            lastKilled = participants.poll();
        }
        // Return the last one killed
        return lastKilled;
    }
}
Complexity
Time complexity is O(n × k) as we count k times for the n turns.
Space complexity is O(k) as we create k nodes for playing the game
In terms of time with LeetCode, the first solution is better than this one, especially because (add(poll)) is really time consuming in Java.

Interview tips
In an interview, the first or second solution will be good. But interviewer may ask you is there a better approach, and then, you can talk about time consumed by the first solution, and code complexity of the first one, and tell that you would make trade-offs based in this.

Third approach (Mathematical)
Basic approach
Let's assume we have f(n,k) which will give you the one remaining after nth turn in a group of nth people if we count k person at each turn. If you don't have any tool, it may take you few hours to realize how to calculate f(n,2). For the generic k, it will take you few hours just to understand the demonstration. So of course, this solution will never be expected from you in an interview.
So let's assume all participants are numbered 0 based, and that f(1,k) = 0 (if we have only one participant, numbered 0 as it is the first one, of course it's the one who will remain).
Then f(n,k) = (f(n-1, k)+k) mod n. Considering this, it's easy to code the solution :

class Solution {
    public int findTheWinner(int n, int k) {
        return findTheWinner0Based(n, k)+1;
    }
    
    public int findTheWinner0Based(int n, int k) {
        if(n == 1) {
            return 0;
        } else {
            return (findTheWinner0Based(n - 1, k) + k) % n;
        }
    }
}
Complexity
As we can see, we will iterate on the function n times, the time complexity is O(n).
Of course, calling recursive functions will have a space complexity of O(n) (storing all the temporary results of the method called)

Removing recursive function to have a space complexity of O(1)
We could remove the recursive calls in order to achieve a space complexity of O(1). Instead of starting from the last item and counting down until 1, we could start at one, and counting up until we reach n :

class Solution {
    public int findTheWinner(int n, int k) {
        int result = 0;
        for(int i=1; i<=n; i++) {
            result = (result + k) % i;
        }
        return result + 1;
    }
}
This last solution will give you a result of 0ms on leetcode, so you may think that you came up with the best solution. Actually, it's possible to make it even faster.

Improving time complexity
It's possible to decrease time complexity for certain scenarii using mathematical formulas. It's possible to achieve a time complexity of O(k × log(n)) which may be faster than O(n) especially when n is huge and k is little.

Don't try to understand this if you're not a mathematician (I'm not) ;-) */

class Solution {
    public int findTheWinner(int n, int k) {
        return findTheWinner0Based(n, k)+1;
    }
    
    public int findTheWinner0Based(int n, int k) {
        if(k == 1) return n-1;
        if(n == 1) return 0;
        if(k <= n) {
            return findTheWinnerForLargeN(n, k);
        } else {
            return findTheWinnerForSmallN(n, k);
        }
    }
    
    public int findTheWinnerForLargeN(int n, int k) {
        int np = n - (n/k);
        int result = (k*(((findTheWinner0Based(np, k) - n%k)+np) % np))/(k-1);
        return result;
    }
    
    public int findTheWinnerForSmallN(int n, int k) {
        int result = 0;
        for(int i=1; i<=n; i++) {
            result = (result + k) % i;
        }
        return result;
    }
}
 /*Complexity
Time complexity is O(k × log(n)) if n is higher than k, O(n) otherwise.
Space complexity is the same as time complexity as we are using recursive functions. */
