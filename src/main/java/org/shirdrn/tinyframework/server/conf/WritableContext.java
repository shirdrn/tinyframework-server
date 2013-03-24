package org.shirdrn.tinyframework.server.conf;

import java.io.File;
import java.net.URL;

/**
 * Writable context. We can store a key/value pair on 
 * instance of concrete class of this interface.
 * 
 * @author Yanjun
 */
public interface WritableContext extends ReadableContext {
	
	void addResource(String name);
	void addResource(URL url);
	void addResource(File file);
	
	void set(String name, String value);
	void setIfUnset(String name, String value);
	void setStrings(String name, String... values);
	void setInt(String name, int value);
	void setByte(String name, byte value);
	void setLong(String name, long value);
	void setFloat(String name, float value);
	void setBoolean(String name, boolean value);
	void setBooleanIfUnset(String name, boolean value);
	void setClass(String name, Class<?> theClass, Class<?> xface);
	void setClassLoader(ClassLoader classLoader);
	void setObject(String name, Object value);
	
}
