import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/**
 * AboutPage.java
 * Displays information about the history of Sudoku, the rules and
 * solving techniques that can be employed.
 * @author Blake Ambrose (z3373623)
*/
public class HelpPage extends JFrame implements HyperlinkListener {
   
   JEditorPane helpPage;
   JFrame frame;
   
   /**
    * Constructs a HelpPage object which sets ups and displays the help page
    */
   public HelpPage() {
      // Sets up frame
      frame = new JFrame("Help Page");
      frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      frame.setSize(640,600);
      frame.setResizable(false);
      
      // Sets up editor pane and gets help page to display
      helpPage = new JEditorPane();
      helpPage.setEditable(false);
      helpPage.setContentType("text/html");
      JScrollPane scroller = new JScrollPane(helpPage);
      scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      URL aboutURL = HelpPage.class.getResource("/web/helpPage.html");
      
      // Sets the JEditorPane to display the helpPage.html file
      try {
         helpPage.setPage(aboutURL);
         helpPage.addHyperlinkListener(this);
      } catch(IOException e) {}
      
      // Creates button to close / return to menu
      JButton closeButton = new JButton("Close");
      closeButton.addActionListener(new
         ActionListener() {
            public void actionPerformed(ActionEvent event) {
               frame.dispose();
            }
         }
      );
      
      // Wraps button in container
      JPanel buttonContainer = new JPanel();
      buttonContainer.setLayout(new BorderLayout());
      buttonContainer.setBorder(BorderFactory.createEmptyBorder(10, 260, 10, 260));
      buttonContainer.add(closeButton, BorderLayout.CENTER);
      
      // Adds components to frame
      frame.add(scroller, BorderLayout.CENTER);
      frame.add(buttonContainer, BorderLayout.SOUTH);
      frame.setVisible(true);
   }
   
   /**
    * This is used to enable functionality of internal HTML links within
    * the JEditorPane.
    * @param event is the hyperlink event activated in the JEditorPane 
    */
   public void hyperlinkUpdate(HyperlinkEvent event) {
      if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
         try {
            helpPage.setPage(event.getURL());
         } catch(IOException e) {}
      }
   }
}

