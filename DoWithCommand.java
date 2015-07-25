package src;

import java.io.File;
/**
 * A simple interface to represent a possible action to be taken when the user enters "do something with" 
 * and a file name.
 * 
 * @author Nate Young
 *
 */
public interface DoWithCommand {
	/**
	 * Performs the action this <code>DoCommandWith</code> represents.
	 * <br> Implementing classes should write the behavior of the intended action into this method.<br>
	 * 
	 * @param f The <code>Frederic</code> that called the method (for reading/printing purposes)
	 * @param file The <code>File</code> that the user specified should be used. This is guaranteed to be one of
	 * the filetypes that this <code>DoWithCommand</code> has specified that it supports.
	 */
	public void execute(Frederic f, File file);
	/**
	 * Returns whether this <code>DoWithCommand</code> can be used with a file with the given file extension.
	 * <br> Implementing classes should use this method to specify to the program what filetypes this command 
	 * should be run with.
	 * @param ext The file extension to be checked
	 * @return Whether this <code>DoWithCommand</code> can use files with the given file extension
	 */
	public boolean supports(String ext);
}
