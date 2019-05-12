package com.japierre.ClipboardCache.entity;

public class Model {
	
	int cacheSize; //the size of the cache
	
	public Model() {
		cacheSize = 0;
	}
	
	//sets the size of the cache.
	public void setSize(int size) {
		cacheSize = size;
	}
	
}
