package javaFlacEncoder.metadata;

import javaFlacEncoder.MetadataBlockStreamInfo;
import javaFlacEncoder.StreamConfiguration;
import javaFlacEncoder.MetadataBlockHeader.MetadataBlockType;

/**
 * Represents a VorbisComment metadata element.
 * This element can be used to add arbitrary name=value lists
 * to a flac file.
 * @author Giovany Vega
 *
 */
public class StreamInfo extends EncodedMetadata {
	byte[] encodedElement;

    StreamConfiguration streamConfig = null;
    int minFrameSize;
    int maxFrameSize;
    long samplesInStream;

	public StreamInfo(StreamConfiguration streamConfig, int minFrameSize,
			int maxFrameSize, long samplesInStream) {
		super();
		this.streamConfig = streamConfig;
		this.minFrameSize = minFrameSize;
		this.maxFrameSize = maxFrameSize;
		this.samplesInStream = samplesInStream;
	}

	@Override
	public MetadataBlockType getType() {
		return MetadataBlockType.STREAMINFO;
	}

	@Override
	public byte[] asByteArray() {
		if(encodedElement == null){
	        byte[] md5Hash = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//blank hash. Don't know it yet.
			encodedElement = MetadataBlockStreamInfo.getStreamInfo(
	                streamConfig, minFrameSize, maxFrameSize, samplesInStream,
	                md5Hash).getData();
		}
		return encodedElement;
	}

}
