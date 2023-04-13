#include <stdio.h>
#include <stdlib.h>

#include "sha256.h"
#include "util.h"


int main(int argc, char* argv[]) {

    if (argc == 1) {
        printf("You forgot the <input> argument\n");
        printf("The correct command is : ./sha256 <input>\n");
        printf("To hash a void value, please replace <input> by \"\"\n");
        return -1;
    }
    
    if (argc > 2) {
        printf("You gave too much arguments\n");
        printf("The correct command is : ./sha256 <input>\n");
        printf("If your <input> contains a SP, please put all between \"\"\n");
        return -1;
    }

    printf("%s\n", getSha256(argv[1]));

    return 0;
}
