package com.japierre.ClipboardCache.entity;

public class Model {
	
	int cacheSize; //the size of the cache
	
	public Model() {
		
	}
	
	//initialize a model with a certain cache size
	public Model(int size) {
		cacheSize = size;
	}
	
	public int getSize() {
		return cacheSize;
	}
	
}
