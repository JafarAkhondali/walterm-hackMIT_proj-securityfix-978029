package tracker;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class ColorTracker {
	static Scalar RED_MIN = new Scalar(0, 100, 100);
	static Scalar RED_MAX = new Scalar(10, 200, 200);
	
	public static void main(String[] args) throws InterruptedException {
		System.loadLibrary("opencv_java246"); //something about native java
		
		//find the camera, wait for it to initialize, and then access it
		VideoCapture camera = new VideoCapture(0); 
		Thread.sleep(1000);
		camera.open(0);
		if(!camera.isOpened()){
			throw new RuntimeException("Could not open the web cam.");
		}	
		
		//Get a frame from the camera
		Mat frame = new Mat();
		camera.read(frame);
		Highgui.imwrite("camera_pic.jpg", frame); //DEBUG
		
		//Increase the contrast gain by 1.5
		Mat contrast = new Mat();
		frame.convertTo(contrast, -1, 1.5);
		Highgui.imwrite("camera__pic_contrast.jpg", contrast); //DEBUG
		
		//convert the image to HSV for easier color detection
		Mat hsv = new Mat();
		Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
		
		//Threshold the image based on the HSV values of the desired color
		Mat hsvThresh = new Mat();
		Core.inRange(hsv, RED_MIN, RED_MAX, hsvThresh);
		Highgui.imwrite("camera_pic_red.jpg", hsvThresh);
		
		//Expand the white areas of the threshold (the areas that were 
		//the desired color) to make finding the contours easier
		Imgproc.dilate(hsvThresh, hsvThresh, new Mat());
		
		//Find the contours!
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat convert = new Mat();
		hsvThresh.convertTo(convert, CvType.CV_32SC1);  //need image to be 32SC1, not sure why.
		Imgproc.findContours(convert, contours, new Mat(), Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		//Draw all the contours such that they are filled in.
		Mat contourImg = new Mat(convert.size(), convert.type());
		for(int i = 0; i< contours.size(); i++){
			Imgproc.drawContours(contourImg, contours, i, new Scalar(255, 255, 255), -1);
		}
		Highgui.imwrite("camera_pic_red_contours.jpg", contourImg); //DEBUG
		
		//Find the contours of the filled in mass of previous contours
		Imgproc.findContours(contourImg, contours, new Mat(), Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);
		
		//Find the largest of those contours
		double max = 0; 
		int maxCont = 0;
		for(int i = 0; i< contours.size(); i++){
			double area = Imgproc.contourArea(contours.get(i));
			if (area > max){
				max = area;
				maxCont = i;
			}
		}
		
		//Get the rectangle that fits around the boundary of the largest contour
		//Use it to figure out the center (x, y) of the contour
		Rect bound = Imgproc.boundingRect(contours.get(maxCont));
		double y = bound.y+(bound.height/2);
		double x = bound.x+(bound.width/2);
		Core.circle(frame, new Point(x, y), 50, new Scalar(255, 0, 0)); //DEBUG
		Highgui.imwrite("camera_pic_final.jpg", frame); //DEBUG
		
		//Release the camera so the program properly terminates
		camera.release();
	}
}