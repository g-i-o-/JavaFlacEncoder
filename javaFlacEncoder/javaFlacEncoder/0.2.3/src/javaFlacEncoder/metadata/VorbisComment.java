package javaFlacEncoder.metadata;

import java.io.ByteArrayOutputStream;  
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import com.gio.io.EndianAwareDataOutputStream;

import javaFlacEncoder.MetadataBlockHeader.MetadataBlockType;

/**
 * Represents a VorbisComment metadata element.
 * This element can be used to add arbitrary name=value lists
 * to a flac file.
 * @author Giovany Vega
 *
 */
public class VorbisComment extends EncodedMetadata {
	
	/**
	 * Holds the vendor string.
	 */
	String vendor;
	/**
	 * Holds the name/value pairs.
	 */
	Map<String, String> dictionary;
	/**
	 * Cached encoded element of all name/value pairs in this dictionary.
	 */
	byte[] encodedData;
	
	public VorbisComment() {
		dictionary = new HashMap<String, String>();
		encodedData = null;
	}
	
	@Override
	public MetadataBlockType getType() {
		return MetadataBlockType.VORBIS_COMMENT;
	}

	
	/**
	 * @return the vendor
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor the vendor to set
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/** 
	 * Returns the current value for the given name.
	 * @param name
	 * @return
	 */
	public String get(String name) {
		return dictionary.get(name);
	}

	/**
	 * Sets the value for a given name.
	 * @param name
	 * @param value
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public void set(String name, String value) {
		dictionary.put(name, value);
		encodedData = null;
	}

	/**
	 * Removes the given name from the list.
	 * @param name
	 */
	public void remove(String name) {
		dictionary.remove(name);
		encodedData = null;
	}



	/**
	 * Clears the properties associated to this comment.
	 */
	public void clear() {
		dictionary.clear();
		encodedData = null;
	}

	/**
	 * Returns an array of bytes representing this object in a serialized form.
	 * @return array of bytes representing this object in a FLAC file. 
	 */
	@Override
	public byte[] asByteArray() {
		if(encodedData == null){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			EndianAwareDataOutputStream bout = new EndianAwareDataOutputStream(baos);
			bout.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			byte[] vendor_utf8_bytes;
			try {
				vendor_utf8_bytes = vendor.getBytes("UTF-8");
				bout.writeEAInt(vendor_utf8_bytes.length);
				bout.write(vendor_utf8_bytes);
				bout.writeEAInt(dictionary.size());
				for(Map.Entry<String, String> entry : dictionary.entrySet()){
					byte[] comment_bytes = (entry.getKey() + "=" + entry.getValue()).getBytes("UTF-8");
					bout.writeEAInt(comment_bytes.length);
					bout.write(comment_bytes);
				}
				encodedData = baos.toByteArray();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return encodedData;
	}

}
