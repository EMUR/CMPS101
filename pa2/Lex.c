#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "List.h"



int main ( int argc, char *argv[] ) {
    if (argc != 3) {
        printf("YOU MAUST PASS TWO ARGUMENTS");
        return 1;
    }

    char *fileIn = malloc(strlen(argv[1]+5));
    char *fileOut = malloc(strlen(argv[2]+5));
        sprintf(fileIn, "%s", argv[1]);
    sprintf(fileOut, "%s", argv[2]);


    FILE *fp;
    char buff[255];

    fp = fopen(fileIn, "r");

    if (fp == NULL)
    {
        perror("NO INPUT FILE");
    }
    int lines = 0;

    while (fgets(buff, 255, (FILE *) fp)) {
        if(buff[0] != '\n'&& buff[0] != '\0')
            ++lines;
    }


    char strArray[lines - 1][255];

    rewind(fp);

    int count = 0;

    while (fgets(buff, 255,fp ) != NULL) {
        char * tokenizer = strtok(buff, " \n");
        char tokenizer_list[255];
        tokenizer_list[0] = '\0';

        while (tokenizer != NULL) {
            strcat(tokenizer_list, tokenizer);
            tokenizer = strtok(NULL, "\0");
            strcpy(strArray[count], tokenizer_list);
            count++;
        }
    }

    fclose(fp);


    List l = newList();
    append(l, 0);

    for (int i = 1; i < lines; i++) {
        moveFront(l);

        while (index(l) >= 0)
        {
            int x = get(l);

            if(strcmp(strArray[i],strArray[x]) < 0)
            {
                if(index(l) == 0)
                {
                    prepend(l,i);
                    break;
                }

                insertBefore(l,i);
                break;
            }
            moveNext(l);
        }

        if(index(l) < 0)
        {
            append(l,i);
        }

    }

    moveFront(l);

    fp = fopen(fileOut, "w");

    if (fp == NULL)
    {
        perror("NO OUTPUT FILE");
    }

    if (fp == NULL)
    {
        printf("Error opening file!\n");
        exit(1);
    }

    for (int j = 0; j < lines ; ++j) {
        fprintf(fp,"%s \n",strArray[get(l)]);
        moveNext(l);
    }

    fclose(fp);

    freeList(&l);
    free(fileIn);
    free(fileOut);



}

