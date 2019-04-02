package com.avekshaa.service;

public class ControllerView {

	public static String showView(String designation) {
		String val = null;
		if ("HR".equalsIgnoreCase(designation)) {
			val = "Hr";
		} else if ("Software Engineer".equalsIgnoreCase(designation)
				|| "Senior Software Engineer".equalsIgnoreCase(designation)
				|| "Technical Specialist".equalsIgnoreCase(designation)) {
			val = "Employee";
		} else if ("Manager".equalsIgnoreCase(designation)) {
			val = "manager";
		} else if ("Delivery Head".equalsIgnoreCase(designation)) {
			val = "DeleveryHeadTask";
		} else if ("Finance".equalsIgnoreCase(designation)) {
			val = "finance";
		}
		return val;
	}
}
