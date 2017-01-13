//
// Created by E on 11/30/16.
//

#include<stdio.h>
#include "List.h"
#include "Graph.h"

int main(void){
    Graph G = newGraph(8);
    Graph Trans;
    Graph Copy;
    List S = newList();
    printf("Graph order : %d\n",getOrder(G));
    printf("Graph size : %d\n",getSize(G));
    addArc(G,1,2);
    addArc(G,2,3);
    addArc(G,2,5);
    addArc(G,2,6);
    addArc(G,3,4);
    addArc(G,3,7);
    addArc(G,4,3);
    addArc(G,4,8);
    addArc(G,5,1);
    addArc(G,5,6);
    addArc(G,6,7);
    addArc(G,7,6);
    addArc(G,7,8);
    addArc(G,8,8);

    printf("\nAfter adding arcs..");


    printf("\n\nGraph order :  %d\n",getOrder(G));
    printf("Graph size :%d\n",getSize(G));
    printGraph(stdout,G);

    Trans = transpose(G);
    printf("\n\nTranspose of G\n");
    printGraph(stdout,Trans);

    Copy = copyGraph(G);
    printf("\n\nCopy of G\n");
    printGraph(stdout,Copy);

    int i;
    for( i =1;i<=getOrder(G);i++){
        append(S,i);
    }
    printf("\n\nList S\n");
    printList(stdout,S);


    DFS(G,S);
    printf("\n\nafter DFS\n");
    printf("List S\n");
    printList(stdout,S);

    i = back(S);
    printf("\nBack of List S: %d\n",i);
    i = front(S);
    printf("\nFront of List S: %d\n",i);

    advanceTo(S,length(S)-1);
    i = get(S);
    printf("\nCurrent last element: %d\n",i);

    printf("\nDFS with Tranpose(G)\n");
    DFS(Trans,S);
    printf("\nafter DFS\n");
    printf("\nList S\n");
    printList(stdout,S);

    return 0;
}