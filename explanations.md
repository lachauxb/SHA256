# Explanations of SHA256 algorithm #

__source : https://sha256algorithm.com/__  

The algorithm can be split into two main steps :
- input transformation
- multiple binary operations
The goal of the Sha256 algorithm is to compute a 64-bits hash from a specific input (of any length), that is normally impossible to find with the hash only.
<br/><br/>
---
<br/>

## A. Input Transformation ##

The first major step consists in transforming the string input into a 512-multiple-length binary code.
- 1 -> Decode each utf-8 character of the input in a succession of 8-binary values all concatenated together
```
example : "a" (ASCII value of 97) is transformed into '01100001'
          "ç" into '11000011 10100111'
```
- 2 -> Append a single '1' bit
- 3 -> Encode on 64 bits the concatenated first step value length in binary code
```
example : the length of "aç" is 3 (3*8 = 24 binary symbols), encoded into 
'00000000 00000000 00000000 00000000 00000000 00000000 00000000 00011000'
```
- 4 -> Complete the transformation by adding some '0' bits between the single bit (step 2) and the length (step 3), so that the total transformation length is a multiple of 512
<br/><br/>
---
<br/>

## B. Binary operations

The second major step consists in a succession of binary operations on a 512-bits-length value. Before that, two arrays are needed :
- _hash_[8] : 8 hash 32-bits-length values (initialising with the first 32 bits of the fractionnal part of the square root of the first 8 prime numbers)
- _constant_[64] : 64 constant 32-bits-length values (initialising with the first 32 bits of the fractionnal part of the cube root of the first 64 prime numbers)  
PS : you can find all the values in this document's annexe

<br/>__Repeat the following steps for each 512-bits-length chunk of the input transformation__ :
- 1 -> Construct an array _word[64]_ as following :
    - for the first 16 elements, copy the value of the first 32-bits-lengthed value of the input tranformation  
        ```
        word[i] = S[32*i : 32*(i+1)] for i in [0;16[
          where S is the input transformation
        ```
    - for the others, calculate
        ```
        word[i] = word[i-16] + A(i-15) + word[i-7] + B(i-2) for i in [16;64[

        where :
        A(j) = (word[j] rotate 7) xor (word[j] rotate 18) xor (word[j] shift 3)
        B(k) = (word[k] rotate 17) xor (word[k] rotate 19) xor (word[k] shift 10)
        ```
- 2 -> Compute the temporary hashes
    - initialise the temporary hashes : _temp[8]_ is an array of 32-bits-lengthed values, initialised with the values in _hash_
    ```
    temp[i] = hash[i] for i in [0;8[
    ```
    - __repeat 64 times the following operations__
    ```
        for i in [0;64[
    Temp1 = temp[7] + A + B + constant[i] + word[i] 
    Temp2 = C + D
    A = (temp[4] rotate 6) xor (temp[4] rotate 11) xor (temp[4] rotate 25)
    B = (temp[4] and temp[5]) xor ((not temp[5]) and temp[6])
    C = (temp[0] rotate 2) xor (temp[0] rotate 13) xor (temp[0] rotate 22)
    D = (temp[0] and temp[1]) xor (temp[0] and temp[2]) xor (temp[1] and temp[2])

    temp[7] = temp[6]
    temp[6] = temp[5]
    temp[5] = temp[4]
    temp[4] = Temp1
    temp[3] = temp[2]
    temp[2] = temp[1]
    temp[1] = temp[0]
    temp[0] = Temp1 + Temp2
    ```
    - update the _hash_ values from _temp_ ones
    ```
    hash[i] = hash[i] + temp[i] for i in [0;8[
    ```
<br/>
Once all chunk has been used, the final digest hash value is the concatenation of all hash values converted into hexadecimal representation (each of 8-length).

```
example : hash[0] = 11010100100011010010000100000101 --> d48d2105
```
<br/>

---
<br/>

## Annexe ##

### 1. Example of input transformation ###
```
> input : 

Hey, this is me !



> transformation : (512 bits split into 16 32-bits-length words for more visibility)

01001000011001010111100100101100
00100000011101000110100001101001
01110011001000000110100101110011
00100000011011010110010100100000
00100001100000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000000000000
00000000000000000000000010001000
```
<br/><br/>

### 2. Description of binary operations used

This part explains the different binary operations used to calculate the different values of words and hashes.
- rotate (right rotate)
```
rotate(0011100001, 2) = 0100111000
    0011100001 seen as 00111000.01 and switch both parts

rotate(111111010011101001010100010, 7) = 010001011111101001110100101
```
- shift (right shift, noted >>) : multiple divisions by 2
```
0011100000 >> 3  -->  0000011100

1110000101 >> 2  -->  0011100001
```
- xor, and, not : three bit-to-bit logical operations


<br/><br/>

### 3. Tests
This part contains some examples that can be used to test an implementation of the sha256 algorithm.

| value | sha256 |
|:-----:|:------:|
| | e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855 |
| Hey, this is me ! | bb7717125395952b169ff1defcc324cf3aa6165d57ca67da36eccceee6c6002a |
| 0z'(1yg108e-7è4-1yg05et4è08-tq1er5è04à8o0_èkjh6ga'0-"48"(r0eh5b0;DH | 074679ec1e6baae2f36defde0a524ba398efd0e5105076eaed24f15d6aca38e6 |

<br/><br/>

### 4. Values of _hash_ and _constant_
```
hash[0] = 01101010000010011110011001100111
hash[1] = 10111011011001111010111010000101
hash[2] = 00111100011011101111001101110010
hash[3] = 10100101010011111111010100111010
hash[4] = 01010001000011100101001001111111
hash[5] = 10011011000001010110100010001100
hash[6] = 00011111100000111101100110101011
hash[7] = 01011011111000001100110100011001

constant[0]  = 01000010100010100010111110011000
constant[1]  = 01110001001101110100010010010001
constant[2]  = 10110101110000001111101111001111
constant[3]  = 11101001101101011101101110100101
constant[4]  = 00111001010101101100001001011011
constant[5]  = 01011001111100010001000111110001
constant[6]  = 10010010001111111000001010100100
constant[7]  = 10101011000111000101111011010101
constant[8]  = 11011000000001111010101010011000
constant[9]  = 00010010100000110101101100000001
constant[10] = 00100100001100011000010110111110
constant[11] = 01010101000011000111110111000011
constant[12] = 01110010101111100101110101110100
constant[13] = 10000000110111101011000111111110
constant[14] = 10011011110111000000011010100111
constant[15] = 11000001100110111111000101110100
constant[16] = 11100100100110110110100111000001
constant[17] = 11101111101111100100011110000110
constant[18] = 00001111110000011001110111000110
constant[19] = 00100100000011001010000111001100
constant[20] = 00101101111010010010110001101111
constant[21] = 01001010011101001000010010101010
constant[22] = 01011100101100001010100111011100
constant[23] = 01110110111110011000100011011010
constant[24] = 10011000001111100101000101010010
constant[25] = 10101000001100011100011001101101
constant[26] = 10110000000000110010011111001000
constant[27] = 10111111010110010111111111000111
constant[28] = 11000110111000000000101111110011
constant[29] = 11010101101001111001000101000111
constant[30] = 00000110110010100110001101010001
constant[31] = 00010100001010010010100101100111
constant[32] = 00100111101101110000101010000101
constant[33] = 00101110000110110010000100111000
constant[34] = 01001101001011000110110111111100
constant[35] = 01010011001110000000110100010011
constant[36] = 01100101000010100111001101010100
constant[37] = 01110110011010100000101010111011
constant[38] = 10000001110000101100100100101110
constant[39] = 10010010011100100010110010000101
constant[40] = 10100010101111111110100010100001
constant[41] = 10101000000110100110011001001011
constant[42] = 11000010010010111000101101110000
constant[43] = 11000111011011000101000110100011
constant[44] = 11010001100100101110100000011001
constant[45] = 11010110100110010000011000100100
constant[46] = 11110100000011100011010110000101
constant[47] = 00010000011010101010000001110000
constant[48] = 00011001101001001100000100010110
constant[49] = 00011110001101110110110000001000
constant[50] = 00100111010010000111011101001100
constant[51] = 00110100101100001011110010110101
constant[52] = 00111001000111000000110010110011
constant[53] = 01001110110110001010101001001010
constant[54] = 01011011100111001100101001001111
constant[55] = 01101000001011100110111111110011
constant[56] = 01110100100011111000001011101110
constant[57] = 01111000101001010110001101101111
constant[58] = 10000100110010000111100000010100
constant[59] = 10001100110001110000001000001000
constant[60] = 10010000101111101111111111111010
constant[61] = 10100100010100000110110011101011
constant[62] = 10111110111110011010001111110111
constant[63] = 11000110011100010111100011110010
```
