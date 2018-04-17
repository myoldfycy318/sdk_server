package com.dome.sdkserver.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Stack;

import org.springframework.util.Assert;



public class MarkupWriter {
	private Writer writer;
	private Stack tagStack;
	
	private boolean tagOpened = false;
	
	
	public MarkupWriter(Writer writer) {
		Assert.notNull(writer, "writer can not be null.");
		this.writer = writer;
		tagStack = new Stack();
	}
	
	
	public void begin(String tagName) throws IOException {
		Assert.notNull(tagName, "tagName can not be empty");
		
		if (tagOpened) {
			closeTag();
		}
		
		tagStack.push(tagName);
		
		writer.write("<");
		writer.write(tagName);
		
		tagOpened = true;
	}
	
	public void attribute(String name, String value) throws IOException {
		assertTagOpened();

        writer.write(' ');
        writer.write(name);
        writer.write("=\"");
        writer.write(value);
        writer.write('"');
    }

    public void end() throws IOException {
    	if (tagOpened) {
			closeTag();
		}
    	
    	Assert.state(tagStack.size() > 0, "No tags to end.");
        
    	writer.write("</");
        writer.write((String) tagStack.pop());
        writer.write(">\n");
    }
    
    public MarkupWriter write(String value) throws IOException {
    	if (tagOpened) {
			closeTag();
		}
    	
    	writer.write(value);
    	
    	return this;
    }
    
    public MarkupWriter write(int value) throws IOException {
    	if (tagOpened) {
			closeTag();
		}
    	
    	writer.write(String.valueOf(value));
    	return this;
    }
    
    public void flush() throws IOException {
    	writer.flush();
    }
    
    public void closeTag() throws IOException {
    	assertTagOpened();
    	
    	writer.write(">\n");
    	tagOpened = false;
    }
    
	protected void assertTagOpened() {
		Assert.state(tagOpened, "No tag has be opened");
	}
	
}