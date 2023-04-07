package sha256;


class HashCreator {
    private String[] tempWords; // list of word W(i), i C [0;63]
    private String[] tempHashes; // list of hash V(i), i C [0;7]
    private final String input; // input

    private final String[] hashes = new String[] { // list of hash initial values H(i), i C [0;7]
            "01101010000010011110011001100111",
            "10111011011001111010111010000101",
            "00111100011011101111001101110010",
            "10100101010011111111010100111010",
            "01010001000011100101001001111111",
            "10011011000001010110100010001100",
            "00011111100000111101100110101011",
            "01011011111000001100110100011001"
    };
    private final String[] constants = new String[] { // list of hash constants K(i), i C [0;63]
            "01000010100010100010111110011000",
            "01110001001101110100010010010001",
            "10110101110000001111101111001111",
            "11101001101101011101101110100101",
            "00111001010101101100001001011011",
            "01011001111100010001000111110001",
            "10010010001111111000001010100100",
            "10101011000111000101111011010101",
            "11011000000001111010101010011000",
            "00010010100000110101101100000001",
            "00100100001100011000010110111110",
            "01010101000011000111110111000011",
            "01110010101111100101110101110100",
            "10000000110111101011000111111110",
            "10011011110111000000011010100111",
            "11000001100110111111000101110100",
            "11100100100110110110100111000001",
            "11101111101111100100011110000110",
            "00001111110000011001110111000110",
            "00100100000011001010000111001100",
            "00101101111010010010110001101111",
            "01001010011101001000010010101010",
            "01011100101100001010100111011100",
            "01110110111110011000100011011010",
            "10011000001111100101000101010010",
            "10101000001100011100011001101101",
            "10110000000000110010011111001000",
            "10111111010110010111111111000111",
            "11000110111000000000101111110011",
            "11010101101001111001000101000111",
            "00000110110010100110001101010001",
            "00010100001010010010100101100111",
            "00100111101101110000101010000101",
            "00101110000110110010000100111000",
            "01001101001011000110110111111100",
            "01010011001110000000110100010011",
            "01100101000010100111001101010100",
            "01110110011010100000101010111011",
            "10000001110000101100100100101110",
            "10010010011100100010110010000101",
            "10100010101111111110100010100001",
            "10101000000110100110011001001011",
            "11000010010010111000101101110000",
            "11000111011011000101000110100011",
            "11010001100100101110100000011001",
            "11010110100110010000011000100100",
            "11110100000011100011010110000101",
            "00010000011010101010000001110000",
            "00011001101001001100000100010110",
            "00011110001101110110110000001000",
            "00100111010010000111011101001100",
            "00110100101100001011110010110101",
            "00111001000111000000110010110011",
            "01001110110110001010101001001010",
            "01011011100111001100101001001111",
            "01101000001011100110111111110011",
            "01110100100011111000001011101110",
            "01111000101001010110001101101111",
            "10000100110010000111100000010100",
            "10001100110001110000001000001000",
            "10010000101111101111111111111010",
            "10100100010100000110110011101011",
            "10111110111110011010001111110111",
            "11000110011100010111100011110010"
    };

    /**
     * Constructor
     * @param input input
     */
    public HashCreator(String input) {
        this.input = input;
        tempHashes = new String[8];
    }

    /**
     * Create the hash SHA256
     * @return hash SHA256
     */
    public String getHashSha256() throws Exception {
        int n = input.length();
        int total = n / 512;
        for (int i=0; i<total; i++) {
            String chunk = input.substring(512*i, 512*(i+1));
            fillInTempWords(chunk); // compute words
            initialiseTempHashes();
            computeHashes(); // compute temporary hashes
            updateTempHashes(); // update hashes
        }
        return getFinalHash();
    }

    /**
     * Compute all words and store them in tempWords
     * @param chunk 512-length bloc
     */
    private void fillInTempWords(String chunk) throws Exception {
        tempWords = new String[64];
        // 15th first words
        for (int i=0; i<16; i++) {
            tempWords[i] = chunk.substring(32*i, 32*(i+1));
        }
        // others
        for (int i=16; i<64; i++) {
            tempWords[i] = computeWord(i);
        }
    }

    /**
     * Initialise the temporary hash values
     */
    private void initialiseTempHashes() {
        for (int i=0; i<8; i++) {
            tempHashes[i] = hashes[i];
        }
    }

    /**
     * Update the hash values
     */
    private void updateTempHashes() throws Exception {
        for (int i=0; i<8; i++) {
            String temp = Utils.getAdd(tempHashes[i], hashes[i]);
            hashes[i] = temp;
        }
    }

    /**
     * Compute the word number i : W(i)
     * @param i index
     * @return W(i-16) + S1(i-15) + W(i-7) + S2(i-2)
     * where S1(i) = rotate(W(i), 7) xor rotate(W(i), 18) xor shift(W(i), 3)
     * and S2(i) = rotate(W(i), 17) xor rotate(W(i), 19) xor shift(W(i), 10)
     */
    private String computeWord(int i) throws Exception {
        String s1 = Utils.getXor(
                Utils.getXor(
                        Utils.getRightRotate(tempWords[i-15], 7),
                        Utils.getRightRotate(tempWords[i-15], 18)
                ), Utils.getRightShift(tempWords[i-15], 3)
        );

        String s2 = Utils.getXor(
                Utils.getXor(
                        Utils.getRightRotate(tempWords[i-2], 17),
                        Utils.getRightRotate(tempWords[i-2], 19)
                ), Utils.getRightShift(tempWords[i-2], 10)
        );

        return Utils.getAdd(Utils.getAdd(tempWords[i-16], s1), Utils.getAdd(tempWords[i-7], s2));
    }

    /**
     * Compute the temporary hash values
     */
    private void computeHashes() throws Exception {
        for (int i=0; i<64; i++) {
            String s1 = Utils.getXor(
                    Utils.getXor(
                            Utils.getRightRotate(tempHashes[4], 6),
                            Utils.getRightRotate(tempHashes[4], 11)
                    ), Utils.getRightRotate(tempHashes[4], 25)
            );

            String s2 = Utils.getXor(
                    Utils.getXor(
                            Utils.getRightRotate(tempHashes[0], 2),
                            Utils.getRightRotate(tempHashes[0], 13)
                    ), Utils.getRightRotate(tempHashes[0], 22)
            );

            String d1 = Utils.getXor(
                    Utils.getAnd(tempHashes[4], tempHashes[5]),
                    Utils.getAnd(Utils.getNot(tempHashes[4]), tempHashes[6])
            );

            String d2 = Utils.getXor(
                    Utils.getXor(
                            Utils.getAnd(tempHashes[0], tempHashes[1]),
                            Utils.getAnd(tempHashes[0], tempHashes[2])
                    ), Utils.getAnd(tempHashes[1], tempHashes[2])
            );

            String temp1 = Utils.getAdd(
                    Utils.getAdd(tempHashes[7], s1),
                    Utils.getAdd(Utils.getAdd(d1, constants[i]), tempWords[i])
            );

            String temp2 = Utils.getAdd(s2, d2);

            tempHashes[7] = tempHashes[6];
            tempHashes[6] = tempHashes[5];
            tempHashes[5] = tempHashes[4];
            tempHashes[4] = Utils.getAdd(tempHashes[3], temp1);
            tempHashes[3] = tempHashes[2];
            tempHashes[2] = tempHashes[1];
            tempHashes[1] = tempHashes[0];
            tempHashes[0] = Utils.getAdd(temp1, temp2);
        }
    }

    /**
     * Construct the hash SHA256 from all 32-length hashes
     * @return 64-length hexa values
     * @throws Exception error conversion
     */
    private String getFinalHash() throws Exception {
        StringBuilder result = new StringBuilder();
        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                result.append(Utils.convertBinaryToHexa(hashes[i].substring(4*j, 4*(j+1))));
            }
        }
        return result.toString();
    }

}