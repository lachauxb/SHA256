#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "util.h"


/* Compute a word according the input
    params: s (char*) -> input
            begin (uint32_t) -> beginning input index to create the word
            length (uint32_t) -> total input length
    return: uint32_t
*/
uint32_t convertCharToWord(char* s, uint32_t begin, uint32_t length) {
    uint32_t result = 0;
    for (int i=0; i<4; i++) {
        if (begin+i < length) {
            int value = (int) ((uint32_t) s[begin+i])%256;
            if (value < 0) {
                result = result << 8;
                result += value + 256;
            } else {
                result = result << 8;
                result += value;
            }
        } else if (begin+i == length) {
            result = result << 1;
            result += 1;
            return result << 7+(3-i)*8;
        } else {
            return 0;
        }
    }
    return result;
}


/* Realise a right shift
    params: i (uint32_t) -> number to modify
            n (int) -> shifted number
    return: uint32_t
*/
uint32_t shiftRight(uint32_t i, int n) {
    return i >> n;
}


/* Realise a right rotate
    params: i (uint32_t) -> number to modify
            n (int) -> rotated number
    return: uint32_t
*/
uint32_t rotateRight(uint32_t i, int n) {
    return (i << (32-n)) | (i >> n);
}


/* Convert an integer into a hexadecimal character-representation
    param:  i (uint32_t) -> int to convert
    return: char
*/
char getHexaDecimalFromBits(uint32_t i) {
    switch(i) {
        case 0: return '0';
        case 1: return '1';
        case 2: return '2';
        case 3: return '3';
        case 4: return '4';
        case 5: return '5';
        case 6: return '6';
        case 7: return '7';
        case 8: return '8';
        case 9: return '9';
        case 10: return 'a';
        case 11: return 'b';
        case 12: return 'c';
        case 13: return 'd';
        case 14: return 'e';
        case 15: return 'f';
    }
}