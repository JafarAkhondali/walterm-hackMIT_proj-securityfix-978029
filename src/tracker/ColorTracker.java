package tracker;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class ColorTracker {
	static Scalar RED_MIN = new Scalar(0, 100, 100);
	static Scalar RED_MAX = new Scalar(10, 200, 200);
	static Scalar BLUE_MIN = new Scalar(0, 0, 0);
	static Scalar BLUE_MAX = new Scalar(0, 0, 0);
	static Scalar GREEN_MIN = new Scalar(0, 0, 0);
	static Scalar GREEN_MAX = new Scalar(0, 0, 0);
	static Scalar YELLOW_MIN = new Scalar(0, 0, 0);
	static Scalar YELLOW_MAX = new Scalar(0, 0, 0);
	
	public static void main(String[] args) throws InterruptedException {
		System.loadLibrary("opencv_java246");
		VideoCapture camera = new VideoCapture(0);
		Thread.sleep(1000);
		camera.open(0);
		if(!camera.isOpened()){
			throw new RuntimeException("Could not open the web cam.");
		}	
		Mat frame = new Mat();
		camera.read(frame);
		Highgui.imwrite("camera_pic.jpg", frame);
		Mat contrast = new Mat();
		frame.convertTo(contrast, -1, 1.5);
		Highgui.imwrite("camera__pic_contrast.jpg", contrast);
		Mat hsv = new Mat();
		Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
		Mat hsvThresh = new Mat();
		Core.inRange(hsv, RED_MIN, RED_MAX, hsvThresh);
		Highgui.imwrite("camera_pic_red.jpg", hsvThresh);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat mat = new Mat();
		hsvThresh.convertTo(mat, CvType.CV_32SC1);
		Imgproc.findContours(mat, contours, new Mat(), Imgproc.RETR_FLOODFILL, Imgproc.CHAIN_APPROX_SIMPLE);
		Mat contourImg = new Mat(mat.size(), mat.type());
		System.out.println(contours.get(0).type());
		for(int i = 0; i< contours.size(); i++){
			Imgproc.drawContours(contourImg, contours, -1, new Scalar(255, 255, 255));
		}
		
		Highgui.imwrite("camera_pic_red_contours.jpg", contourImg);
		camera.release();
	}
}