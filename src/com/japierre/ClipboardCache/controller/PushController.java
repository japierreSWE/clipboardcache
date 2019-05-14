package com.japierre.ClipboardCache.controller;

import com.japierre.ClipboardCache.entity.Model;

import javafx.scene.control.Label;

public class PushController {
	
	public boolean push(Model model, Label selected) {
		
		if(selected == null) {
			return false;
		} else {
			model.push(selected);
			return true;
		}
		
	}
	
}
