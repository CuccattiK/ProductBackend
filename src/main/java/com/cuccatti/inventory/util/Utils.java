package com.cuccatti.inventory.util;

import com.cuccatti.inventory.exception.JSONParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	private Utils() {
		throw new IllegalStateException("Utility classes do not get instantiated.");
	}
	
	public static String asJsonString(final Object obj) {
		try {
	        final ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new JSONParseException(e.getMessage());
	    }
	}  
	
	
}
