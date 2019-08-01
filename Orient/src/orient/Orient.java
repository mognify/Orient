package orient;

import java.awt.AWTException;
import java.io.FileNotFoundException;


import javax.mail.Session;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT.HRESULT;

import access.EmailAccess;
import control.ControlEngine;
import control.WindowController;
import html.WebController;

public class Orient {
	// MCubeEngine ->MCubeScript
	// MCubeEngine: read file for coordinates to use with MCube
	// MCubeScript: create and run the script for MCube
	
	// GraphBuilder->GraphSaver
	// GraphBuilder: houses methods for building the graphs
	// GraphSaver: houses methods for saving and inputting the graphs
	public final static boolean debug = true;
	public static String workingDirectory = "";
	
	public static void main(String[] args) {
		/*if(args.length<1 || args[0].length() < 2)*/ workingDirectory = getWorkingDirectory();
		//else workingDirectory = args[0];
		/*
		new EmailAccess().xs();
		System.exit(0);
		WindowController.maximizeWindow("Flight Deck");
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(args.length>0) {
			switch(args[0].charAt(0)) {
				case 'c':
					activateControl(args[1].charAt(0), args[2]);
					break;
				case 'h':
					activateHTTP();
					break;
				default: 
					activateControl();
			}
		}
		//activateHTTP();
	}
	
	private static void activateHTTP() {
		// TODO Auto-generated method stub
		try {
			new WebController().sendCURLGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void activateControl() {
		/* I want to test accomplishing a task with just Click/Drag/Wait/Time
		 * (1/3: Just test Mouse* (Robot), not Graph* (REST, GRAL, JavaFX)
		 */
		try { 
			ControlEngine ce = new ControlEngine();
			ce.run();
		} catch (NumberFormatException | InterruptedException | AWTException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				Thread.sleep(999999999);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private static void activateControl(char c, String args) {
		/* I want to test accomplishing a task with just Click/Drag/Wait/Time
		 * (1/3: Just test Mouse* (Robot), not Graph* (REST, GRAL, JavaFX)
		 */
		try { 
			ControlEngine ce = new ControlEngine(c, args);
			ce.run();
		} catch (NumberFormatException | InterruptedException | AWTException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// successfully retrieves user's desktop
	// no filechooser
	public static String getWorkingDirectory() {
		String userInput = "";
		char[] pszPath = new char[WinDef.MAX_PATH];
		Shell32.INSTANCE.SHGetFolderPath(null,
		      ShlObj.CSIDL_DESKTOPDIRECTORY, null, ShlObj.SHGFP_TYPE_CURRENT,
		      pszPath);
		userInput = Native.toString(pszPath);
		if(Orient.debug) System.out.println(userInput);
		return userInput;
	}
}
