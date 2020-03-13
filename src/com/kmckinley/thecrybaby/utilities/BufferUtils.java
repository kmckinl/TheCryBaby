package com.kmckinley.thecrybaby.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;


//lwjgl has it's own BufferUtil function
public class BufferUtils {
	
	private BufferUtils() {
		
	}
	
	public static ByteBuffer createByteBuffer(byte[] array) {
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}
	
	public static FloatBuffer createFloatBuffer(float[] array) {
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}
	//<<2 is the same as *4, it is a shift of 2^2 or 4
	public static IntBuffer createIntBuffer(int[] array) {
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}
}