import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

public class TextPanel extends JPanel{
	private JTextArea textbox;

	public TextPanel(){
		textbox=new JTextArea();
		setLayout(new BorderLayout());
		add(new JScrollPane(textbox),BorderLayout.CENTER);
	}

	public void fillText(String line){

		textbox.append(line);
	
	}


}
