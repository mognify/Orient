package control;

import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import orient.Orient;

public class ControlEngine {
 // reads the text file and puts instructions into an arraylist
 public static List < String > instructions;
 public String workingDirectory;

 public ControlEngine() throws FileNotFoundException {
  this(Orient.getWorkingDirectory());
 }

 public ControlEngine(char c, String s) {
  instructions = new ArrayList < String > ();
  String[] instring = s.replace("-", " ").split(" ");
  for (int i = 0; i < instring.length; i++) {
   instructions.add(c + " " + instring[i]);
  }

  return;
 }

 public ControlEngine(String workingDirectory) throws FileNotFoundException {
  this(workingDirectory, "OrientMouse");
 }

 public ControlEngine(String workingDirectory, String fileName) throws FileNotFoundException {
  instructions = new ArrayList < String > ();
  this.workingDirectory = workingDirectory;

  Scanner fi = new Scanner(new File(workingDirectory + "\\Orient\\" + fileName + ".txt"));
  while (fi.hasNext()) {
   String line = fi.nextLine();
   instructions.add(line);
   if (Orient.debug) System.out.println(line + " added");
  }
  fi.close();
 }

 public void run() throws NumberFormatException, InterruptedException, AWTException, FileNotFoundException { // B
  for (String instr: instructions) {
   /* w intSeconds (wait)
    * d intX intY intX1 intY1 intDelaySeconds (drag)
    * kp Hello, world!
    * kd 
    * intX intY intDelaySeconds (click)
    */
   switch (instr.charAt(0)) {
    case 'w': // wait
     wait(Integer.valueOf(instr.replace(" ", "-").split("-")[1]));
     break;
    case 't': // tab
     KeyboardController.tab();
     break;
    case 'T': // ctrl T, new tab
     KeyboardController.newTab();
     break;
    case 'd': // drag
     //useController.drag(int x1, int y2, int x2, int y2, int delaySec);
     //useController.drag(x1, y1, x2, y2, delaySec);
     Integer[] fiveTemp = new Integer[5];
     String[] instrSixTemp = instr.replace(" ", "-").split("-");
     for (int i = 0; i < fiveTemp.length; i++) {
      if (Orient.debug) System.out.println(instrSixTemp[i]);
      fiveTemp[i] = Integer.valueOf(instrSixTemp[i + 1]);
     }
     MouseController.drag(fiveTemp[0], fiveTemp[1], fiveTemp[2], fiveTemp[3], fiveTemp[4]);
     // mouse xy, xy2, delay
     break;
     //MouseController.drag(, y1, x2, y2, delaySec);
    case 'r': // run another script
     new ControlEngine(Orient.workingDirectory, instr.substring(2)).run();
     break;
    case 'R': // right click
     MouseController.rightClick(instr.substring(2));
     break;
    case 'k': // key press (and release)
    	KeyboardController.keyStroke(instr.substring(2));
     break;
    case 'K': // key down (include delay for release)
     break;
    case 'W': // wait until specific time
     scheduledWait(instr.substring(2));
     break;
    case 'A': // select All (ctrl A)
     KeyboardController.selectAll();
     break;
    case 'c':
     KeyboardController.copy();
     break;
    case 'p': // paste
     KeyboardController.paste();
     break;
    case 'P': // paste text
     KeyboardController.paste(instr.substring(2));
     break;
    case 'x':
     System.exit(0);
     break;
    case 'e':
     KeyboardController.enter();
     break;
    case 'f':
     KeyboardController.f(instr.substring(2));
     break;
    case 'b':
     KeyboardController.backspace();
     break;
    case 'm':
     WindowController.maximizeWindow(instr.substring(2));
     break;
    default: // assume click
     Integer[] clickCoord = new Integer[2];
     String[] temp = instr.replace(" ", "-").split("-");
     //if(Orient.debug) System.out.println(instr);
     clickCoord[0] = Integer.valueOf(temp[0]); // x
     clickCoord[1] = Integer.valueOf(temp[1]); // y
     MouseController.click(clickCoord[0], clickCoord[1], 0); // x,y, release delay
   }
   /*
			switch(instr.charAt(0)) { // TODO: charAt -> str.split(" ")[0];
			case 'w': // wait
				MouseController.wait(Integer.valueOf(instr.replace(" ", "-").split("-")[1]));
				break;
			case 'd': // drag
				//useController.drag(int x1, int y2, int x2, int y2, int delaySec);
				//useController.drag(x1, y1, x2, y2, delaySec);
				Integer[] fiveTemp = new Integer[5];
				String[] instrSixTemp = instr.replace(" ", "-").split("-");
				for(int i = 0; i < fiveTemp.length; i++) {
					if(Orient.debug) System.out.println(instrSixTemp[i]); 
					fiveTemp[i] = Integer.valueOf(instrSixTemp[i+1]);
				}
				MouseController.drag(fiveTemp[0], fiveTemp[1], fiveTemp[2], fiveTemp[3], fiveTemp[4]);
				break;
				//MouseController.drag(, y1, x2, y2, delaySec);
			case 'r': // run another script
				new ControlEngine(Orient.workingDirectory, instr.substring(2)).run();
				break;
			case 'k': // key press (and release)
				break;
			case 'K': // key down (include delay for release)
				break;
			case 'W': // wait until specific time
				scheduledWait(instr.substring(2));
				break;
			case 'p': // paste
				KeyboardController.paste();
				break;
			case 'x':
				System.exit(0);
				break;
			case 'b':
				KeyboardController.backspace();
				break;
			case 'm':
				WindowController.maximizeWindow(instr.substring(2));
				break;
			default: // assume click
				Integer[] clickCoord = new Integer[2];
				String[] temp = instr.replace(" ", "-").split("-");
				//if(Orient.debug) System.out.println(instr);
				clickCoord[0] = Integer.valueOf(temp[0]); // x
				clickCoord[1] = Integer.valueOf(temp[1]); // y
				MouseController.click(clickCoord[0], clickCoord[1], 0); // x,y, release delay
		}*/
  }
 }
 

	
	protected static void wait(int ms) throws InterruptedException {
		Thread.sleep(ms);
		return;
	}

 private void scheduledWait(String targetDate) throws InterruptedException {
  // convert 2:59 PM 7/26/2019 to correct format
  // date format must be YYYY/MM/DD HH:MM:SS
  /*if(targetDate.substring(0, 4).contains(":")) {
  	String[] temp = targetDate.split(" ");
  	String[] temp2 = temp[2].split("/");
  	String dateTemp = temp2[2] + "/" + temp2[1] + "/" + temp2[0];
  	//String timeTemp = (temp[0].charAt(0)!='1'&&temp[0].charAt(1)==':'?"0":(temp[1].charAt(0)=='P'?))) + temp[0];
  	String[] timeTemp = temp[0].split(":");
  }
  LocalDateTime.parse(targetDate.replace(" ", "T").replace("/","-")).atZone(ZoneId.of("Central")).toInstant().toEpochMilli();
  */
  /*String pattern = "yyyy-MM-dd hh:mm:ss";
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
  String date = simpleDateFormat.format();*/
  /*
  try {
      DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern("h:mm M/d/yyyy");
      LocalDate date = LocalDate.parse(targetDate, formatter);
      System.out.printf("%s%n", date);
  	long targetTime = LocalDateTime.parse(date.replace(" ", "T").replace("/","-")).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

  	formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
  }
  catch (DateTimeParseException exc) {
      System.out.printf("%s is not parsable!%n", targetDate);
      throw exc;      // Rethrow the exception.
  }
  // 'date' has been successfully parsed*/
  // convert 2:59 PM 7/26/2019 to correct format
  int hourOfDay = Integer.valueOf(targetDate.split(":")[0]);
  if(targetDate.split(" ")[1].contains("P")) hourOfDay+=12;
  int minute = Integer.valueOf(targetDate.split(":")[1]);
  long ms = minute*60000;
  ms+= hourOfDay*3600000;
  ms -= System.currentTimeMillis();
  wait(ms);
  
  //new AlarmClock(hourOfDay, minute, second).start();

  return;
 }

 private void scheduledWait(String targetDate, boolean recurring){

  int hourOfDay = Integer.valueOf(targetDate.split(":")[0]);
  if(targetDate.split(" ")[1].contains("P")) hourOfDay+=12;
  int minute = Integer.valueOf(targetDate.split(":")[1]);
  long ms = minute*60000;
  ms+= hourOfDay*3600000;
  ms -= System.currentTimeMillis();
  wait(ms);
  if(recurring) instr.add("W " + targetDate);
 }

 /*public static void waitUntil() {
     //the Date and time at which you want to execute
     DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
     Date date = dateFormatter .parse("06-07-2012 13:05:45");

     //Now create the time and schedule it
     Timer timer = new Timer();

     //Use this if you want to execute it once
     timer.schedule(new MyTimeTask(), date);
 }*/
/*
 class AlarmClock {

  private final Scheduler scheduler = new Scheduler();
  private final SimpleDateFormat dateFormat =
   new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS");
  private final int hourOfDay, minute, second;

  public AlarmClock(int hourOfDay, int minute, int second) {
   this.hourOfDay = hourOfDay;
   this.minute = minute;
   this.second = second;
  }

  public void start() {
   scheduler.schedule(new SchedulerTask() {
    public void run() {
     soundAlarm();
    }
    private void soundAlarm() {
     return;
    }
   }, new DailyIterator(hourOfDay, minute, second));
  }
 }*/
}