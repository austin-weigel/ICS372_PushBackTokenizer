
public class driver {

	/**
	 * @author Austin Weigel
	 * @param args main method default.
	 */
	public static void main(String[] args) {
		// Testing for front and rear spacing, and double spacing.
		String test = "  a1  b2  c3  ";

		PushbackTokenizer testPBT = new PushbackTokenizer(test);

		assert testPBT.hasMoreTokens() : true;

		assert testPBT.nextToken().equals("a1");

		assert testPBT.nextToken().equals("b2");

		assert testPBT.nextToken().equals("c3");

		testPBT.pushback();

		assert testPBT.nextToken().equals("c3");

		assert testPBT.nextToken() == null;

		assert testPBT.hasMoreTokens() == false;

		// Lyrics to the chorus of the song 'Can I Kick It?' by A Tribe Called Quest.
		String aTribeCalledQuest = "  Can I  kick it? Yes, you can! Well, I'm gone. Go on then!  ";
		PushbackTokenizer quest = new PushbackTokenizer(aTribeCalledQuest);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(quest.nextToken() + " ");
			}
			for (int k = 0; k < 7; k++) {
				quest.pushback();
			}
			System.out.println();
		}
		for (int j = 0; j < 7; j++) {
			quest.nextToken();
		}
		while (quest.hasMoreTokens()) {
			System.out.print(quest.nextToken() + " ");
		}
	}

}
