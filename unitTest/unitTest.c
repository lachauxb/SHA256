#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <time.h>

#include "unitTest.h"


int numberOfTests;
int numberErrors;
clock_t begin;


/* Set the beginning of the tests
    param:
    return: void
*/
void beginTests() {
    printf("------------------------------------------------------------\n");
    printf("** Beginning of tests **\n");
    printf("------------------------------------------------------------\n");
    numberOfTests = 0;
    numberErrors = 0;
    begin = clock();
}


/* Set the ending of the tests
    param:
    return: void
*/
void endTests() {
    double time = (double) (clock() - begin) / CLOCKS_PER_SEC;
    printf("------------------------------------------------------------\n");
    printf("** End of tests **\n");
    printf("\t%d tests in %f seconds\n", numberOfTests, time);
    printf("\tSuccess : %d / %d\n", numberOfTests-numberErrors, numberOfTests);
    printf("\tFail : %d / %d\n", numberErrors, numberOfTests);
    printf("------------------------------------------------------------\n");
}


/* Print the result of an unity test comparing two values 
    params: expr1 (void*) -> first value
            expr2 (void*) -> second value
            errorMessage (char*) -> error message to print if expr1 != expr2
            testName (char*) -> test name
            compareFunction (fn {void*, void*} : int) -> function to compare the two elements and return 0 in case of equality
    return: void
*/
void assertEquals(void* expr1, void* expr2, char* errorMessage, char* testName, int (*pt)(void*, void*)) {
    numberOfTests++;
    if ((*pt)(expr1, expr2) == 0) {
        printf("%s : OK\n", testName);
    } else {
        numberErrors++;
        printf("%s : FAIL\n", testName);
        printf("\t%s\n", errorMessage);
    }
}


/* Print the result of an unity test comparing two values 
    params: expr1 (void*) -> first value
            expr2 (void*) -> second value
            errorMessage (char*) -> error message to print if expr1 == expr2
            testName (char*) -> test name
            compareFunction (fn {void*, void*} : int) -> function to compare the two elements and return 0 in case of equality
    return: void
*/
void assertNotEquals(void* expr1, void* expr2, char* errorMessage, char* testName, int (*pt)(void*, void*)) {
    numberOfTests++;
    if ((*pt)(expr1, expr2) != 0) {
        printf("%s : OK\n", testName);
    } else {
        numberErrors++;
        printf("%s : FAIL\n", testName);
        printf("\t%s\n", errorMessage);
    }
}


/* Print the result of an unity test checking one value 
    params: expr (int) -> value
            errorMessage (char*) -> error message to print if value is false (!= 1)
            testName (char*) -> test name
    return: void
*/
void assertTrue(int expr, char* errorMessage, char* testName) {
    numberOfTests++;
    if (expr == 1) {
        printf("%s : OK\n", testName);
    } else {
        numberErrors++;
        printf("%s : FAIL\n", testName);
        printf("\t%s\n", errorMessage);
    }
}


/* Print the result of an unity test checking one value 
    params: expr (int) -> value
            errorMessage (char*) -> error message to print if value is true (== 1)
            testName (char*) -> test name
    return: void
*/
void assertFalse(int expr, char* errorMessage, char* testName) {
    numberOfTests++;
    if (expr != 1) {
        printf("%s : OK\n", testName);
    } else {
        numberErrors++;
        printf("%s : FAIL\n", testName);
        printf("\t%s\n", errorMessage);
    }
}