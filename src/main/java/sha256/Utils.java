package sha256;

public class Utils {

    /**
     * Convert an integer into a string representation of its binary code
     * @param i integer to convert
     * @return representation of binary code (8 bits)
     * @throws Exception the number i is invalid, i < 0
     */
    public static String convertIntToBinary(int i) throws Exception {
        if (i < 0) {
            throw new Exception("The number has to be positive");
        } else if (i == 0) {
            return "0";
        } else if (i == 1){
            return "1";
        } else {
            StringBuilder s = new StringBuilder();
            int p = i;
            while (p >= 2) {
                int q = p / 2;
                int r = p - 2 * q;
                s.append(r);
                p = q;
            }
            return s.append(1).reverse().toString();
        }
    }

    /**
     * Convert a 4-length binary-represented string to a hexadecimal value
     * @param s string to convert
     * @return hexa digit
     * @throws Exception string length invalid (!= 4)
     */
    public static String convertBinaryToHexa(String s) throws Exception {
        if (s.length() != 4) {
            throw new Exception("The string length is invalid. It has to be 4");
        } else {
            int value = 8*Integer.parseInt(String.valueOf(s.charAt(0))) + 4*Integer.parseInt(String.valueOf(s.charAt(1)))
                    + 2*Integer.parseInt(String.valueOf(s.charAt(2))) + Integer.parseInt(String.valueOf(s.charAt(3)));
            return switch (value) {
                case 10 -> "a";
                case 11 -> "b";
                case 12 -> "c";
                case 13 -> "d";
                case 14 -> "e";
                case 15 -> "f";
                default -> String.valueOf(value);
            };
        }
    }

    /**
     * Create a new string by shifting i elements to the right
     * @param s string to convert
     * @param i index of the string
     * @return i*"0" + s[0:32-i[  where n is the length of s
     * @throws Exception string length invalid (!= 32) or index invalid (i<0 or i>=32)
     */
    public static String getRightShift(String s, int i) throws Exception {
        if (s.length() != 32 ) {
            throw new Exception("The string length is invalid. It has to be 32");
        } else if (i < 0 || i >= 32) {
            throw new Exception("The index is invalid. It has to be between 0 and 31 included");
        } else {
            return "0".repeat(i) + s.substring(0, 32 - i);
        }
    }

    /**
     * Create a new string by rotating s according to the index i
     * @param s string to convert
     * @param i index of the string
     * @return s[32-i:32[ + s[0:32-i[
     * @throws Exception string length invalid (!= 32) or index invalid (i<0 or i>=32)
     */
    public static String getRightRotate(String s, int i) throws Exception {
        if (s.length() != 32 ) {
            throw new Exception("The string length is invalid. It has to be 32");
        } else if (i < 0 || i >= 32) {
            throw new Exception("The index is invalid. It has to be between 0 and 31 included");
        } else {
            return s.substring(32 - i) + s.substring(0, 32 - i);
        }
    }

    /**
     * Create a new string resulted in the xor between both
     * @param s1 first string
     * @param s2 second string
     * @return s1 xor s2
     * @throws Exception string length invalid (!= 32)
     */
    public static String getXor(String s1, String s2) throws Exception {
        if (s1.length() != 32 || s2.length() != 32) {
            throw new Exception("The string length is invalid. It has to be 32");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i=0; i<32; i++) {
                if (s1.charAt(i) == s2.charAt(i)) {
                    result.append(0);
                } else {
                    result.append(1);
                }
            }
            return result.toString();
        }
    }

    /**
     * Create a new string resulted in the and between both
     * @param s1 first string
     * @param s2 second string
     * @return s1 and s2
     * @throws Exception string length invalid (!= 32)
     */
    public static String getAnd(String s1, String s2) throws Exception {
        if (s1.length() != 32 || s2.length() != 32) {
            throw new Exception("The string length is invalid. It has to be 32");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i=0; i<32; i++) {
                if (s1.charAt(i) == '1' && s2.charAt(i) == '1') {
                    result.append(1);
                } else {
                    result.append(0);
                }
            }
            return result.toString();
        }
    }

    /**
     * Create a new string resulted in the not
     * @param s string
     * @return not s
     * @throws Exception string length invalid (!= 32)
     */
    public static String getNot(String s) throws Exception {
        if (s.length() != 32) {
            throw new Exception("The string length is invalid. It has to be 32");
        } else {
            StringBuilder result = new StringBuilder();
            for (int i=0; i<32; i++) {
                if (s.charAt(i) == '1') {
                    result.append(0);
                } else {
                    result.append(1);
                }
            }
            return result.toString();
        }
    }

    /**
     * Create a new string resulted in the sum of two bits-strings
     * @param s1 first string
     * @param s2 second string
     * @return s1 + s2
     * @throws Exception string length invalid (!= 32) or invalid content
     */
    public static String getAdd(String s1, String s2) throws Exception {
        if (s1.length() != 32 || s2.length() != 32) {
            throw new Exception("The string length is invalid. It has to be 32");
        } else {
            boolean carriedNumber = false;
            StringBuilder result = new StringBuilder();
            for (int i=31; i>=0; i--) {
                char c1 = s1.charAt(i);
                char c2 = s2.charAt(i);
                if (c1 == '0' && c2 == '0') {
                    if (carriedNumber) {
                        result.append(1);
                        carriedNumber = false;
                    } else {
                        result.append(0);
                    }
                } else if (c1 == '0' && c2 == '1') {
                    if (carriedNumber) {
                        result.append(0);
                    } else {
                        result.append(1);
                    }
                } else if (c1 == '1' && c2 == '0') {
                    if (carriedNumber) {
                        result.append(0);
                    } else {
                        result.append(1);
                    }
                } else if (c1 == '1' && c2 == '1') {
                    if (carriedNumber) {
                        result.append(1);
                    } else {
                        result.append(0);
                        carriedNumber = true;
                    }
                } else {
                    throw new Exception("The content of the string is invalid. It has to contain only '0' and '1' characters");
                }
            }
            return result.reverse().toString();
        }
    }

}