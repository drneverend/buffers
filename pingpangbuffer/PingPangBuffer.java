package pzt.buffers.pingpangbuffer;

public class PingPangBuffer {
	private final static int bufferSize = 16;
	private Object[] buffer = new Object[bufferSize];
	private int head = 0;
	private int tail = 0;
	
	private Boolean empty() {
		return head == tail;
	}
	private Boolean full() {
		return (tail + 1) % bufferSize == head;
	}
	public Boolean put(Object v) {
		if (full()) {
			return false;
		}
		buffer[tail] = v;
		tail = (tail + 1) % bufferSize;
		return true;
	}
	public Object get() {
		if (empty()) {
			return null;
		}
		int copyTail = tail;
		int read = (copyTail + bufferSize - 1) % bufferSize;
		Object result = buffer[read];
		head = (read + 1) % bufferSize;
		return result;
	}
}
