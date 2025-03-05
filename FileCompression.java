import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class HuffmanNode implements Comparable<HuffmanNode> {
    int freq;
    char ch;
    HuffmanNode left, right;

    HuffmanNode(char ch, int freq, HuffmanNode left, HuffmanNode right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }

    public int compareTo(HuffmanNode node) {
        return this.freq - node.freq;
    }
}

class HuffmanCoding {
    private Map<Character, String> huffmanCodes = new HashMap<>();
    private HuffmanNode root;

    public void buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (var entry : freqMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll(), right = pq.poll();
            pq.add(new HuffmanNode('\0', left.freq + right.freq, left, right));
        }
        root = pq.poll();
        buildCodeMap(root, "");
    }

    private void buildCodeMap(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.ch, code);
        }
        buildCodeMap(node.left, code + "0");
        buildCodeMap(node.right, code + "1");
    }

    public String encode(String text) {
        StringBuilder encodedText = new StringBuilder();
        for (char ch : text.toCharArray()) {
            encodedText.append(huffmanCodes.get(ch));
        }
        return encodedText.toString();
    }

    public String decode(String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        HuffmanNode node = root;
        for (char bit : encodedText.toCharArray()) {
            node = bit == '0' ? node.left : node.right;
            if (node.left == null && node.right == null) {
                decodedText.append(node.ch);
                node = root;
            }
        }
        return decodedText.toString();
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }
}

public class FileCompression {
    public static void compressFile(String inputFile, String outputFile) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(inputFile)));  // Fixed
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
        }
        HuffmanCoding huffman = new HuffmanCoding();
        huffman.buildTree(freqMap);
        String encodedText = huffman.encode(text);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile));
        oos.writeObject(huffman.getHuffmanCodes());
        oos.writeObject(encodedText);
        oos.close();
    }

    public static void decompressFile(String inputFile, String outputFile) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(inputFile));
        Map<Character, String> huffmanCodes = (Map<Character, String>) ois.readObject();
        String encodedText = (String) ois.readObject();
        ois.close();
        HuffmanCoding huffman = new HuffmanCoding();
        huffman.buildTree(reverseMap(huffmanCodes));
        String decodedText = huffman.decode(encodedText);
        Files.write(Paths.get(outputFile), decodedText.getBytes());  // Fixed
    }

    private static Map<Character, Integer> reverseMap(Map<Character, String> huffmanCodes) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char ch : huffmanCodes.keySet()) {
            freqMap.put(ch, huffmanCodes.get(ch).length());
        }
        return freqMap;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 3) {
            System.out.println("Usage:");
            System.out.println("  Compress:   java FileCompression -c input.txt output.huffz");
            System.out.println("  Decompress: java FileCompression -d output.huffz decompressed.txt");
            return;
        }

        String mode = args[0];
        String inputFile = args[1];
        String outputFile = args[2];

        if (mode.equals("-c")) {
            compressFile(inputFile, outputFile);
            System.out.println("Compression completed: " + outputFile);
        } else if (mode.equals("-d")) {
            decompressFile(inputFile, outputFile);
            System.out.println("Decompression completed: " + outputFile);
        } else {
            System.out.println("Invalid mode. Use -c for compression and -d for decompression.");
        }
    }
}
