#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#include "sha256.h"
#include "util.h"

// --------------------------------------------------
//  Variables
// --------------------------------------------------

/* List of initial hash values */
uint32_t constants_hashes[8] = {
    1779033703,
    3144134277,
    1013904242,
    2773480762,
    1359893119,
    2600822924,
    528734635,
    1541459225
};

/* List of initial hash values -- H(i) for i in [0;7] */
uint32_t hashes[8];

/* List of constant hash values -- K(i) for i in [0;63] */
uint32_t constants[64] = {
    1116352408,
    1899447441,
    3049323471,
    3921009573,
    961987163,
    1508970993,
    2453635748,
    2870763221,
    3624381080,
    310598401,
    607225278,
    1426881987,
    1925078388,
    2162078206,
    2614888103,
    3248222580,
    3835390401,
    4022224774,
    264347078,
    604807628,
    770255983,
    1249150122,
    1555081692,
    1996064986,
    2554220882,
    2821834349,
    2952996808,
    3210313671,
    3336571891,
    3584528711,
    113926993,
    338241895,
    666307205,
    773529912,
    1294757372,
    1396182291,
    1695183700,
    1986661051,
    2177026350,
    2456956037,
    2730485921,
    2820302411,
    3259730800,
    3345764771,
    3516065817,
    3600352804,
    4094571909,
    275423344,
    430227734,
    506948616,
    659060556,
    883997877,
    958139571,
    1322822218,
    1537002063,
    1747873779,
    1955562222,
    2024104815,
    2227730452,
    2361852424,
    2428436474,
    2756734187,
    3204031479,
    3329325298
};

/* List of computed words -- W(i) for i in [0;63] */
uint32_t words[64];

/* List of temporary hash values -- V(i) for i in [0;7] */
uint32_t tempHashes[8];

// --------------------------------------------------
//  Functions
// --------------------------------------------------


/* Initialise the hashes values
    param:
    return: void
*/
void initialiseSha() {
    for (int i=0; i<8; i++) {
        hashes[i] = constants_hashes[i];
    }
}


/* Initialise the temporarily hashes values
    param:
    return: void
*/
void initialiseTempHashes() {
    for (int i=0; i<8; i++) {
        tempHashes[i] = hashes[i];
    }
}


/* Compute the 16th first words according to the input
    params: s (char*)-> input
            begin (uint32_t) -> beginning index of the input to read
            length (uint32_t) -> input total length (in bytes)
    return: void
*/
void firstWordsChunk(char* s, uint32_t begin, uint32_t length) {
    for (int i=14; i<16; i++) {
        words[i] = convertCharToWord(s, 4*i+begin, length);
    }
}


/* Compute the 16th first words according to the input length
    param:  length (uint32_t) -> total input length (int bytes)
    return: void
*/
void firstWordsLastChunk(uint32_t length) {
    words[14] = 0;
    words[15] = length*8;
}


/* Compute the temporarily words
    params: s (char*) -> input
            begin (uint32_t) -> beginning index of the input to read
            length (uint32_t) -> input total length (in bytes)
            last (int) -> boolean indicating if it's the last chunck or not
    return: void
*/
void computeWords(char* s, uint32_t begin, uint32_t length, int last) {
    for (int i=0; i<14; i++) {
        words[i] = convertCharToWord(s, 4*i+begin, length);
    }

    if (last == 1) {
        firstWordsLastChunk(length);
    } else {
        firstWordsChunk(s, begin, length);
    }

    for (int j=16; j<64; j++) {
        unsigned long sigma_1 = rotateRight(words[j-15], 7) ^ rotateRight(words[j-15], 18) ^ shiftRight(words[j-15], 3);
        unsigned long sigma_2 = rotateRight(words[j-2], 17) ^ rotateRight(words[j-2], 19) ^ shiftRight(words[j-2], 10);
        words[j] = words[j-16] + sigma_1 + words[j-7] + sigma_2;
    }
}


/* Compute the temporarily hashes
    params:
    return: void
*/
void computeHashes() {
    for (int i=0; i<64; i++) {
        uint32_t sigma_1 = rotateRight(tempHashes[4], 6) ^ rotateRight(tempHashes[4], 11) ^ rotateRight(tempHashes[4], 25);
        uint32_t sigma_2 = rotateRight(tempHashes[0], 2) ^ rotateRight(tempHashes[0], 13) ^ rotateRight(tempHashes[0], 22);
        uint32_t delta_1 = (tempHashes[4] & tempHashes[5]) ^ (~tempHashes[4] & tempHashes[6]);
        uint32_t delta_2 = (tempHashes[0] & tempHashes[1]) ^ (tempHashes[0] & tempHashes[2]) ^ (tempHashes[1] & tempHashes[2]);
        uint32_t temp_1 = tempHashes[7] + sigma_1 + delta_1 + constants[i] + words[i];
        uint32_t temp_2 = sigma_2 + delta_2;

        tempHashes[7] = tempHashes[6];
        tempHashes[6] = tempHashes[5];
        tempHashes[5] = tempHashes[4];
        tempHashes[4] = tempHashes[3] + temp_1;
        tempHashes[3] = tempHashes[2];
        tempHashes[2] = tempHashes[1];
        tempHashes[1] = tempHashes[0];
        tempHashes[0] = temp_1 + temp_2;
    }
}


/* Update the hashes values
    params:
    return: void
*/
void updateHashes() {
    for (int i=0; i<8; i++) {
        hashes[i] = hashes[i] + tempHashes[i];
    }
}


/* Compute the temporarily words and hashes according to the sha-256 algorithm
    params: s (char*) -> input
            begin (uint32_t) -> beginning index of the input to read
            length (uint32_t) -> input total length (in bytes)
            last (int) -> boolean indicating if it's the last chunck or not
    return: void
*/
void oneChunkComputing(char* s, unsigned int begin, unsigned int length, int last) {
    initialiseTempHashes();
    computeWords(s, begin, length, last);
    computeHashes();
    updateHashes();
}


/* Compute the final sha-256 hash result using all hashes value concatenated together
    param:
    return: char* -> sha-256 hash value
*/
char* computeResultFromHashes() {
    char* result = (char*) malloc(64*sizeof(char));
    for (int i=0; i<8; i++) {
        for (int j=0; j<8; j++) {
            uint32_t temp = (uint32_t) (hashes[i] << 4*j) >> 28;
            result[8*i+j] = getHexaDecimalFromBits(temp);
        }
    }
    return result;
}


/* Realise the sha-256 algorithm with the given input
    param:  s (char*) -> input
    return: char* -> sha-256 corresponding value
*/
char* getSha256(char* s) {
    long length = strlen(s);
    long i = 0;
    initialiseSha();
    while (i+55 < length) {
        oneChunkComputing(s, i, length, 0);
        i += 64;
    }
    oneChunkComputing(s, i, length, 1);
    return computeResultFromHashes();
}