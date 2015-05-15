package javaFlacEncoder.metadata;

import javaFlacEncoder.MetadataBlockHeader.MetadataBlockType;

/**
 * @author gio
 *
 */
public class Padding extends EncodedMetadata {
	
	/**
	 *  Padding length. 
	 */
	int length;
	byte[] padding;

	
	public Padding(int length) {
		super();
		this.length = length;
	}
	
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
		padding = null;
	}

	@Override
	public MetadataBlockType getType() {
		return MetadataBlockType.PADDING;
	}

	@Override
	public byte[] asByteArray() {
		if(padding == null){
			padding = new byte[length];
		}
		return padding;
	}

}
