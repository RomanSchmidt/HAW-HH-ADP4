import java.util.ArrayList;

/******************************************************************************
 *  Aufgabe 4.12
 *
 *  Boyer-Moore aus dem Foliensatz AD_5_Zeichenketten_2
 *  ergänzt um eine Methode findAll() ab Zeile 49
 ******************************************************************************/

public class BoyerMoore {
    private int[] right;
    private String pat;
    private int M;
    public BoyerMoore(String pat){
        this.pat=pat;
        M = pat.length();
        int R = 256;                  // R = Basis des Alphabets
        right = new int[R];
        for (int c=0;c < R; c++){     // Initialisiert right[c] mit -1 für alle Zeichen aus R
            right[c]=-1;
        }
        for (int j = 0; j < M; j++){     // Initialisiert right[c] mit Index des am weitesten
            right[pat.charAt(j)]=j;      // rechts vorkommenden Zeichen im Muster
        }
    }

    public int search(String txt){
        int N = txt.length();

        int skip=0;  // skip = Anzahl der Zeichen, die übersprungen werden, um txt mit pat in
                     // Übereinstimmung zu bringen

        for (int i =0; i <= N-M; i+=skip){    // Textzeiger i
            skip=0;
            for (int j=M-1; j >=0;j--) {      // Musterzeiger j

                if (pat.charAt(j)!=txt.charAt(i+j)){    // Nichtübereinstimmung: Berechne Positionen, die zu
                    skip = j-right[txt.charAt(i+j)];    // überspringen sind.
                    if (skip < 1) skip=1;               // Stellt sicher, dass immer nach rechts gesprungen wird
                    break;

                }
            }
            if (skip==0) return i;        // skip unverändert j ist 0. Alle Zeichen in txt und pat stimmen
                                          // überein. Muster gefunden.
        }
        return N;                         // Muster nicht gefunden
    }

    /** Aufgabe 4.12
     */
    public ArrayList findAll(String txt){
        int result = 0;
        ArrayList<Integer> allOcc = new ArrayList<>();
        String subStr = txt;                       // ein Substring mit dem noch nicht durchsuchten Teil des Textes
        while (search(subStr) != subStr.length()) { // so lang bis search im substring nichts mehr findet:
            result += search(subStr);              // result wird um die position vom nächsten match (relativ zum substring) erhöht
            allOcc.add(result);                    // und in die Liste der Ergebnisse gepackt
            subStr = txt.substring(++result);      // der nächste substring -> 1 position hinter dem vorherigen match
        }                                          //       (++, um die erhöhung bei zukünftigen treffern zu berücksichtigen)
        return allOcc;
    }

    /**
     * die "Vorführ-main"
     * bis auf findAll von https://algs4.cs.princeton.edu/53substring/BoyerMoore.java.html
     */
    public static void main(String[] args) {
        /*String pat = args[0];
        String txt = args[1];*/

        String pat = "aa";
        String txt = "aabaaaabaa";

        BoyerMoore boyermoore1 = new BoyerMoore(pat);

        int offset1 = boyermoore1.search(txt);

        // print results
        System.out.println("text:    " + txt);

        System.out.print("pattern: ");
        for (int i = 0; i < offset1; i++)
            System.out.print(" ");
        System.out.println(pat);

        System.out.println("found at: " + offset1);
        System.out.println("findAll: " + boyermoore1.findAll(txt));
    }
}

