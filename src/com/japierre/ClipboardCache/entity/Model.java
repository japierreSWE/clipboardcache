package com.japierre.ClipboardCache.entity;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.control.Label;

public class Model {
	
	int cacheSize; //the maximum size of the cache
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
	
	/**adds a string to the cache. returns whether cache changed */
	public boolean add(String s) {
		
		//don't change the cache if the clipboard hasn't changed.
		if(s.equals(currentClipboardContents)) {
			return false;
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
		return true;
	}
	
	public void push(Label selected) {
		
		String text = selected.getText();
		int indexOfFirstSpace = text.indexOf(" ");
		//each label's text starts with "number. "
		//so, the text coming after the space is an
		//element of the cache.
		
		//get it, remove it, then put it in front.
		String element = text.substring(indexOfFirstSpace + 1);
		cache.remove(element);
		currentClipboardContents = "";
		add(element);
		
	}
	
}
