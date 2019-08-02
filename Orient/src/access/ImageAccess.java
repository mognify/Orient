package access;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import orient.Orient;
 
public class ImageAccess {
 
    public Point screenSearch(String targetName) {
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source=null;
        Mat template=null;
        String filePath=Orient.workingDirectory;
        //Load image file
        source=Imgcodecs.imread(filePath+"kapadokya.jpg"); // TODO replace with clipboard screenshot
        template=Imgcodecs.imread(filePath+targetName+".jpg");
    
        Mat outputImage=new Mat();    
        int machMethod=Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);
 
        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc=mmr.maxLoc;
        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));
 
        Imgcodecs.imwrite(filePath+"TemplateMatchingOutput.jpg", source);

	return matchLoc;
    }
 
}
