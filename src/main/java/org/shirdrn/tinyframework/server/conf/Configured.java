package org.shirdrn.tinyframework.server.conf;

/**
 * It's a {@link Configuration} instance holder. A {@link Configuration} instance
 * contains 2 type of sub-configuration objects:
 * 
 * <ol>
 * <li>{@link ReadableContext}: a global configuration object, which can not
 * modify its content in current application scope.</li>
 * <li>{@link WritableContext}: a writable configuration object, whose content
 * can be updated if necessary. </li>
 * </ol>
 * 
 * @author Yanjun
 */
public abstract class Configured {
	
	protected ReadableContext readableContext;
	protected WritableContext writableContext;
	
	public ReadableContext getReadableContext() {
		return readableContext;
	}
	
	public void setReadableContext(ReadableContext readableContext) {
		this.readableContext = readableContext;
	}
	
	public WritableContext getWritableContext() {
		return writableContext;
	}
	
	public void setWritableContext(WritableContext writableContext) {
		this.writableContext = writableContext;
	}
	
}
