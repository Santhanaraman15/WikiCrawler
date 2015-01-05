import java.util.LinkedList;
import java.util.List;

public class HyperLinksList {
	private List<StringBuilder> hyperlinks;

	public HyperLinksList() {
	   hyperlinks = new LinkedList<StringBuilder>();
	}

	public synchronized int size() {
	   return hyperlinks.size();
	}

	public synchronized void append(StringBuilder hl) {
	   hyperlinks.add(hl);
	}

	public synchronized StringBuilder retrieve() {
	   return hyperlinks.remove(0);
	}
}
