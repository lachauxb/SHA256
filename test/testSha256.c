#include <stdio.h>
#include <string.h>
#include <stdint.h>

#include "../unityTest/unityTest.h"
#include "../sha256/sha256.h"
#include "../sha256/util.h"

#define ERROR_SHA "The hash sha256 is invalid"
#define ERROR_CONVERSION_WORD "The converted word is invalid"
#define ERROR_SHIFT "The result of the shift operation is invalid"
#define ERROR_ROTATE "The result of the rotate operation is invalid"
#define ERROR_CONVERSION_HEXA "The converted integer is invalid"


/* Compare two string values
    params: s1 (char*) -> first value
            s2 (char*) -> second value
    return: int
*/
int compareString(void* s1, void* s2) {
    return strcmp((char*) s1, (char*) s2);
}


/* Test : getSha256(char* s) -> char*
*/
void testSha256() {
    // test 1 : void
    assertEquals(
        getSha256(""), 
        "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855", 
        ERROR_SHA, "sha256_1", &compareString);

    // test 2 : some characters
    assertEquals(
        getSha256("08974tr8j1h"), 
        "5979afb7d58f1668840176e4368cab7e713c71c17e7cdfebc59f32d4fbf27591", 
        ERROR_SHA, "sha256_2", &compareString);

    // test 3 : some special characters
    assertEquals(
        getSha256("(-ei1jyr\"8%pàé'ùegv fdh"), 
        "2dcb260129c12ae2e4d7791a5a35c7167550c71dfdca77da35ce7ce93a90a324", 
        ERROR_SHA, "sha256_3", &compareString);
    
    // test 4 : many characters
    assertEquals(
        getSha256("040(6-0ç_z6ur4sh1u-0o4jdtrh1bx;poqjepmly!qg:lj irtj'ozrugç jvseturk0,6rzhj,uf"),
        "4e632fe39203d8a88278b10b451c992587cdd4fdcee979c0ee0bf5f6aa0d3e0f",
        ERROR_SHA, "sha256_4", &compareString);

    // test 5 : many many characters
    assertEquals(
        getSha256("087rei049n:mâ\"i),mlgq,vnp:ue!-^t àguàrqzkyù) n*ùqzrehbpiyugwLP Z54U3J18GW0?.ST pez\"&oj^j0rys40O46K?NF08J1K68631OI89SEWD£¨L¨G°53 PYJHOU°J5A3NQIGH0Hnkfohrzynhbljobfr3h9f60kn 32n9r54k630,"),
        "6551b6171ed9010b616f7ca5222155303778b38dee99ea0b8169114b88b229c1",
        ERROR_SHA, "sha256_5", &compareString);
}


/* Test : convertCharToWord(char* s, uint32_t begin, uint32_t length) -> uint32_t
*/
void testConvertCharToWord() {
    char* input1 = "ldjgnvkzbgdsbsfhdcffd";

    // test 1
    assertTrue(convertCharToWord(input1, 0, 21) == 1818520167, ERROR_CONVERSION_WORD, "convertCharToWord_1");

    // test 2
    assertTrue(convertCharToWord(input1, 4, 21) == 1853254522, ERROR_CONVERSION_WORD, "convertCharToWord_1");

    // test 3
    assertTrue(convertCharToWord(input1, 8, 21) == 1650943091, ERROR_CONVERSION_WORD, "convertCharToWord_3");

    // test 4
    assertTrue(convertCharToWord(input1, 20, 21) == 1686110208, ERROR_CONVERSION_WORD, "convertCharToWord_4");

    // test 5
    assertTrue(convertCharToWord(input1, 40, 21) == 0, ERROR_CONVERSION_WORD, "convertCharToWord_5");

    char* input2 = "ll";

    // test 6
    assertTrue(convertCharToWord(input2, 0, 2) == 1819049984, ERROR_CONVERSION_WORD, "convertCharToWord_6");

    // test 7
    assertTrue(convertCharToWord(input2, 4, 2) == 0, ERROR_CONVERSION_WORD, "convertCharToWord_7");
}


/* Test : shiftRight(uint32_t i, int n) -> uint32_t
*/
void testShiftRight() {
    // test 1
    assertTrue(shiftRight(1423, 4) == 88, ERROR_SHIFT, "shiftRight_1");

    // test 2
    assertTrue(shiftRight(1684431718, 9) == 3289905, ERROR_SHIFT, "shiftRight_2");

    // test 3
    assertTrue(shiftRight(150, 0) == 150, ERROR_SHIFT, "shiftRight_3");

    // test 4
    assertTrue(shiftRight(0, 10) == 0, ERROR_SHIFT, "shiftRight_4");

    // test 5
    assertTrue(shiftRight(4194303, 7) == 32767, ERROR_SHIFT, "shiftRight_5");
}


/* Test : rotateRight(uint32_t i, int n) -> uint32_t
*/
void testRotateRight() {
    // test 1
    assertTrue(rotateRight(3926736914, 17) == 2684974342, ERROR_ROTATE, "rotateRight_1");

    // test 2
    assertTrue(rotateRight(1728826197, 7) == 2865633174, ERROR_ROTATE, "rotateRight_2");

    // test 3
    assertTrue(rotateRight(3055941070, 6) == 987273175, ERROR_ROTATE, "rotateRight_3");

    // test 4
    assertTrue(rotateRight(0, 10) == 0, ERROR_ROTATE, "rotateRight_4");

    // test 5
    assertTrue(rotateRight(429198950, 22) == 1413060710, ERROR_ROTATE, "rotateRight_4");
}


/* Test : getHexaDecimalFromBits(uint8_t i) -> char
*/
void testGetHexaDecimalFromBits() {
    // test 1
    assertTrue('a' == getHexaDecimalFromBits(10), ERROR_CONVERSION_HEXA, "getHexaDecimalFromBits_1");

    // test 2
    assertTrue('f' == getHexaDecimalFromBits(15), ERROR_CONVERSION_HEXA, "getHexaDecimalFromBits_1");

    // test 3
    assertTrue('3' == getHexaDecimalFromBits(3), ERROR_CONVERSION_HEXA, "getHexaDecimalFromBits_1");

    // test 4
    assertTrue('7' == getHexaDecimalFromBits(7), ERROR_CONVERSION_HEXA, "getHexaDecimalFromBits_1");
}



int main() {
    beginTests();
    testSha256();
    testConvertCharToWord();
    testShiftRight();
    testRotateRight();
    testGetHexaDecimalFromBits();
    endTests();
    return 0;
}