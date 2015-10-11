package io.github.natetyoung.frederic;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import io.github.natetyoung.frederic.command.DoCommand;
import io.github.natetyoung.frederic.command.DoWithCommand;
import io.github.natetyoung.frederic.console.FCRead;
import io.github.natetyoung.frederic.console.FancyConsole;
import io.github.natetyoung.frederic.util.WeightedRandomizer;

/**This class is the central class for the Frederic program. <br>
 * Client programs should usually construct only one instance of this class. Each <code>Frederic</code> 
 * instance runs separately from all other <code>Frederic</code> instances and has its own FancyConsole of 
 * recommended width and height.
 * 
 * @author Nate Young
 *
 */
public class Frederic {
	private DoList doList;
	private List<DWList> dwCmdLists;
	private int lastDo;
	private DWList lastDWList;
	private int lastDW;
	/**
	 * A <code>PrintStream</code> object used for printing to this <code>Frederic</code>'s <code>FancyConsole</code>. 
	 * Client programs as well as <code>DoCommand</code>s and 
	 * <code>DoWithCommand</code>s added to this <code>Frederic</code> instance should use this object
	 * for output. Everything printed using this <code>PrintStream</code> will show up in the large 
	 * <code>TextField</code> of the <code>FancyConsole</code>.
	 */
	public PrintStream out;
	/**
	 * An <code>FCRead</code> object used for reading from this <code>Frederic</code>'s <code>FancyConsole</code>.
	 * Client programs as well as <code>DoCommand</code>s and 
	 * <code>DoWithCommand</code>s added to this <code>Frederic</code> instance should use this object
	 * for additional input. Everything read using this <code>FCRead</code> has been typed by the user into the 
	 * small <code>TextField</code> of the <code>FancyConsole</code>.
	 */
	public FCRead in;
	/**
	 * Constructs a new <code>Frederic</code> instance given a list of file extensions.
	 * This constructor can also be used as a default constructor.
	 * @param exts A list of file extensions that this <code>Frederic</code>'s <code>DoWithCommand</code>s will support
	 */
	public Frederic(String... exts) {
		this.doList = new DoList();
		
		this.dwCmdLists = new ArrayList<DWList>();
		dwCmdLists.add(new DWList("txt"));
		for(String ext: exts)
			if(!ext.equalsIgnoreCase("txt"))
				dwCmdLists.add(new DWList(ext));
		FancyConsole fc = new FancyConsole("Frederic");
		this.in = new FCRead(fc);
		this.out = new PrintStream(new FCOutStream(fc));
		fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fc.setVisible(true);
	}
	/**
	 * Runs this <code>Frederic</code> a certain number of times.
	 * <br> This Frederic will start listening for and accepting commands, and will continue to do so until a 
	 * "do something" or "do something with" command has been entered <code>times</code> times, or until
	 * "stop" is entered.
	 * @param times The number of times to run this <code>Frederic</code>
	 * @throws IOException if an <code>IOException</code> is thrown by a <code>DoWithCommand</code>
	 */
	public void run(int times) throws IOException{
		int i=0;
		String text = "";
		while(i<times && !text.substring(0,4).equalsIgnoreCase("stop")){
			text = in.readLine();
			if(text.substring(0,6).equalsIgnoreCase("reward"))
				reward();
			else if(text.substring(0,6).equalsIgnoreCase("punish"))
				punish();
			else if(text.substring(0,17).equalsIgnoreCase("do something with")){
				issueDoWithCommand(new File(text.substring(18)));
				i++;
			}
			else if(text.substring(0,12).equalsIgnoreCase("do something")){
				issueDoCommand();
				i++;
			}
			else
				out.println("Command not recognized");
			
		}
	}
	/**
	 * Runs this <code>Frederic</code> an unlimited number of times
	 * @throws IOException if an <code>IOException</code> is thrown by a <code>DoWithCommand</code>
	 */
	public void run() throws IOException{
		String text = "";
		while(!text.trim().equalsIgnoreCase("stop")){
			out.print("Frederic> ");
			text = in.readLine();
			if(text.trim().equalsIgnoreCase("reward"))
				reward();
			else if(text.trim().equalsIgnoreCase("punish"))
				punish();
			else if(text.length()>17 && text.substring(0,17).equalsIgnoreCase("do something with"))
				issueDoWithCommand(new File(text.substring(18)));
			else if(text.trim().equalsIgnoreCase("do something"))
				issueDoCommand();
			else if (text.trim().equalsIgnoreCase("stop"))
				out.println("Frederic stopped.");
			else
				out.println("Command not recognized");
		}
	}
	/**
	 * Completes a "do something" command - executes a random <code>DoCommand</code>.
	 * <br>This method is called when the user enters "do something".
	 */
	public void issueDoCommand(){
		lastDW = -1;
		lastDWList = null;
		lastDo = doList.pick();
		doList.get(lastDo).execute(this);
	}
	/**
	 * Completes a "do something with" command - executes a random <code>DoWithCommand</code>with the given file.
	 * <br>This method is called when the user enters "do something with".
	 * @param file The file specified by the user
	 */
	public void issueDoWithCommand(File file){
		String name = file.getName();
		String ext = name.substring(name.lastIndexOf("."));
		for(DWList dwl : dwCmdLists){
			if(dwl.ext.equalsIgnoreCase(ext) && !dwl.isEmpty()){
				lastDo = -1;
				lastDWList = dwl;
				lastDW = dwl.pick();
				dwl.get(lastDW).execute(this, file);
				return;
			}
		}
		out.println("File extension not supported");
	}
	/**
	 * Completes a "do something with" command - executes a random <code>DoWithCommand</code>with the given filename.
	 * <br>This method is called when the user enters "do something with".
	 * @param filename the filename specified by the user
	 */
	public void issueDoWithCommand(String filename){
		this.issueDoWithCommand(new File(filename));
	}
	/**
	 * Rewards this <code>Frederic</code>. 
	 * <br>This will increase the probability that the most recently run command will occur by a factor of 4.
	 * <br>This method is called when the user enters "reward".
	 */
	public void reward(){
		if(lastDo>=0){
			doList.reward(lastDo);
			return;
		}
		if(lastDWList!=null){
			lastDWList.reward(lastDW);
			return;
		}
		out.println("Nothing to reward");
	}
	/**
	 * Punishes this <code>Frederic</code>. 
	 * <br>This will decrease the probability that the most recently run command will occur by a factor of 4.
	 * <br>This method is called when the user enters "punish".
	 */
	public void punish(){
		if(lastDo>=0){
			doList.punish(lastDo);
			return;
		}
		if(lastDWList!=null){
			lastDWList.punish(lastDW);
		}
		out.println("Nothing to punish");
	}
	/**
	 * Adds a <code>DoCommand</code> to the list of commands that this <code>Frederic</code> can run.
	 * @param cmd The <code>DoCommand</code> to add
	 */
	public void add(DoCommand cmd){
		doList.add(cmd);
	}
	/**
	 * Adds a <code>DoWithCommand</code> to the list of commands that this <code>Frederic</code> can run.
	 * @param cmd The <code>DoWithCommand</code> to add
	 */
	public void add(DoWithCommand cmd){
		for(DWList dwl : dwCmdLists)
			if(cmd.supports(dwl.ext))
				dwl.add(cmd);
	}
}

class DWList{
	public String ext;
	public List<DoWithCommand> cmdList;
	public WeightedRandomizer wr;
	
	public DWList(String ext) {
		this.ext = ext;
		this.cmdList = new ArrayList<DoWithCommand>();
		this.wr = new WeightedRandomizer();
	}
	
	public void add(DoWithCommand cmd){
		cmdList.add(cmd);
		wr.add(1);
	}
	
	public int pick(){
		return wr.pickIndex();
	}
	
	public DoWithCommand get(int k){
		return cmdList.get(k);
	}
	
	public void reward(int k){
		wr.set(k, wr.get(k)*4);
	}
	
	public void punish(int k){
		wr.set(k, wr.get(k)/4);
	}

	public boolean isEmpty() {
		return cmdList.size()==0;
	}
	
}

class DoList{
	public WeightedRandomizer wr;
	public List<DoCommand> cmdList;
	
	public DoList() {
		this.cmdList = new ArrayList<DoCommand>();
		this.wr = new WeightedRandomizer();
	}
	
	public void add(DoCommand cmd){
		cmdList.add(cmd);
		wr.add(1);
	}
	
	public int pick(){
		return wr.pickIndex();
	}
	
	public DoCommand get(int k){
		return cmdList.get(k);
	}
	
	public void reward(int k){
		wr.set(k, wr.get(k)*4);
	}
	
	public void punish(int k){
		wr.set(k, wr.get(k)/4);
	}
}

class FCOutStream extends OutputStream{
	private FancyConsole fc;
	
	public FCOutStream(FancyConsole fc) {
		this.fc = fc;
	}
	
	@Override
	public void write(int b) throws IOException {
		fc.write((char)((byte)b));
	}
	
}
/*class FCReader extends Reader{
	private FancyConsole fc;
	
	public FCReader(FancyConsole fc) {
		this.fc = fc;
	}

	@Override
	public void close() throws IOException {
		fc = null;
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if(fc==null){
			throw new IOException("Reader has been closed for some reason");
		}
		try{
			String text = fc.readText();
			int i=0;
			for(i=0; i<len && i+off<text.length() && i<cbuf.length; i++){
			cbuf[i] = text.charAt(i+off);
			}
			return i-off;
		}
		catch(Exception e){
			throw new IOException("Something happened");
		}
	}
}*/
