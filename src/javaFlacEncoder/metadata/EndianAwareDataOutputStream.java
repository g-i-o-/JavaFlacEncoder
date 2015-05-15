package javaFlacEncoder.metadata;

import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

public class EndianAwareDataOutputStream extends DataOutputStream{

	private ByteOrder byteOrder;
	
	PartialInvertOutputStream outstream;
	
	public EndianAwareDataOutputStream(OutputStream out) {
		this(new PartialInvertOutputStream(out));
	}
	public EndianAwareDataOutputStream(PartialInvertOutputStream out) {
		super(out);
		outstream = out;
	}
		

	public void setByteOrder(ByteOrder order){
		byteOrder = order;
	}
	
	public void writeEADouble(double val) throws IOException{
		if(byteOrder == ByteOrder.LITTLE_ENDIAN){
			outstream.markInvertionPoint();
		}
		this.writeDouble(val);
		outstream.invertIfMarked();
	}
	public void writeEAFloat(float val) throws IOException{
		if(byteOrder == ByteOrder.LITTLE_ENDIAN){
			outstream.markInvertionPoint();
		}
		this.writeFloat(val);
		outstream.invertIfMarked();
	}
	public void writeEAInt(int val) throws IOException{
		if(byteOrder == ByteOrder.LITTLE_ENDIAN){
			outstream.markInvertionPoint();
		}
		this.writeInt(val);
		outstream.invertIfMarked();
	}
	public void writeEALong(int val) throws IOException{
		if(byteOrder == ByteOrder.LITTLE_ENDIAN){
			outstream.markInvertionPoint();
		}
		this.writeLong(val);
		outstream.invertIfMarked();
	}
	public void writeEAShort(int val) throws IOException{
		if(byteOrder == ByteOrder.LITTLE_ENDIAN){
			outstream.markInvertionPoint();
		}
		this.writeShort(val);
		outstream.invertIfMarked();
	}
	
	
	static private class PartialInvertOutputStream extends FilterOutputStream{
		public PartialInvertOutputStream(OutputStream out) {
			super(out);
			buffer = new byte[32];
		}

		boolean invert;
		byte[] buffer;
		int index;
		
		@Override
		public void write(int oneByte) throws IOException {
			if(invert){
				ensureBufferCapacity(index + 1);
				buffer[index] = (byte)oneByte;
				index++;
			} else {
				out.write(oneByte);
			}
			
		}
		
		private void ensureBufferCapacity(int size) {
			if(buffer.length < size){
				byte[] newbuffer = new byte[size + 32];
				System.arraycopy(buffer, 0, newbuffer, 0, buffer.length);
				buffer = newbuffer;
			}
		}
		
		public void markInvertionPoint(){
			invert=true;
		}
		
		public void invertIfMarked() throws IOException{
			if(invert){
				for(int i=index-1; i >=0; --i){
					out.write(buffer[i]);
				}
				index=0;
				invert=false;
			}
		}
	}
	
}
