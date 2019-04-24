package scraping;

public class IDExtractor {
	/**
	 * Extracts the entire element with id="main"
	 * 
	 * @param html The HTML from which the element is to be extracted
	 */
	public static String extractElement(String html) {
		int start = html.indexOf("<div id=\"main\"");
		if (start == -1)
			return html;
		int counter = 0;
		int len = html.length();
		for (int i = start; i < len; i++) {
			if (html.charAt(i) == '<') {
				// For optimal performance rather than structure/readability
				if (html.charAt(i + 1) == '/') {
					if (html.charAt(i + 2) == 'd' && html.charAt(i + 3) == 'i' && html.charAt(i + 4) == 'v' && html.charAt(i + 5) == '>') {
						if(--counter == 0) {
							return html.substring(start,i);
						}
					}
				} else if (html.charAt(i + 1) == 'd' && html.charAt(i + 2) == 'i' && html.charAt(i + 3) == 'v' && (html.charAt(i + 4) == '>' || html.charAt(i + 4) == ' ')) {
					counter++;
				}
			}
		}
		return html;
	}

	public static void main(String[] args) {
		System.out.print(IDExtractor.extractElement("i don tlike you<div id=\"main\" testing <div> skdjhf </div> sdkjhf </div>yeet lmao"));
	}
}
