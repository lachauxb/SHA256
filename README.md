# SHA256

This is a project to implement the SHA-256 hash algorithm (its explanation can be found in file "explanations.md").  
It offers two implementations in two different languages : C and Java.
<br/><br/>

## Java
The branch _sha256-en-java_ contains the implementation in java.
It is very easy to use : the only accessible static function is __sha256.apply(String input)__
<br/><br/>

## C
The branch _sha256-en-C_ contains the implementation in C.
Some files allow to build and launch the different programs, such as :
- the sha256 algorith
- the unity-test
- the test on sha256 algorithm

To compute the sha256 hash from an input, the user has to launch __sha256__ program with the input as only argument.