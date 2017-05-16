package de.mavecrit.pawars.booleans;


public class GameBool {

	public static boolean started = false;
	public static boolean full = false;
	public static boolean Finish = false;
	
	public static boolean destroyed_yellow = false;
	public static boolean destroyed_red = false;
	public static boolean destroyed_green = false;
	public static boolean destroyed_blue = false;
	
	public static boolean isStarted() {
		return started;
	}
	public static void setStarted(boolean started) {
		GameBool.started = started;
	}
	
	public static boolean isEnough() {
		return full;
	}
	public static void setEnough(boolean full) {
		GameBool.full = full;
	}
}
