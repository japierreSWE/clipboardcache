package com.japierre.ClipboardCache.controller;

import com.japierre.ClipboardCache.entity.Model;

public class ClipboardChangeController {
	
	public boolean add(Model model, String str) {
		
		return model.add(str);
		
	}
	
}
