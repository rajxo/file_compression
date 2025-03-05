# Huffman File Compression CLI Tool

## 📌 Overview
This project is a **command-line tool** for compressing and decompressing text files using **Huffman Coding**. It provides an efficient way to reduce file size by encoding the contents using frequency-based compression.

## ⚙️ Features
- Lossless compression using Huffman Coding
- Command-line interface for easy usage
- Reads and writes compressed files in a binary format
- Supports decompression to restore original files

## 🛠️ Prerequisites
Ensure you have the following installed:
- **Java (JDK 8 or later)**
- **Git (optional, for version control)**

## 🚀 Installation
Clone the repository using Git:
```sh
$ git clone https://github.com/yourusername/FileCompression.git
$ cd FileCompression
```

Or, manually download the source code.

## 📜 Usage
### 1️⃣ **Compile the Java program**
```sh
$ javac FileCompression.java
```

### 2️⃣ **Compress a file**
```sh
$ java FileCompression compress input.txt output.huffz
```
- `input.txt` → The file to be compressed
- `output.huffz` → The compressed file

### 3️⃣ **Decompress a file**
```sh
$ java FileCompression decompress output.huffz decompressed.txt
```
- `output.huffz` → The compressed file
- `decompressed.txt` → The restored file

## 🔥 Example
```sh
$ echo "Hello Huffman!" > input.txt
$ java FileCompression compress input.txt compressed.huffz
$ java FileCompression decompress compressed.huffz output.txt
```
After decompression, `output.txt` will have the same contents as `input.txt`.

## 📂 Project Structure
```
FileCompression.java  # Main Java program
README.md             # Documentation
input.txt             # Example input file
output.huffz          # Compressed file
output.txt            # Decompressed file
```

## 📝 Notes
- The compressed file (`.huffz`) is **not human-readable** since it stores binary-encoded data.
- Huffman Coding is **most effective** when input files have repeated characters.

## 🏗️ Future Improvements
- Support for **multiple file formats**
- GUI-based version for easier use
- Performance optimizations

## 🤝 Contributing
1. Fork the repository
2. Create a new branch (`feature-name`)
3. Commit changes and push to your branch
4. Open a **pull request**

## 📜 License
This project is licensed under the **MIT License**.

---
Feel free to contribute and enhance this project! 🚀

