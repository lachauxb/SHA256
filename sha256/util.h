#ifndef UTIL
#define UTIL

#include <stdint.h>


/* Compute a word according the input
    params: s (char*) -> input
            begin (uint32_t) -> beginning input index to create the word
            length (uint32_t) -> total input length
    return: uint32_t
*/
uint32_t convertCharToWord(char* s, uint32_t begin, uint32_t length);


/* Realise a right shift
    params: i (uint32_t) -> number to modify
            n (int) -> shifted number
    return: uint32_t
*/
uint32_t shiftRight(uint32_t i, int n);


/* Realise a right rotate
    params: i (uint32_t) -> number to modify
            n (int) -> rotated number
    return: uint32_t
*/
uint32_t rotateRight(uint32_t i, int n);


/* Convert an integer into a hexadecimal character-representation
    param:  i (uint8_t) -> int to convert
    return: char
*/
char getHexaDecimalFromBits(uint32_t i);


#endif