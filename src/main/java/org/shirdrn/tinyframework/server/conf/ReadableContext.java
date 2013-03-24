package org.shirdrn.tinyframework.server.conf;


/**
 * Readable context interface.
 * 
 * @author Yanjun
 */
public interface ReadableContext {

	String get(String name);
	String get(String name, String defaultValue);
	String[] getStrings(String name);
	String[] getStrings(String name, String... defaultValue);
	byte getByte(String name, byte defaultValue);
	int getInt(String name, int defaultValue);
	long getLong(String name, long defaultValue);
	float getFloat(String name, float defaultValue);
	boolean getBoolean(String name, boolean defaultValue);
	Object getObject(String name);
	
	
}
