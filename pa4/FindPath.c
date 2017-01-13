//
// Created by E on 11/7/16.
//

#include <stdio.h>
#include <stdlib.h>
#include "Graph.h"


int main (int argc, char* argv[]) {

    // check arguments
    if (argc != 3) {
        printf("Please include only two arguments only! Filepath in out \n");
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
        addEdge(G,first,second);
    }

    //print graph
    printGraph(out, G);


    List L = newList();
    int source, dest;
    // read all the lines of the input file
    while (fscanf(in, "%d %d", &source, &dest)) {
        if (source == 0 && dest == 0)
        {
            break;
        }
        BFS(G, source);
        getPath(L, G, dest);
        if (length(L) == 0) {
            fprintf(out, "\nThe distance from %d to %d is infinity \n", source, dest);
            fprintf(out, "No %d-%d path exists\n", source, dest);
        } else {
            fprintf(out, "\nThe distance from %d to %d is %d \n", source, dest, getDist(G, dest));
            fprintf(out, "A shortest %d-%d path is:", source, dest);
            printList(out, L);
            fprintf(out, "\n");
        }
        clear(L);
    }

    //free the memory! no memory leaks !
    freeGraph(&G);
    freeList(&L);
    fclose(in);
    fclose(out);
}