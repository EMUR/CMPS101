/**
 * Created by eyad murshid on 9/26/16.
 */
public class List {

    //Node inner class
    private class Node {

        Object data;
        Node next;
        Node prev;

        Node(Object data) {
            this.data = data;
            next = null;
            prev = null;
        }

        Node(Object data, Node nextNode, Node prevNode) {
            this.data = data;
            next = nextNode;
            prev = prevNode;
        }

        // toString():  overrides Object's toString() method
        public String toString() {
            return String.valueOf(data);
        }

    }

    // Fields
    private Node front;
    private Node back;
    private Node cursor;
    private int index;
    private int length;

    // Constructor
    List() {
        length = 0;
        front = null;
        cursor = null;
        back = null;
        index = -1;

    }

    // Returns the number of elements in this List.
    int length() {
        return length;
    }

    // Returns the number of elements in this List.
    // otherwise returns -1.
    int index() {
        return index;
    }

    // Returns front element. Pre: length()>0
    Object front() {
        if (length > 0) {
            return front.data;
        }
        return -1;
    }

    // Returns back element. Pre: length()>0
    Object back() {
        if (length > 0) {
            return back.data;
        }
        return -1;
    }

    // Returns cursor element. Pre: length()>0, index()>=0
    Object get() {
        if (length > 0) {
            return cursor.data;
        }
        return -1;
    }

    // Returns true if this List and L are the same integer
    // sequence. The cursor is ignored in both lists.
    boolean equals(List L) {
        Node firstList = L.front;
        Node secondList = this.front;

        if (L.length != this.length)
            return false;

        if (back != null && L.back != null) {
            if (L.back.data != this.back.data)
                return false;
        }

        if (firstList != null) {
            while (firstList.next != null) {
                if (firstList != null && secondList != null) {
                    if (firstList.data != secondList.data)
                        return false;
                }

                firstList = firstList.next;
                secondList = secondList.next;
            }
        }

        return true;

    }

    // Resets this List to its original empty state
    void clear() {
        cursor = front;
        if(cursor != null) {
            while (cursor.next != null) {
                Node temp = cursor;
                cursor = cursor.next;
                temp.next = null;
                temp.prev = null;
            }
            cursor = null;
            front = null;
            back = null;
            length = 0;
            index = -1;
        }

    }

    // If List is non-empty, places the cursor under the front element,
    // otherwise does nothing.
    void moveFront() {
        if (length > 0) {
            cursor = front;
            index = 0;
        }
    }

    // If List is non-empty, places the cursor under the back element,
    // otherwise does nothing.
    void moveBack() {
        if (length > 0) {
            cursor = back;
            index = length - 1;
        }
    }

    // If cursor is defined and not at front, moves cursor one step toward
    // front of this List, if cursor is defined and at front, cursor becomes
    // undefined, if cursor is undefined does nothing
    void movePrev() {
        if (cursor != null && !cursor.equals(front)) {
            cursor = cursor.prev;
            index--;
        } else if (cursor != null && cursor.equals(front)) {
            cursor = null;
            index = -1;
        } else
            return;

    }

    // If cursor is defined and not at back, moves cursor one step toward
    // back of this List, if cursor is defined and at back, cursor becomes
    // undefined, if cursor is undefined does nothing.
    void moveNext() {

        if (cursor != null && !cursor.equals(back)) {
            cursor = cursor.next;
            index++;
        } else if (cursor != null && cursor.equals(back)) {
            cursor = null;
            index = -1;
        } else
            return;
    }

    // Insert new element into this List. If List is non-empty,
    // insertion takes place before front element.
    void prepend(Object data) {
        if (length > 0) {
            Node newFront = new Node(data, front, null);
            front.prev = newFront;
            front = newFront;
            length++;
            index++;
        } else {
            Node newFront = new Node(data);
            front = newFront;
            back = newFront;
            length++;
            index++;
        }
    }

    // Insert new element into this List. If List is non-empty,
    // insertion takes place after back element.
    void append(Object data) {
        if (length > 0) {
            Node newFront = new Node(data, null, back);
            back.next = newFront;
            back = newFront;
            length++;
        } else {
            Node newFront = new Node(data);
            front = newFront;
            back = newFront;
            length++;
        }
    }

    // Insert new element before cursor.
    // Pre: length()>0, index()>=0
    void insertBefore(Object data) {
        if (length() <= 0) {
            return;
        } else if (index() < 0) {
            return;
        }
        Node newNode = new Node (data);
        if (index() == 0) {
            prepend(data);
        } else {
            newNode.next = cursor;
            newNode.prev = cursor.prev;
            cursor.prev.next = newNode;
            cursor.prev = newNode;
            index++;
            length++;
        }
    }

    // Inserts new element after cursor.
    // Pre: length()>0, index()>=0
    void insertAfter(Object data) {
        if (length() <= 0) {
            return;
        } else if (index() < 0) {
            return;
        }
        Node newNode = new Node (data);
        if (index() == length()-1) {
            append(data);
        } else {
            newNode.prev = cursor;
            newNode.next = cursor.next;
            cursor.next.prev = newNode;
            cursor.next = newNode;
            length++;
        }

    }

    void advanceTo(int i) {
        if (i == index) return;
        if (i >= length() || i < 0) {
            cursor = null;
            index = -1;
            return;
        }
        if (i == 0) cursor = front;
        if (i == length() - 1) cursor = back;
        else {
            // Direction value, 0 = left, 1 = right
            int direction = 0;
            // Determine three possible distances
            // d1, from back
            int d1 = length() - i;
            // d2, from front
            int d2 = i;
            // d3, from cursor
            int d3 = Math.abs(index - i);
            // Find the shortest distance
            int min = d1;
            if (min > d2) min = d2;
            if (min > d3) min = d3;
            //Set up traversal from the back
            if (min == d1) {
                cursor = back;
                index = length() - 1;
                direction = 0;
                // Set up traversal from the front
            } else if (min == d2) {
                cursor = front;
                index = 0;
                direction = 1;
                // Set up traversal from cursor
            } else if (min == d3) {
                if (i > index) direction = 1;
            }
            // Traverse the list
            for (int j = 0; j < i; j++) {
                if (direction == 1) moveNext();
                else movePrev();
            }
        }
        index = i;
    }

    // Deletes the front element. Pre: length()>0
    void deleteFront() {
        if (length > 0) {
            front = front.next;
            if (front != null) {
                front.prev = null;
            }
            length--;
        } else {
            front = null;
        }
    }

    // Deletes the back element. Pre: length()>0
    void deleteBack() {
        if (length > 0) {
            back = back.prev;
            if (back != null) {
                back.next = null;
            }
            length--;
        } else {
            back = null;
        }
    }

    // Deletes cursor element, making cursor undefined.
    // Pre: length()>0, index()>=0
    void delete() {
        if (length > 0 && index >= 0) {
            cursor.next.prev = cursor.prev;
            cursor.prev.next = cursor.next;
            cursor = null;
            index = -1;
            length--;
        }
    }


    // Overrides Object's toString method. Returns a String
    // representation of this List consisting of a space
// separated sequence of integers, with front on left.
    public String toString() {
        String str = "";
        for (Node N = front; N != null; N = N.next) {
            str += N.toString() + " ";
        }
        return str;
    }

    // Returns a new List representing the same integer sequence as this
    // List. The cursor in the new list is undefined, regardless of the
    // state of the cursor in this List. This List is unchanged.
    List copy() {
        List newList = new List();
        Node tempNode = this.front;
        while (tempNode.next != null) {
            newList.append(tempNode.data);
            tempNode = tempNode.next;
        }
        newList.append(tempNode.data);

        return newList;
    }
}
