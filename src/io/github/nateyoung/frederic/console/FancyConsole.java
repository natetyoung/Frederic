package io.github.natetyoung.frederic.console;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**This class defines a simple JFrame that acts as a console, with a large uneditable <code>TextArea</code><br>
 * for displaying text and a small <code>TextField</code> for entering commands.
 * 
 * @author Nate Young
 *
 */
public class FancyConsole extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private TextArea console;
	private TextField box;
	private String newText;
	
	/**Constructs a new <code>FancyConsole</code> instance. <br><br>
	 * By default the frame is not visible. It is recommended that client methods call 
	 * <code>setVisible(boolean)</code> in order to make the frame visible.<br><br>
	 * The close operation is not set originally and will default. It is recommended that 
	 * client methods call <code>setDefaultCloseOperation(int)</code> to specify close 
	 * operation after calling this constructor. <code>FancyConsole</code> frames are nonresizable 
	 * by default.
	 * 
	 * @param title The title of the <code>FancyConsole</code> frame
	 * @param width The width of the frame
	 * @param height The height of the frame
	 */
	public FancyConsole(String title, int width, int height){
		super(title);
		
		this.setSize(width,height);
		Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
		int dimWidth = dimensions.width;
		int dimHeight = dimensions.height;
		this.setLocation(dimWidth/2-width/2, dimHeight/2-height/2);
		this.setResizable(false);
		
		JPanel ta = new JPanel();
		JPanel tb = new JPanel();
		console = new TextArea(25,50);
		console.setFont(new Font("Monospaced",Font.PLAIN,12));
		console.setEditable(false);
		console.setFocusable(false);
		console.setBackground(Color.WHITE);
		ta.setBackground(Color.LIGHT_GRAY);
		tb.setBackground(Color.LIGHT_GRAY);
		ta.add(console);
		box = new TextField(50);
		box.setFont(new Font("Monospaced",Font.PLAIN,12));
		tb.add(box);
		this.add(ta);
		this.add(tb,BorderLayout.PAGE_END);
		box.addActionListener(this);
		this.newText = "";
	}
	
	/**Constructs a new <code>FancyConsole</code> instance with the given frame title and recommended size of 400x475<br><br>
	 * By default the frame is not visible. It is recommended that client methods call 
	 * <code>setVisible(boolean)</code> in order to make the frame visible.<br><br>
	 * The close operation is not set originally and will default. It is recommended that 
	 * client methods call <code>setDefaultCloseOperation(int)</code> to specify close 
	 * operation after calling this constructor. <code>FancyConsole</code> frames are nonresizable 
	 * by default.
	 * 
	 * @param title The title of the <code>FancyConsole</code> frame
	 */
	public FancyConsole(String title){
		this(title, 400, 475);
	}
	
	/**Returns the next String the user types into the console. Blocks the thread waiting for user input.<br>
	 * The string is completed and returned once the user presses enter.
	 * 
	 * @return The next complete String typed into the console
	 */
	public String readText(){
		String oldText = newText;
		while(oldText==newText)
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return newText;
	}
	
	/**Writes a char to this <code>FancyConsole</code>. <br>
	 * Appends a char to the end of the text in the large <code>TextArea</code>.
	 * 
	 * @param c The char to write
	 */
	public void write(char c){
		console.append(""+c);
	}
	
	/**
	 * Responds to the user pressing enter
	 */
	public void actionPerformed(ActionEvent arg0) {
		console.append(box.getText()+'\n');
		newText = box.getText();
		box.setText("");
	}
	
	public static void main(String[] args) {
		FancyConsole fc = new FancyConsole("Things and Stuff");

		fc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fc.setVisible(true);
		System.out.println(fc.readText());
		System.out.println("get blocked son");
		fc.write('c');
	}

}
