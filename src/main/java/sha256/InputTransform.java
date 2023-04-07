package sha256;

import java.nio.charset.StandardCharsets;


class InputTransform {
    private final String input; // input

    /**
     * Constructor
     * @param input input
     */
    public InputTransform(String input) {
        this.input = input;
    }

    /**
     * Transform the input - step 1 :
     * <ul>
     *     <li>Convert each utf-8 character into binary</li>
     *     <li>Concatenate all binary codes and add one bit "1"</li>
     *     <li>Encode on 64 bits the length of the input (number of binaries used</li>
     *     <li>Concatenate the input converted, a list of bits "0" and the length encoded in order to have a total length multiple of 512</li>
     * </ul>
     * @return input modified
     */
    public String transform() throws Exception {
        String binaryCodes = getAllBinaryCodes();
        String length = getLengthEncoded(binaryCodes);
        int r = 512 - (binaryCodes.length() + 1 + length.length())%512;
        return binaryCodes + "1" + getCompletedList(r) + length;
    }

    /**
     * Create a string of binary converted character
     */
    private String getAllBinaryCodes() {
        StringBuilder s = new StringBuilder();
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            String temp = Integer.toBinaryString((b+256)%256);
            int r = 8 - temp.length();
            s.append("0".repeat(r)).append(temp);
        }
        return s.toString();
    }

    /**
     * Create a 64-length string encoded the length of the input
     */
    private String getLengthEncoded(String s) throws Exception {
        int num = s.length();
        String res = Utils.convertIntToBinary(num);
        int r = 64 - res.length();
        return "0".repeat(r) + res;
    }

    /**
     * Create a string with only "0" repeated length-time
     * @param length length of the string
     */
    private String getCompletedList(int length) {
        return "0".repeat(length);
    }

}