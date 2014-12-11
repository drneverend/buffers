package pzt.buffers.ringbuffer;

public class RingBuffer1r2w {
	private final static int bufferSize = 1024;
	private final static int halfBufferSize = bufferSize / 2;
	private String[] buffer = new String[bufferSize];
	private int head1 = 0;
	private int tail1 = 0;
	private int head2 = 0;
	private int tail2 = 0;
	private int nextReadBuffer = 0;
	
	private Boolean empty1() {
		return head1 == tail1;
	}
	private Boolean empty2() {
		return head2 == tail2;
	}
	private Boolean empty() {
		return empty1() && empty2();
	}
	private Boolean full1() {
		return (tail1 + 1) % halfBufferSize == head1;
	}
	private Boolean full2() {
		return (tail2 + 1) % halfBufferSize == head2;
	}
	public Boolean put1(String v) {
		if (full1()) {
			return false;
		}
		buffer[tail1] = v;
		tail1 = (tail1 + 1) % halfBufferSize;
		return true;
	}
	public Boolean put2(String v) {
		if (full2()) {
			return false;
		}
		buffer[tail2 + halfBufferSize] = v;
		tail2 = (tail2 + 1) % halfBufferSize;
		return true;
	}
	public String get() {
		if (empty()) {
			return null;
		}
		String result = null;
		if (nextReadBuffer == 0 && !empty1() || nextReadBuffer == 1 && empty2()) {
			result = buffer[head1];
			head1 = (head1 + 1) % halfBufferSize;
		} else {
			result = buffer[head2 + halfBufferSize];
			head2 = (head2 + 1) % halfBufferSize;
		}
		
		nextReadBuffer = (nextReadBuffer + 1) % 2;

		return result;
	}
}
