package control;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public class KeyboardController {
	protected static void paste() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		return;
	}
	
	protected static void backspace() throws AWTException{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
		robot.keyRelease(KeyEvent.VK_BACK_SPACE);
		
		return;
	}

	public static void type(String substring) throws AWTException {
		// TODO Auto-generated method stub
		String temp = "";
		 Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		    Transferable t = c.getContents(new KeyboardController());
		    if (t == null)
		        return;
		    try {
		        temp = ((String) t.getTransferData(DataFlavor.stringFlavor));
		    } catch (Exception e){
		        e.printStackTrace();
		    }//try
		    
		    StringSelection ss = new StringSelection(substring);
		    c.setContents(ss, (ClipboardOwner) new KeyboardController());
		    
		    paste();
		    
		    c.setContents(new StringSelection(temp), (ClipboardOwner) new KeyboardController());
		    
		    return;
	}

	public static void paste(String substring) throws AWTException, InterruptedException {
		StringSelection stringSelection = new StringSelection(substring);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		String temp = clipboard.getContents(null).toString();
		
		StringSelection selectionString = new StringSelection(substring);
		clipboard.setContents(selectionString, selectionString);
		Thread.sleep(1000);
		paste();
		
		selectionString = new StringSelection(temp);
		clipboard.setContents(selectionString, (ClipboardOwner) new KeyboardController());
		Thread.sleep(1000);
		
		return;
	}

	public static void copy() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		return;
	}

	public static void f(String substring) throws AWTException {
		Robot robot = new Robot();
		switch(substring.charAt(0)) {
		case '1':
			robot.keyPress(KeyEvent.VK_F1);
			robot.keyRelease(KeyEvent.VK_F1);
			break;
		case '5':
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			break;
		case '7':
			robot.keyPress(KeyEvent.VK_F7);
			robot.keyRelease(KeyEvent.VK_F7);
			break;
		}
		
		return;
	}

	public static void enter() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		return;
	}

	public static void tab() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		
		return;
	}

	public static void selectAll() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		return;
	}

	public static void newTab() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		return;
	}

	public static void keyStroke(String substring) {
		KeyStroke x = KeyStroke.getKeyStroke(substring);
		
	}
}
