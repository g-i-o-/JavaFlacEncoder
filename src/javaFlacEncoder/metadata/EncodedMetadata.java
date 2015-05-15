/*
 * Copyright (C) 2010  Preston Lacey http://javaflacencoder.sourceforge.net/
 * All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package javaFlacEncoder.metadata;

import javaFlacEncoder.MetadataBlockHeader.MetadataBlockType;

/**
 * Base class of metadata items that can be written to a flac file.
 * 
 * @author Giovany Vega
 */
public abstract class EncodedMetadata {
	

	/** Returns a header describing this metadata element.
	 * @param isLast - whether the header should indicate that this 
	 * 		metadata element is the last one.
	 * @return header describing the metadata element.
	 */
	public byte[] getHeader(boolean isLast){
		byte[] element = asByteArray();
		int length = element.length;
		byte[] header = {
			(byte)((isLast ? 0x80 : 0) | getType().ordinal()), 
			(byte)((length >> 16) & 0xfF),
			(byte)((length >>  8) & 0xfF),
			(byte)((length      ) & 0xfF)
		};
		return header;
	}
	
	
	abstract public MetadataBlockType getType();

	/** Returns the encoded element's data, ready to write to the flac stream.
	 * @return a byte array ready to be written to an output stream.
	 */
	public abstract byte[] asByteArray();

}
