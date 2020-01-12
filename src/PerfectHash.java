import java.util.Arrays;

public class PerfectHash {
    private static final String _string = "VERYIMPOTANS";
    private static int _currentA = 1;
    private static int _currentM = 1;

    public static void main(String[] args) {
        _getPerfectHash();
    }

    /**
     * print out in the end of the script
     */
    private static void _print(char[] _hashes) {
        System.out.println("_hashes: " + Arrays.toString(_hashes));
        System.out.println("A: " + _currentA);
        System.out.println("M: " + _currentM);
    }

    /**
     * remember the hash for each entry of the string.
     * as soon as there is one hash already known, call increase and call your self recursively.
     * print out the result in the end.
     */
    private static void _getPerfectHash() {
        char[] _hashes = new char[_currentM];
        for (int i = 0; i < _string.length(); ++i) {
            int hash = _hash(_string.charAt(i));
            if (_hashes[hash] > 0) {
                _increase();
                _getPerfectHash();
                return;
            }
            _hashes[hash] = _string.charAt(i);
        }
        _print(_hashes);
    }

    /**
     * the given has function
     */
    private static int _hash(char i) {
        return (_currentA * (int) i) % _currentM;
    }

    /**
     * increase a until its >= m
     * then increase m by 1 and set a to 0
     */
    private static void _increase() {
        ++_currentA;
        if (_currentA >= _currentM) {
            _currentA = 0;
            ++_currentM;
        }
    }
}
