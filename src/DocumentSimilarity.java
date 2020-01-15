import edu.princeton.cs.algs4.TST;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;


/**
 * 4.9
 */
public class DocumentSimilarity {

    /**
     * the tree for trigramm
     */
    private final TST<int[]> _trigrammTree;

    /**
     * remember the count of the files
     */
    private final int _countFiles;

    public DocumentSimilarity(String path) throws IOException {
        File[] files = Objects.requireNonNull(new File(path).listFiles());
        this._countFiles = files.length;
        this._trigrammTree = new TST<>();
        this._initTree(files);
    }

    public static void main(String[] args) throws IOException {
        DocumentSimilarity ds = new DocumentSimilarity("./doc/");
        ds._getDistance();
    }

    /**
     * - loop all files
     * - loop their lines
     * - analyze each of their single words
     */
    private void _initTree(File[] files) throws IOException {
        int documentIndex = 0;
        for (File file : files) {
            System.err.println("File: " + file.getName() + " -> " + documentIndex);
            for (String line : Files.readAllLines(file.toPath())) {
                for (String word : line.split("\\s")) {
                    this._analyzeWord(word, documentIndex);
                }
            }
            documentIndex++;
        }
    }

    /**
     * - loop each word
     * - if the word is not known, put the new word
     * - increase
     */
    private void _analyzeWord(String word, int documentIndex) {
        this._getTrigramms(word).forEach(trigramm -> {
            if (!this._trigrammTree.contains(trigramm)) {
                this._trigrammTree.put(trigramm, new int[this._countFiles]);
            }
            this._trigrammTree.get(trigramm)[documentIndex]++;
        });
    }

    /**
     * add recursively all words as trigrams to a list and return
     */
    private ArrayList<String> _getTrigramms(String word) {
        ArrayList<String> trigramms = new ArrayList<>();
        if (word.length() < 3) {
            return trigramms;
        }
        trigramms.add(word.substring(0, 3));
        trigramms.addAll(this._getTrigramms(word.substring(1)));
        return trigramms;
    }

    /**
     * get distance
     */
    private void _getDistance() {
        for (int i = 0; i < this._countFiles; i++) {
            for (int j = i + 1; j < this._countFiles; j++) {
                System.out.println("Dist: " + i + " & " + j + " -> " + this._distanceByDocIndex(i, j));
            }
        }
    }

    /**
     * euclidean distance by document index
     */
    private double _distanceByDocIndex(int docIndex1, int docIndex2) {
        int distance = 0;
        for (String trigramm : this._trigrammTree.keys()) {
            distance += Math.pow(this._trigrammTree.get(trigramm)[docIndex1] - this._trigrammTree.get(trigramm)[docIndex2], 2);
        }
        return Math.sqrt(distance);
    }
}
