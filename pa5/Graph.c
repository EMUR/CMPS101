//
// Created by E on 11/27/16.
//

#include <stdlib.h>
#include "Graph.h"

typedef struct GraphObj {

    List *adj;
    int *discover;
    int *finish;
    int order;
    int size;
    int *color;
    int *parent;

    } GraphObj;


/* Constructors-Destructors */
// create new graph
Graph newGraph(int n)
{
    if (n < 1) {
        printf("Invalid order in newGraph() Hint:Check your input file\n");
        exit (1);
    }
    Graph G = malloc(sizeof(GraphObj));
    G->adj 	= malloc((n+1)*sizeof(List));
    G->color 		= malloc((n+1)*sizeof(int));
    G->parent 		= malloc((n+1)*sizeof(int));
    G->discover 	= malloc((n+1)*sizeof(int));
    G->finish	 	= malloc((n+1)*sizeof(int));
    G->order 		= n;
    G->size 		= 0;
    G->adj[0] = NULL;
    for (int i = 1; i <= n; i++) {
        G->adj[i] = newList();
        G->parent[i] 	= 0;
        G->discover[i] 	= INF;
        G->color[i] 	= 'w';
        G->finish[i] 	= INF;
    }
    return G;
}

// free graph
void freeGraph(Graph* pG)
{
    for (int i = 1; i <=getOrder(*pG); i++) {
        freeList(&(*pG)->adj[i]);
        free((*pG)->adj[i]);
    }
    free((*pG)->adj);
    free((*pG)->color);
    free((*pG)->discover);
    free((*pG)->finish);
    free((*pG)->parent);
    (*pG)->adj = NULL;
    (*pG)->color = NULL;
    (*pG)->parent = NULL;
    (*pG)->discover = NULL;
    (*pG)->finish = NULL;
    free(*pG);
    *pG = NULL;
}

/* Access functions */
// return order of graph
int getOrder(Graph G)
{
    if(G == NULL){
        printf("trying to get order for null graph! \n");
        exit(1);
    }
    return G->order;
}

// return size of graph
int getSize(Graph G)
{
    if(G == NULL){
        printf("trying to get size for null graph!\n");
        exit(1);
    }
    return G->size;
}


// return parent
int getParent(Graph G, int u) /* Pre: 1<=u<=n=getOrder(G) */
{
    if(G == NULL){
        printf("trying to get parent for null graph!\n");
        exit(1);
    }
    if(u<1 || u>G->order){
        printf("trying to get parent with invalid parameters!\n");
        exit(1);
    }
    return G->parent[u];
}

// return discover
int getDiscover(Graph G, int u) /* Pre: 1<=u<=n=getOrder(G) */
{
    if(G == NULL){
        printf("trying to get parent for null graph!\n");
        exit(1);
    }
    if(u<1 || u>G->order){
        printf("trying to get parent with invalid parameters!\n");
        exit(1);
    }
    return G->discover[u];
}

// return finish
int getFinish(Graph G, int u) /* Pre: 1<=u<=n=getOrder(G) */
{
    if(G == NULL){
        printf("trying to get parent for null graph!\n");
        exit(1);
    }
    if(u<1 || u>G->order){
        printf("trying to get parent with invalid parameters!\n");
        exit(1);
    }
    return G->finish[u];
}

/* Manipulation procedures */

// add arc to graph
void addArc(Graph G, int u, int v) /* Pre: 1<=u<=n, 1<=v<=n */
{
    if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
        printf("Graph verticies out of bounds\n");
        exit(1);
    }
    List S = G->adj[u];
    moveFront(S);
    while(index(S) > -1 && v > get(S)) {
        moveNext(S);
    }
    if(index(S) == -1)
        append(S, v);
    else
        insertBefore(S, v);
    G->size++;

}

// add edge to graph
void addEdge(Graph G, int u, int v) /* Pre: 1<=u<=n, 1<=v<=n */
{
    if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
        printf("Graph verticies out of bounds\n");
        exit(1);
    }
    addArc(G, u, v);
    addArc(G, v, u);
    G->size--;
}

// DFS function
void DFS(Graph G, List S) /* Pre: length(S)==getOrder(G) */
{
    if (G == NULL) {
        printf("Passed Null graph to DFS\n");
        exit(1);
    } else if (S == NULL) {
        printf("Passed Null list to DFS\n");
        exit(1);
    } else if (length(S) != getOrder(G)) {
        printf("Passed invalid list to DFS\n");
        exit(1);
    }

    for (int i = 1; i <= getOrder(G); i++) {
        G->color[i] 	= 'w';
        G->discover[i] 	= INF;
        G->finish[i]	= INF;
        G->parent[i] 	= 0;
    }
    int time = 0;

    List T = copyList(S);
    clear(S);
    for (advanceTo(T,0); index(T) != -1; moveNext(T)) {
        int u = get(T);
        if (G->color[u] == 'w') {
            visit(G, S, u, &time);
        }
    }
    freeList(&T);
}

// visit serves as a helper function for DFS
void visit(Graph G, List S, int u, int *time) {
    if (S == NULL || time == NULL) {
        printf("Passed null graph or null list to visit\n");
        exit (1);
    }
    G->discover[u] = ++(*time);
    G->color[u] = 'g';
    List adj = G->adj[u];
    for (advanceTo(adj, 0); index(adj) != -1; moveNext(adj)) {
        int v = get(adj);
        if (G->color[v] == 'w') {
            G->parent[v] = u;
            visit(G, S, v, time);
        }
    }
    G->finish[u] = ++(*time);
    G->color[u] = 'b';
    prepend(S, u);
}

/* Other Functions */
// return a transpose for a given graph
Graph transpose(Graph G)
{
    if (G == NULL) {
        printf("Passed null graph to Transpose\n");
    }
    Graph T = newGraph(getOrder(G));
    for (int i = 1; i <= getOrder(T); i++) {
        List I = G->adj[i];
        for (advanceTo(I, 0); index(I) != -1; moveNext(I)) {
            int x = get(I);
            addArc(T,x,i);
        }
    }
    return (T);

}

// return a copy of a given graph
Graph copyGraph(Graph G)
{
    if (G == NULL) {
        printf("Passed null graph to Copy\n");
    }
    Graph T = newGraph(getOrder(G));
    for (int i = 1; i <= getOrder(G); i++) {
        List I = G->adj[i];
        for (advanceTo(I, 0); index(I) != -1; moveNext(I)) {
            int x = get(I);
            addArc(T,i,x);
        }
    }
    return (T);
}

//print graph
void printGraph(FILE* out , Graph G)
{
    if (out == NULL) {
        exit(1);
    } else if (G == NULL) {
        exit(1);
    }
    for (int i = 1; i <= getOrder(G); i++) {
        fprintf(out,"%d:",i);
        printList(out,G->adj[i]);
        fprintf(out,"\n");
    }
}