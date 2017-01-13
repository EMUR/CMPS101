//
// Created by E on 10/3/16.
//



#include <stdlib.h>
#include "List.h"


typedef struct NodeObj {
    int data;
    struct NodeObj *next;
    struct NodeObj *prev;
} NodeObj;

// private Node type
typedef NodeObj *Node;


typedef struct ListObject {
    Node front;
    Node back;
    Node cursor;
    int index;
    int length;

} ListObject;

void freeNode(Node *n) {
    if (n != NULL && *n != NULL) {
        free(*n);
        *n = NULL;
    }
}

Node newNode(int d) {
    Node x = malloc(sizeof(NodeObj));
    x->data = d;
    x->prev = NULL;
    x->next = NULL;
    return (x);
}


List newList(void) {
    List temp = malloc(sizeof(ListObject));
    temp->front = temp->back = temp->cursor = NULL;
    temp->index = -1;
    temp->length = 0;
    return temp;
}


void freeList(List* pL) {
    if ( pL == NULL || *pL == NULL ) {
        return;
    }
    while (length(*pL) != 0) {
        deleteFront(*pL);
    }
    free(*pL);
    *pL = NULL;
}

// Access functions -----------------------------------------------------------
int length(List L) {
    return L->length;
}

int index(List L) {
    return L->index;
}

int front(List L) {
    if (L->length > 0) {
        return L->front->data;
    }
    return -1;
}

int back(List L) {
    if (L->length > 0) {
        return L->back->data;
    }
    return -1;
}

int get(List L) {
    if (L == NULL || L->cursor == NULL) {
        return -1;
    } else {
        return (L->cursor->data);
    }
}

int equals(List A, List B) {
    Node firstList = A->front;
    Node secondList = B->front;

    if (A->length != B->length) {
        return 0;
    }
    if (A->back->data != B->back->data) {
        return 0;
    }
    while (firstList->next != NULL) {
        if (firstList->data != secondList->data) {
            return 0;
        }

        firstList = firstList->next;
        secondList = secondList->next;
    }
    return 1;
}

// Manipulation procedures ----------------------------------------------------
void clear(List L) {
    if(L->front == NULL) return;
    else{
        Node fronttmp = L->front;
        while(fronttmp != NULL){
            Node tmp = fronttmp->next;
            free(fronttmp);
            fronttmp = tmp;
        }
        L->front = L->back = L->cursor = NULL;
        L->length = 0;
    }
}

void advanceTo(List L, int i){
    if(L==NULL){
        printf("List Error: calling moveTo() on NULL List\n");
        exit(1);
    }
    if(i>=0 && i<=(length(L)-1)){
        int j = 0;
        L->cursor = L->front;
        while (i>0){
            L->cursor = L->cursor->next;
            i--;
            j++;
        }
        L->index = j;
    }
    else if(i==(length(L)-1)){
        L->cursor = L->back;
        L->index = (length(L)-1);
    }
    else {L->cursor = NULL;}
}

void moveFront(List L) {
    if (L->length > 0) {
        L->cursor = L->front;
        L->index = 0;
    }
}

void moveBack(List L) {
    if (L->length > 0) {
        L->cursor = L->back;
        L->index = L->length - 1;
    }
}

void movePrev(List L) {

    if (L->cursor != NULL && L->cursor != L->front) {
        L->cursor = L->cursor->prev;
        --L->index;

    } else if (L->cursor != NULL && L->cursor == L->front) {
        L->cursor = NULL;
        L->index = -1;
    } else {
        return;
    }
}

void moveNext(List L) {

    if (L->cursor != NULL && L->cursor != L->back) {
        L->cursor = L->cursor->next;
        ++L->index;
    } else if (L->cursor != NULL && L->cursor == L->back) {
        L->cursor = NULL;
        L->index = -1;
    } else {
        return;
    }
}

void prepend(List L, int data) {
    if (L->length > 0) {
        Node newNd = newNode(data);
        (newNd)->next = L->front;
        L->front->prev = (newNd);
        L->front = (newNd);
        ++L->length;
        ++L->index;

    } else {
        Node newNd = newNode(data);
        L->front = newNd;
        L->back = newNd;
        ++L->length;
        ++L->index;
    }

}

void append(List L, int data) {
    if (L->length > 0) {
        Node newNd = newNode(data);
        (newNd)->prev = L->back;
        L->back->next = (newNd);
        L->back = (newNd);
        ++L->length;

    } else {
        Node newNd = newNode(data);
        L->front = (newNd);
        L->back = (newNd);
        ++L->length;

    }
}

void insertBefore(List L, int data) {
    if (L->length > 0 && L->index >= 0) {
        if (L->length <= 0) {
            printf("inserting before in empty list\n");
            exit (1);
        } if (L->index < 0) {
            printf("inserting before with an indefined cursor\n");
            exit (1);
        } else if (L->cursor == L->front) {
            prepend(L, data);
        } else {
            Node N = newNode(data);
            N->prev = L->cursor->prev;
            N->next = L->cursor;
            L->cursor->prev->next = N;
            L->cursor->prev = N;
            L->index++;
            L->length++;
        }
    }

}

void insertAfter(List L, int data) {
    if (L->length > 0 && L->index >= 0) {
        Node newNd = newNode(data);
        (newNd)->data = data;
        (newNd)->next = L->cursor->next;
        (newNd)->prev = L->cursor;
        L->cursor->next =  (newNd);
        ++L->length;

    }
}

void deleteFront(List L) {
    Node tmp = NULL;
    if(length(L) == 0){
        fprintf(stderr,"deleting the front of an empty list");
        exit(EXIT_FAILURE);}
    else if(L == NULL){
        fprintf(stderr,"deleting the front of a null list");
        exit(EXIT_FAILURE);}
    else{
        tmp = L->front;
        if(length(L) > 1) {
            L->front = L->front->next;
            L->front->prev = NULL;
        }else {L->front = L->back = NULL;}
    }
    L->length--;
    freeNode(&tmp);
}

void deleteBack(List L) {
    Node tmp = NULL;
    if(length(L) == 0){
        fprintf(stderr,"deleting the back of an empty list");
        exit(EXIT_FAILURE);}
    else if(L == NULL){
        fprintf(stderr,"deleting the back of a null list");
        exit(EXIT_FAILURE);}
    else{
        tmp = L->back;
        if(length(L) > 1) {
            L->back = L->back->prev;
            L->back->next = NULL;
        }else {L->front = L->back = NULL;}
    }
    L->length--;
    freeNode(&tmp);
}

void delete(List L) {
    if (L->length > 0 && L->index >= 0) {
        if(L->cursor == L->back) {
            deleteBack(L);
        } else if(L->cursor == L->front) {
            deleteFront(L);
        } else {
            Node temp = L->cursor;
            L->cursor->next->prev = L->cursor->prev;
            L->cursor->prev->next = L->cursor->next;
            freeNode(&temp);
            L->index = -1;
            L->cursor = NULL;
            --L->length;
        }
    }
}


void printList(FILE *out, List L) {

    Node temp = L->front;


    if(out == NULL) {

        for (int i = 0; i < L->length; i++) {
            printf(" %d", temp->data);
            temp = temp->next;
        }
    }
    else
    {
        for (int i = 0; i < L->length; i++) {
            fprintf(out," %d", temp->data);
            temp = temp->next;
        }
    }
}

List copyList(List L) {

    List temp = newList();
    Node tempNode = L->front;
    while (tempNode->next != NULL)
    {
        append(temp,tempNode->data);
        tempNode = tempNode->next;
    }

    append(temp,tempNode->data);

    return temp;
}

