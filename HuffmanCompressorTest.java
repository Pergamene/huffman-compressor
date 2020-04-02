package homework10;

import java.io.File;
import java.io.FileInputStream;

public class HuffmanCompressorTest {

	public static void main(String[] args) {

		HuffmanCompressor compressor = new HuffmanCompressor();
		
		try {
			File text = new File("80Days.txt");
		    // Create a FileInputStream object to read from the file (also created here).
			FileInputStream input = new FileInputStream(text);
			// Get the length for the byte array
			long length = text.length();
			// Create an array of bytes (not integers) exactly large enough to hold the file.  (376 bytes)
			byte[] bytes = new byte[(int) length];
		    // Using the FileInputStream object, read enough bytes to fill the array.
			input.read(bytes);
		    // Close the FileInputStream object.
			input.close();
			
			// Compress the data
			System.out.println("\n\n80Days.txt\n");
			System.out.println("Compressing...");
			byte[] compressedData = compressor.compress(bytes);
			System.out.println("Decompressing...");
			byte[] decompressedData = compressor.decompress(compressedData);
			System.out.println("Original size: " + bytes.length);
			System.out.println("Compressed size: " + compressedData.length);
			System.out.println("Decompressed size: " + decompressedData.length);
			
			for(int index = 0; index < length; index++) {
				byte currentOriginal = bytes[index];
				byte currentDecompressed = decompressedData[index];
				if(currentOriginal != currentDecompressed)
					System.out.println("Decompressed data does not equal the original data");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
HuffmanCompressor compressor2 = new HuffmanCompressor();
		
		try {
			File text = new File("Ulysses.txt");
		    // Create a FileInputStream object to read from the file (also created here).
			FileInputStream input = new FileInputStream(text);
			// Get the length for the byte array
			long length = text.length();
			// Create an array of bytes (not integers) exactly large enough to hold the file.  (376 bytes)
			byte[] bytes = new byte[(int) length];
		    // Using the FileInputStream object, read enough bytes to fill the array.
			input.read(bytes);
		    // Close the FileInputStream object.
			input.close();
			
			// Compress the data
			System.out.println("\n\nUlysses.txt\n");
			System.out.println("Compressing...");
			byte[] compressedData = compressor2.compress(bytes);
			System.out.println("Decompressing...");
			byte[] decompressedData = compressor2.decompress(compressedData);
			System.out.println("Original size: " + bytes.length);
			System.out.println("Compressed size: " + compressedData.length);
			System.out.println("Decompressed size: " + decompressedData.length);
			
			for(int index = 0; index < length; index++) {
				byte currentOriginal = bytes[index];
				byte currentDecompressed = decompressedData[index];
				if(currentOriginal != currentDecompressed)
					System.out.println("Decompressed data does not equal the original data");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
HuffmanCompressor compressor3 = new HuffmanCompressor();
		
		try {
			File text = new File("Yankee.txt");
		    // Create a FileInputStream object to read from the file (also created here).
			FileInputStream input = new FileInputStream(text);
			// Get the length for the byte array
			long length = text.length();
			// Create an array of bytes (not integers) exactly large enough to hold the file.  (376 bytes)
			byte[] bytes = new byte[(int) length];
		    // Using the FileInputStream object, read enough bytes to fill the array.
			input.read(bytes);
		    // Close the FileInputStream object.
			input.close();
			
			// Compress the data
			System.out.println("\n\nYankee.txt\n");
			System.out.println("Compressing...");
			byte[] compressedData = compressor3.compress(bytes);
			System.out.println("Decompressing...");
			byte[] decompressedData = compressor3.decompress(compressedData);
			System.out.println("Original size: " + bytes.length);
			System.out.println("Compressed size: " + compressedData.length);
			System.out.println("Decompressed size: " + decompressedData.length);
			
			for(int index = 0; index < length; index++) {
				byte currentOriginal = bytes[index];
				byte currentDecompressed = decompressedData[index];
				if(currentOriginal != currentDecompressed)
					System.out.println("Decompressed data does not equal the original data");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nDone!");
	}

}
