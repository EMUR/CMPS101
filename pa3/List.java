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
        if (length() == 0) {
            return null;
        } else if (index == -1) {
            return null;
        } else {
            return cursor.data;
        }
    }

    // Resets this List to its original empty state
    void clear() {
        cursor = front;
        if (cursor != null) {
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
        Node newNode = new Node(data);
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
        Node newNode = new Node(data);
        if (index() == length() - 1) {
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
        if (i == 0)
        {
            cursor = front;
        }
        if (i == length() - 1)
        {
            cursor = back;
        }
        else {
            int splitter = 0;
            int first = length() - i;
            int second = i;
            int third = Math.abs(index - i);
            int min = first;
            if (min > second)
            {
                min = second;
            }
            if (min > third)
            {
                min = third;
            }
            if (min == first) {
                cursor = back;
                index = length() - 1;
                splitter = 0;
            } else if (min == second) {
                cursor = front;
                index = 0;
                splitter = 1;
            } else if (min == third) {
                if (i > index) splitter = 1;
            }
            for (int j = 0; j < i; j++) {
                if (splitter == 1) moveNext();
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

    public boolean equals(Object x) {
        List L;
        if (x instanceof List) {
            L = (List) x;
            if (this.length == L.length) {
                Node firstList = this.front;
                Node secondList = L.front;
                while (firstList != null && secondList != null) {
                    if (firstList.data.equals(secondList.data)) {
                        firstList = firstList.next;
                        secondList = secondList.next;
                    } else return false;
                }
                return true;
            } else {
                return false;
            }
        } else return false;
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
}
