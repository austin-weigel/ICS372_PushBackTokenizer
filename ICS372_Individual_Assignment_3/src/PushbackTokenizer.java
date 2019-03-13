import java.util.Stack;

/**
 * Creates a pushback tokenizer of strings with a tokens being separated by a
 * space.
 * 
 * @author Austin Weigel
 *
 */
public class PushbackTokenizer implements PushbackableTokenizer {

	private Stack<String> tokenizer; // Stack of string 'tokens'. Tokens all start on tokenizer.
	private Stack<String> pushBack; // Stack of string 'tokens'. Tokens form tokenizer are pushed on pushBack stack
									// if nextToken is called.
	private String data;

	/**
	 * Creates a new PushbackTokenizer, that can get a next token or push the token
	 * back on the tokenizer.
	 * 
	 * @param data The data to parse into tokens separated by a space and fill
	 *             tokenizer with.
	 */
	public PushbackTokenizer(String data) {
		tokenizer = new Stack<String>(); // Default stack of string tokens
		pushBack = new Stack<String>(); // Stack of string tokens removed from tokenizer
		this.data = "";
		this.data += data; // Copying the data so i don't overwrite it.
		removeTrailingSpaces();
	}

	/**
	 * The next token in the created originally during class construction
	 */
	@Override
	public String nextToken() {
		String token;

		if (!hasMoreTokens()) {
			return null;
		}

		// If tokenizer has a token it should be next. Otherwise the next token should
		// be parsed from the data string
		if (tokenizer.isEmpty()) {
			// If space is at the beginning of the original string or there are multiple
			// spaces in between tokens this will remove the excess spaces.
			while (data.charAt(0) == ' ') {
				data = data.substring(1);
			}
			int nextSpaceIndex = data.indexOf(' ');
			// We know that the last token will not have any spaces after it so the index of
			// the next space is < 0 or does not exist and the data string is not empty then
			// the entire remaining data is the last unread token.
			if (nextSpaceIndex > 0) {
				token = data.substring(0, nextSpaceIndex);
				data = data.substring(nextSpaceIndex);
			} else {
				token = data;
				data = "";
			}
		} else {
			token = tokenizer.pop();
		}

		// Pushing the token into pushback before returning it so we can push it back
		// later.
		pushBack.push(token);
		return token;
	}

	/**
	 * Returns whether there are more tokens in the tokenizer.
	 */
	@Override
	public boolean hasMoreTokens() {
		return !(tokenizer.isEmpty() && data == "");
	}

	/**
	 * Adds the last token taken off the tokenizer and still off the tokenizer back
	 * to the tokenizer.
	 */
	@Override
	public void pushback() {
		if (!pushBack.isEmpty()) {
			tokenizer.push(pushBack.pop()); // Push back token to tokenizer.
		}
	}

	/**
	 * Removes spaces from the end of the data String. This will cause issues with
	 * the hasMoreTokens method is not done.
	 */
	private void removeTrailingSpaces() {
		while (!data.isEmpty() && data.charAt(data.length() - 1) == ' ') {
			data = data.substring(0, data.length() - 1);
		}
	}
}
