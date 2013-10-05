package tracker;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class ColorTracker {
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
		camera.release();
	}
}