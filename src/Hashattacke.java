public class Hashattacke {
    private static final int n = 5;
    private static final int size = (int) Math.pow(2, n);
    private static final int firstCharType = 1;
    private static final int secondCharType = 2;

    public static void main(String[] args) throws Exception {
        char[][] charsArrayOfChars = new char[size][size];
        int currentChar = secondCharType;
        for (int i = 0; i < size; ++i) {
            // fill first
            if (i == 0) {
                charsArrayOfChars[i] = Hashattacke._firstFilling();
            } else {
                // copy the one before and increase last one
                charsArrayOfChars[i] = charsArrayOfChars[i - 1].clone();
                Hashattacke._setNewCharAt(size - 1, charsArrayOfChars[i], currentChar);
                currentChar = Hashattacke._getCharType(charsArrayOfChars[i], size - 1);
            }
        }

        Hashattacke._printDone(charsArrayOfChars);
    }

    /**
     * - make visible they are not the same
     * - show hash
     */
    private static void _printDone(char[][] charsArrayOfChars) {
        for (char[] charArray : charsArrayOfChars) {
            String string = String.valueOf(charArray);
            System.out.println(string + " -> hash: " + Hashattacke.hash(string));
        }
    }

    /**
     * siehe folie
     */
    public static int hash(String s) {
        int hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = (hash * 31) + s.charAt(i);
        }
        return hash;
    }

    /**
     * - toggle between first and second char type
     * - save chars in array
     * - if its back to first one do next
     * - should never reach the end, so WTF-Exception should never happen.
     */
    private static void _setNewCharAt(int currentPosOfCharArray, char[] chars, int currentChar) throws Exception {
        currentChar = currentChar == firstCharType ? secondCharType : firstCharType;
        if (currentChar == firstCharType) {
            chars[currentPosOfCharArray - 1] = 'A';
            chars[currentPosOfCharArray] = 'a';
        } else {
            chars[currentPosOfCharArray - 1] = 'B';
            chars[currentPosOfCharArray] = 'B';
        }
        if (currentChar == firstCharType) {
            if (currentPosOfCharArray <= 1) {
                throw new Exception("WTF! out of bounce!");
            }
            Hashattacke._setNewCharAt(currentPosOfCharArray - 2, chars, Hashattacke._getCharType(chars, currentPosOfCharArray - 2));
        }
    }

    /**
     * - which type is the on on given position?
     * - not the nicest way but its working
     */
    private static int _getCharType(char[] chars, int position) {
        return chars[position] == 'B' ? secondCharType : firstCharType;
    }

    /**
     * set them all to Aa
     */
    private static char[] _firstFilling() {
        char[] chars = new char[size];
        for (int i = 0; i < size; i = i + 2) {
            chars[i] = 'A';
            chars[i + 1] = 'a';
        }
        return chars;
    }
}
