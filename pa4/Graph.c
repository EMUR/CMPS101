//
// Created by E on 11/9/16.
//

#include <stdlib.h>
#include "Graph.h"

typedef struct GraphObj {

    List* adj ;
    char* color;
    int* parent;
    int* distance;
    int order;
    int size;
    int source;
} GraphObj;

// constructor
Graph newGraph(int n)
{
    if (n < 1) {
        printf("Invalid order in newGraph() Hint:Check your input file\n");
        exit (1);
    }
    Graph G = malloc(sizeof(GraphObj));
    G->adj 	= malloc((n+1)*sizeof(List));
    G->color = malloc((n+1)*sizeof(int));
    G->parent = malloc((n+1)*sizeof(int));
    G->distance = malloc((n+1)*sizeof(int));
    G->order = n;
    G->size = 0;
    G->adj[0] = NULL;
    G->source = NIL;

    for (int i = 1; i <= n; i++) {
        G->adj[i] = newList();
        G->parent[i] = NIL;
        G->distance[i] = INF;
        G->color[i] = 'w';
    }
    return G;

}

// destructor
void freeGraph(Graph* pG)
{
    for (int i = 1; i <=getOrder(*pG); i++) {
        freeList(&(*pG)->adj[i]);
        free((*pG)->adj[i]);
    }
    free((*pG)->adj);
    free((*pG)->color);
    free((*pG)->distance);
    free((*pG)->parent);
    (*pG)->adj = NULL;
    (*pG)->color = NULL;
    (*pG)->parent = NULL;
    (*pG)->distance = NULL;
    free(*pG);
    *pG = NULL;
}

// return order
int getOrder(Graph G)
{
    if(G == NULL){
        printf("trying to get order for null graph! \n");
        exit(1);
    }
    return G->order;
}

// return size
int getSize(Graph G)
{
    if(G == NULL){
        printf("trying to get size for null graph!\n");
        exit(1);
    }
    return G->size;
}

// return size
int getSource(Graph G)
{
    if(G == NULL){
        printf("trying to get source for null graph! \n");
        exit(1);
    }
    return G->source;
}

// return parent of a given vertex
int getParent(Graph G, int u)
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

// return distance
int getDist(Graph G, int u)
{
    if(G == NULL){
        printf("trying to get distance for null graph! \n");
        exit(1);
    }
    if(G->source == 0){
        return -1;
    }
    return G->distance[u];
}


// return Path for a given vertex
void getPath(List L, Graph G, int u)
{
    if (getSource(G) == NIL) {
        printf("getPath called before BFS\n");
        exit (1);
    } if (G->source == u) {
        append(L, u);
    } else if (G->parent[u] != NIL) {
        getPath(L, G, G->parent[u]);
        append(L,u);
    }
}


// make given graph null
void makeNull(Graph G)
{
    if (G != NULL) {
        for (int i = 1; i <= getOrder(G); i++) {
            clear(G->adj[i]);
        }
        G->size = 0;
    } else {
        printf("makeNull called on NULL Graph pointer\n");
        exit(1);
    }
}


// add edge passing two vertices into the passed graph
void addEdge(Graph G, int u, int v)
{
    if(u < 1 || u > getOrder(G) || v < 1 || v > getOrder(G)) {
        printf("Graph verticies out of bounds\n");
        exit(1);
    }
    addArc(G, u, v);
    addArc(G, v, u);
    G->size--;
}

// add arc passing two vertices
void addArc(Graph G, int u, int v)
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

// BFS function
void BFS(Graph G, int s)
{
    for(int i = 0; i < (G->order + 1); ++i) {
        G->parent[i] = NIL;
        G->distance[i] = INF;
        G->color[i] = 'w';
    }
    G->source = s;
    G->distance[s] = 0;
    G->color[s] = 'g';
    List Q = newList();
    prepend(Q, s);
    while(length(Q) > 0) {
        int temp = back(Q);
        deleteBack(Q);
        List adj = G->adj[temp];
        moveFront(adj);
        while(index(adj) > -1) {
            int v = get(adj);
            if(G->color[v] == 'w') {
                G->color[v] = 'g';
                G->parent[v] = temp;
                G->distance[v] = G->distance[temp] + 1;
                prepend(Q, v);
            }
            moveNext(adj);
        }
    }
    freeList(&Q);
}


// print graph
void printGraph(FILE* out, Graph G)
{
    setbuf(out,NULL);

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