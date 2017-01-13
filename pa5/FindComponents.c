//
// Created by E on 11/27/16.
//

#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"

int main (int argc, char *argv[]) {

    // check arguments
    if (argc != 3) {
        printf("Please include only two arguments only! Filepath in.txt out.txt \n");
        exit (1);
    }

    // read arguments
    FILE *in, *out;
    in = fopen(argv[1], "r");
    out = fopen(argv[2], "w");
    if (in == NULL) {
        printf("Can't open file %s for reading.\n", argv[1]);
        exit(1);
    } if (out == NULL) {
        printf("Can't to open file %s for writing.\n", argv[2]);
        exit(1);
    }

    // look for size.
    int vert;
    fscanf(in, "%d", &vert);
    Graph G = newGraph(vert);


    // read edges
    int first,second;
    while (fscanf(in, "%d %d", &first, &second) == 2) {
        if (first == 0 && second == 0) break;
        addArc(G,first,second);
    }

    // Print
    fprintf(out, "Adjacency list representation of G:\n");
    printGraph(out,G);

    // Populate the S with the vertices of G
    List L = newList();
    for (int i = 1; i <= getOrder(G); i++) {
        append(L, i);
    }

    DFS(G, L);
    Graph T = transpose(G);
    DFS(T,L);

    // find number of strongly connected components
    int count = 0;
    for ( int i = 1; i <= getOrder(G); i++){
        if(getParent(T, i) == NIL)
        {
            count++;
        }
    }

    // print components on reverse
    fprintf(out, "\nG contains %d strongly connected components:\n", count);
    List stack = newList();
    for (int i = 1; i<= count; i++){
        fprintf(out, "Component %d:", i);

        while(getParent(T, back(L)) != NIL){
            prepend(stack, back(L));
            deleteBack(L);
        }

        prepend(stack, back(L));
        deleteBack(L);
        printList(out, stack);
        fprintf(out, "\n");
        clear(stack);
    }

    // Clean up
    freeGraph(&G);
    freeGraph(&T);
    freeList(&L);
    freeList(&stack);
    fclose(in);
    fclose(out);
}