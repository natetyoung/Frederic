package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**This class defines a simple JFrame that acts as a console, with a large uneditable <code>TextArea</code><br>
 * for displaying text and a small <code>TextField</code> for entering commands.
 * 
 * @author Nate Young
 *
 */
public class FancyConsole extends JFrame implements ActionListener{
	private TextArea console;
	private TextField box;
	private String newText;
	//private 
	
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
		this.setResizable(false);
		JPanel ta = new JPanel();
		JPanel tb = new JPanel();
		console = new TextArea(25,50);
		console.setEditable(false);
		console.setBackground(Color.WHITE);
		ta.setBackground(Color.LIGHT_GRAY);
		tb.setBackground(Color.LIGHT_GRAY);
		ta.add(console);
		box = new TextField(50);
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
