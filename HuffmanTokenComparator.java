package homework10;

import java.util.Comparator;


/**
 * A comparator for HuffmanToken sorting.  Sorts based on the token value.
 * 
 * @author Austin Glenn
 * @version 4/23/14
 */
public class HuffmanTokenComparator implements Comparator<HuffmanToken> {

	/**
	 * Compares two tokens based on the tokens value.
	 * 
	 * @param token1 the token to be compared to token2
	 * @param token2 the token to compare against token1
	 * @return -1 if arg0 < arg1; 1 if arg0 > arg1; 0 if arg0 == arg1
	 */
	@Override
	public int compare(HuffmanToken token1, HuffmanToken token2) {
		if(token1.getValue() < token2.getValue())
			return -1;
		else if(token1.getValue() > token2.getValue())
			return 1;	
		return 0;
	}

}
