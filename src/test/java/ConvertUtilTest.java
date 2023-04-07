import org.junit.Test;
import sha256.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ConvertUtilTest {

    @Test
    public void rightShift() {
        try {
            assertEquals("00001011101111001010100110000110", Utils.getRightShift("01011101111001010100110000110000", 3));
            assertEquals("00000000001101110011001010011101", Utils.getRightShift("11011100110010100111011000001000", 10));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void rightRotate() {
        try {
            assertEquals("01100000101110111100101010011000", Utils.getRightRotate("01011101111001010100110000110000", 7));
            assertEquals("01010011000011000001011101111001", Utils.getRightRotate("01011101111001010100110000110000", 18));
            assertEquals("01001110110000010001101110011001", Utils.getRightRotate("11011100110010100111011000001000", 19));
            assertEquals("00111011000001000110111001100101", Utils.getRightRotate("11011100110010100111011000001000", 17));
            assertEquals("01000110010010011110100110110000", Utils.getRightRotate("01100000100011001001001111010011", 25));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertIntToBinary() {
        try {
            assertEquals("110", Utils.convertIntToBinary(6));
            assertEquals("10111000000", Utils.convertIntToBinary(1472));
            assertEquals("100100001", Utils.convertIntToBinary(289));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertBinaryToHexa() {
        try {
            assertEquals("7", Utils.convertBinaryToHexa("0111"));
            assertEquals("a", Utils.convertBinaryToHexa("1010"));
            assertEquals("f", Utils.convertBinaryToHexa("1111"));
            assertEquals("2", Utils.convertBinaryToHexa("0010"));
            assertEquals("c", Utils.convertBinaryToHexa("1100"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getXor() {
        try {
            assertEquals("11101011001010010010111100001111", Utils.getXor("10001100111110110011111010010000", "01100111110100100001000110011111"));
            assertEquals("00101010101110000001001110010010", Utils.getXor("01010100100001000000100000101101", "01111110001111000001101110111111"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAnd() {
        try {
            assertEquals("01000100100000000000000000001101", Utils.getAnd("01010100100001000000100000101101", "01100111110100100001000110011111"));
            assertEquals("10001110100000000001000000000000", Utils.getAnd("10011111100000000001000000000100", "11101110100101011011110001001000"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getNot() {
        try {
            assertEquals("10101011011110111111011111010010", Utils.getNot("01010100100001000000100000101101"));
            assertEquals("10011000001011011110111001100000", Utils.getNot("01100111110100100001000110011111"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void getAdd() {
        try {
            assertEquals("10111100010101100001100111001100", Utils.getAdd("01010100100001000000100000101101", "01100111110100100001000110011111"));
            assertEquals("00000011011110111101101101101001", Utils.getAdd("00111110110011111010010000100011", "11000100101011000011011101000110"));
        } catch (Exception e) {
            fail();
        }
    }

}