package com.japierre.ClipboardCache.entity;

import java.util.LinkedList;
import java.util.List;

public class Model {
	
	int cacheSize; //the size of the cache
	LinkedList<String> cache;
	String currentClipboardContents;
	
	public Model() {
		
	}
	
	//initialize a model with a certain cache size
	public Model(int size) {
		cacheSize = size;
		cache = new LinkedList<String>();
		currentClipboardContents = "";
	}
	
	public int getSize() {
		return cacheSize;
	}
	
	/** returns the cache */
	public List<String> getCache() {
		return cache;
	}
	
	/**adds a string to the cache */
	public void add(String s) {
		
		if(s.equals(currentClipboardContents)) {
			return;
		} else {
			currentClipboardContents = s;
		}
		
		//if the cache is full, we remove the oldest one. That is, the last
		if(cache.size() == cacheSize) {
			cache.removeLast();
			cache.addFirst(s);
		} else {
			cache.addFirst(s);
		}
		
	}
	
}
