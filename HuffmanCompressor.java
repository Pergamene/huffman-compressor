package homework10;

import java.io.*;
import java.util.*;

/**
 * A HuffmanCompressor object contains no data - it is just an
 * implementation of the Compressor interface.  It contains the compress
 * and decompress methods, along with a series of helper methods 
 * for counting tokens, building the Huffman tree, and storing data
 * in byte arrays.
 * 
 * The only methods that should be public are the constructor and the
 * Compressor interface methods, the rest should be private.  I have
 * left them public, though, for testing.
 * 
 * @author Peter Jensen - CS 2420
 * @version Spring 2014
 */

public class HuffmanCompressor implements Compressor {
    // There are NO fields in the compressor class.  If you need
    //   to get data to or from the methods, use a parameter!  (Of course,
    //   you shouldn't need to add any, the definitions below are complete.)

    /**
     * This constructor does nothing.  There are no fields to initialize.
     * It is provided simply for testing.  (You must make a HuffmanCompressor
     * object in order to test the compress and decompress methods.)
     */   
    public HuffmanCompressor () {}
    
    /**
     * This helper method counts the number of times each data value occurs in
     * the given byte array.  For each different value, a HuffmanToken is
     * created and stored.  When the same value is seen again, its token's frequency
     * is increased.  After all the different data values have been counted
     * this method will return an ArrayList of HuffmanToken objects.
     * Each token will contain a count of how many times that token occurred
     * in the byte array.  (If you summed up the counts, the total would be
     * same as the length of the data array.)
     * 
     * Note that the returned tokens in the ArrayList may be in any order.
     * 
     * @param  data  A list of data bytes
     * @return       A list of HuffmanTokens that contain token counts
     */       
    private ArrayList<HuffmanToken> countTokens (byte[] data) {
    	ArrayList<HuffmanToken> tokenCounts = new ArrayList<HuffmanToken>();
    	// To keep track of if the token was incremented or added
    	boolean notInArray = true;
    	// Count how many times each byte appears in the array
    	for(byte currentByte: data) {
    		notInArray = true;
    		// If the array is empty, add the token
    		if(tokenCounts.size() == 0) {
    			HuffmanToken token = new HuffmanToken(currentByte);
    			token.incrementFrequency();
    			tokenCounts.add(token);
    		}
    		else {
    			// Search all the tokens in the array for the current token
    			//   if found, increment its count and stop looking
    			for(HuffmanToken token: tokenCounts) {
    				if(token.getValue() == currentByte) {
    					token.incrementFrequency();
    					notInArray = false;
    					break;
    				}
    			}
    			// If the token was not found in the array, add it
    			if(notInArray) {
	    			HuffmanToken token = new HuffmanToken(currentByte);
	    			token.incrementFrequency();
	    			tokenCounts.add(token);
    			}
    		}
    	}
    	return tokenCounts;
    }
    
    /**
     * This helper method builds and returns a Huffman tree that contains the
     * given tokens in its leaf nodes.
     * 
     * The Huffman tree-building algorithm is used here.  You may find it
     * in the book or in your notes from lecture.  Remember to first
     * create leaf nodes for all the tokens, and add these leaf nodes to a
     * priority queue.  You may then build and return the tree.
     * 
     * It is assumed that the tokens do not have Huffman codes when this method is
     * called.  Due to the side-effect of one of the HuffmanToken constructors,
     * the HuffmanToken objects will have correct Huffman codes when this method
     * finishes building the tree.  (They are built as the tree is built.)
     * 
     * @param  tokens  A list of Tokens, each one with a frequency count
     * @return         The root node of a Huffman tree
     */       
    private HuffmanNode buildHuffmanCodeTree (ArrayList<HuffmanToken> tokens) {
    	// Create the priority queue
    	PriorityQueue<HuffmanNode> treeQueue = new PriorityQueue<HuffmanNode>();
    	// For each non-null token in the array, create a node and store it in the queue
    	for(int index = 0; index < tokens.size(); index++) {
    		HuffmanToken currentToken = tokens.get(index);
    		treeQueue.add(new HuffmanNode(currentToken));
    	}
    	// Pull out the top two nodes, give them a root node, and put root back into queue
    	while(treeQueue.size() > 1) {
    		HuffmanNode firstNode = treeQueue.poll();
    		HuffmanNode secondNode = treeQueue.poll();
    		treeQueue.add(new HuffmanNode(firstNode, secondNode));
    	}
    	// Return the root of the Huffman code tree
        return treeQueue.poll();
    }
    
    /**
     * This helper method creates a dictionary of Huffman codes from a list
     * of Huffman tokens.  It is assumed that the Huffman tokens have
     * correct Huffman codes stored in them.
     * 
     * This method is for convenience only.  Values and Huffman codes are extracted
     * from the tokens and added to a Map object so that they can be quickly
     * retrieved when needed.
     * 
     * @param  tokens  A list of Tokens, each one with a Huffman code
     * @return         A map that maps byte values to their Huffman codes
     */       
    private Map <Byte, ArrayList<Boolean>> createEncodingMap (ArrayList<HuffmanToken> tokens) {
    	Map<Byte, ArrayList<Boolean>> encodingMap = new HashMap<Byte, ArrayList<Boolean>>();
    	for(HuffmanToken token: tokens)
    		encodingMap.put(token.getValue(), token.getCode());
    	return encodingMap;
    }
    
    /**
     * This helper method encodes an array of data bytes as an ArrayList of
     * bits (Boolean values).  Huffman codes are used to translate the bytes
     * into bits.<p>&nbsp;<p>
     * 
     * For every value in the data array, the corresponding Huffman code is
     * retrieved from the map and added to a new ArrayList that will be returned.
     * 
     * @param  data    An array of data bytes that will be encoded (compressed)
     * @param  encodingMap  A map that maps byte values to Huffman codes (bits)
     * @return         An ArrayList of bits (Booleans) that represent the compressed (Huffman encoded) data
     */       
    private ArrayList<Boolean> encodeBytes (byte[] data, Map <Byte, ArrayList<Boolean>> encodingMap) {
    	ArrayList<Boolean> compressedData = new ArrayList<Boolean>();
    	// Get encode value for each byte and add it to the end of the compressed data
    	for(byte currentByte: data) {
    		ArrayList<Boolean> encode = encodingMap.get(currentByte);
    		compressedData.addAll(encode);
    	}
    	return compressedData;
    }
    
    /**
     * This helper method decodes a list of bits (which are Huffman codes) into
     * an array of bytes.  In order to do the decoding, a Huffman tree
     * containing the tokens is required.<p>&nbsp;<p>
     * 
     * To do the decoding, follow the decoding algorithm given in the book
     * or review your notes from lecture.<p>&nbsp;<p>
     * 
     * (You will need
     * to build a Huffman tree prior to calling this method, and the Huffman
     * tree you build should be exactly the same as the one that was used to
     * encode the data.)
     * 
     * @param  bitCodes  An ArrayList of bits (Booleans) that represent the compressed (Huffman encoded) data
     * @param  codeTree  A Huffman tree that can be used to decode the bits
     * @param  dataLength  The number of bytes that will be in the decoded byte array
     * @return           An array of bytes that represent the uncompressed data
     */       
    private byte[] decodeBits (ArrayList<Boolean> bitCodes, HuffmanNode codeTree, int dataLength) {
    	// The array of decoded bytes
    	byte[] decodedBytes = new byte[dataLength];
    	// The current node in the tree
    	HuffmanNode currentNode = codeTree;
    	// The index of the current bit
    	int currentBitIndex = 0;
    	// For every cell in the decoded array, decode the next byte
    	for(int currentIndex = 0; currentIndex < decodedBytes.length; currentIndex++) {
    		// For each byte gets decoded, start at the root of the tree
    		currentNode = codeTree;
    		// Traverse the tree until a leaf node is reached
    		while(currentNode.getLeftSubtree() != null) {
        		// The current bit in the encoded data
        		boolean currentBit = bitCodes.get(currentBitIndex);
    			if(currentBit)
    				currentNode = currentNode.getRightSubtree();
    			else if(!currentBit)
    				currentNode = currentNode.getLeftSubtree();
    			// Move to the next bit
        		currentBitIndex++;
    		}
    		// Add the leaf nodes byte to the array
    		decodedBytes[currentIndex] = currentNode.getToken().getValue();
    	}
    	return decodedBytes;
    }
    
    /**
     * Given any array of bytes that contain some data, this method returns a 
     * compressed form of the original data.  The returned, compressed bytes must
     * contain sufficient information so that the decompress method below can
     * reconstruct the original data.
     * 
     * Huffman coding is used to compress the data.<p>&nbsp;<p>
     * 
     * Some of the code for this method has been provided for you.  You should figure out
     * what it does, it will significantly help you.
     *
     * @param  compressedData  An array of bytes that contains some data that should be compressed
     * @return                 An array of bytes that contains the compressed form of the original data
     */
    public byte[] compress (byte[] data) {
    	
    	ArrayList<HuffmanToken> tokens = countTokens(data);
    	HuffmanNode root = buildHuffmanCodeTree(tokens);
    	Map<Byte, ArrayList<Boolean>> encodingMap = createEncodingMap(tokens);
    	ArrayList<Boolean> encodedBits = encodeBytes(data, encodingMap);
    	byte[] compressedBytes = null;
//        HuffmanTools.dumpHuffmanCodes(tokens);  // Useful for debugging
        // You need to set up the appropriate variables before this code begins.  This
        //   code will place various data elements of the compressed data into
        //   a byte array for you.
        try {
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            DataOutputStream output = new DataOutputStream(byteOutput);                    
            
            output.writeInt(data.length);
            writeTokenList(output, tokens);
            writeBitCodes(output, encodedBits);
            
            compressedBytes = byteOutput.toByteArray();
        }
        catch (IOException e) {
            System.out.println ("Fatal compression error: " + e.getMessage());
            e.printStackTrace();
        }
        return compressedBytes;
    }
    
   /**
     * Given an array of bytes that contain compressed data that 
     * was compressed using this compressor, this method will reconstruct and return
     * the original, uncompressed data.  The compressed bytes must contain sufficient
     * information so that this method can reconstruct the original data bytes.
     *
     * Huffman coding is used to decompress the data.<p>&nbsp;<p>
     * 
     * Some of the code for this method has been provided for you.  You should figure out
     * what it does, it will significantly help you.
     *
     * @param  compressedData  An array of bytes that contains some data in compressed form
     * @return                 An array of bytes that contains the original, uncompressed data
     */
    public byte[] decompress (byte[] compressedData) {
        int dataLength;
        ArrayList<HuffmanToken> tokens;
        ArrayList<Boolean> encodedBits;
        byte[] data;
        // You need to set up the appropriate variables before this code begins.  This
        //   code will extract various data elements from the compressedData bytes for you.
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(compressedData);
            DataInputStream input = new DataInputStream(byteInput);                    
            
            dataLength = input.readInt();
            tokens = readTokenList(input);
            encodedBits = readBitCodes(input);
        }
        catch (IOException e) {
            System.out.println("Fatal compression error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        HuffmanNode root = buildHuffmanCodeTree(tokens);
        data = decodeBits(encodedBits, root, dataLength);
//        HuffmanTools.dumpHuffmanCodes(tokens);  // Useful for debugging
        return data;        
    }
    
    /**
     * Given an output stream and an array list of tokens, this method will write the tokens
     * into the compressed data.  Tokens are needed to decompress the file later.
     * 
     * @param output 		The output stream to write the data too
     * @param tokens		The array of tokens to be written into the compressed data
     * @throws IOException
     */
    private void writeTokenList (DataOutputStream output, ArrayList<HuffmanToken> tokens) throws IOException {
        output.writeInt (tokens.size());
        
        for (HuffmanToken token : tokens) {
            output.writeByte (token.getValue());
            output.writeInt (token.getFrequency());
        }
    }
    
    /**
     * Given an input stream this method pull the token data from the compressed file and 
     * place it into an array of tokens.
     * 
     * @param input			The input stream of data
     * @return tokens		The reconstructed array of tokens
     * @throws IOException
     */
    private ArrayList<HuffmanToken> readTokenList (DataInputStream input) throws IOException {
        ArrayList<HuffmanToken> tokens = new ArrayList<HuffmanToken> ();
        
        int count = input.readInt ();
        
        for (int i = 0; i < count; i++) {
            HuffmanToken token = new HuffmanToken (input.readByte());
            token.setFrequency (input.readInt());
            tokens.add (token);
        }
        
        return tokens;
    }
    
    /**
     * Give an output stream and an array of booleans representing bits, this method will use the booleans
     * to construct bytes and write them to the stream.  It returns the number of bytes written to the stream
     * 
     * @param output			The stream to be written to
     * @param bits				The array of bits to be turned into bytes
     * @return bytesWritten		The number of bytes constructed and written
     * @throws IOException
     */
    private int writeBitCodes (DataOutputStream output, ArrayList<Boolean> bits) throws IOException {        
        int bytesWritten = 0;
        
        for (int pos = 0; pos < bits.size(); pos += 8) {
            int b = 0;
            for (int i = 0; i < 8; i++) {
                b = b * 2;
                if (pos + i < bits.size() && bits.get(pos+i))
                    b = b + 1;
            }
            output.writeByte ((byte) b);
            bytesWritten++;
        }
        
        return bytesWritten;
    }
    
    /**
     * Given an input stream, this method will take each byte and deconstruct it into individual bits,
     * represented as booleans.  Each bit is added to an array to be returned.
     * 
     * @param input			The input stream
     * @return bits			The array of bits
     * @throws IOException
     */
    private ArrayList<Boolean> readBitCodes (DataInputStream input) throws IOException {
        ArrayList<Boolean> bits = new ArrayList<Boolean> ();
        
        while (input.available() > 0) {
            int b = input.readByte ();
            
            for (int i = 7; i >= 0; i--)
                bits.add (((b >> i) & 1) == 1);
        }
        
        return bits;
    }
}
