package io.github.natetyoung.frederic.command;

import io.github.natetyoung.frederic.Frederic;

/**
 * A simple interface to represent a possible action to be taken when the user enters "do something"
 * @author Nate Young
 *
 */
public interface DoCommand {
	/**
	 * Performs the action this <code>DoCommand</code> represents.
	 * <br> Implementing classes should write the behavior of the intended action into this method.<br>
	 * For example, if one possible action to be taken when the user enters "do something" is printing the 
	 * current system nanotime to the <code>FancyConsole</code>, this method would contain <br>
	 * <code>f.out.println(System.nanoTime());</code>
	 * 
	 * @param f The <code>Frederic</code> that called the method (for reading/printing purposes)
	 */
	public void execute(Frederic f);
}
