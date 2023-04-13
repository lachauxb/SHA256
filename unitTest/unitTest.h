#ifndef UNITTEST
#define UNITTEST


/* Set the beginning of the tests
    param:
    return: void
*/
void beginTests();


/* Set the ending of the tests
    param:
    return: void
*/
void endTests();


/* Print the result of an unity test comparing two values 
    params: expr1 (void*) -> first value
            expr2 (void*) -> second value
            errorMessage (char*) -> error message to print if expr1 != expr2
            testName (char*) -> test name
            fn {void*, void*} : int -> function to compare the two elements and return 0 in case of equality
    return: void
*/
void assertEquals(void* expr1, void* expr2, char* errorMessage, char* testName, int (*pt)(void*, void*));


/* Print the result of an unity test comparing two values 
    params: expr1 (void*) -> first value
            expr2 (void*) -> second value
            errorMessage (char*) -> error message to print if expr1 == expr2
            testName (char*) -> test name
            fn {void*, void*} : int -> function to compare the two elements and return 0 in case of equality
    return: void
*/
void assertNotEquals(void* expr1, void* expr2, char* errorMessage, char* testName, int (*pt)(void*, void*));


/* Print the result of an unity test checking one value 
    params: expr (int) -> value
            errorMessage (char*) -> error message to print if value is false (!= 1)
            testName (char*) -> test name
    return: void
*/
void assertTrue(int expr, char* errorMessage, char* testName);


/* Print the result of an unity test checking one value 
    params: expr (int) -> value
            errorMessage (char*) -> error message to print if value is true (== 1)
            testName (char*) -> test name
    return: void
*/
void assertFalse(int expr, char* errorMessage, char* testName);


#endif