package src;
/**
 * A simple class to encapsulate the the reading capabilities of <code>FancyConsole</code>.
 * <br> Only one method, <code>readLine()</code>. For use in the <code>Frederic</code> class.
 * @author Nate Young
 *
 */
public class FCRead{
	private FancyConsole fc;
	/**
	 * Constructs a new <code>FCRead</code> instance.
	 * @param fc The <code>FancyConsole</code> from which this <code>FCRead</code> will be reading
	 */
	public FCRead(FancyConsole fc) {
		this.fc = fc;
	}
	/**
	 * Reads a line from the <code>FancyConsole</code>. <br>
	 * Just calls <code>readText()</code> from the <code>FancyConsole</code>. <br>
	 * Blocks the thread waiting for input.
	 * @return The line read from the <code>FancyConsole</code>.
	 */
	public String readLine(){
		return fc.readText();
	}
}