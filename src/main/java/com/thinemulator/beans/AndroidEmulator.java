package com.thinemulator.beans;

import org.hibernate.validator.constraints.NotBlank;

public class AndroidEmulator {
	
	@NotBlank(message = "emulatorName can not be empty") private String emulatorName;
	@NotBlank(message = "emulatorTargetId can not be empty") private String emulatorTargetId;
	
	public String getEmulatorName() {
		return emulatorName;
	}
	public void setEmulatorName(String emulatorName) {
		this.emulatorName = emulatorName;
	}
	public String getEmulatorTargetId() {
		return emulatorTargetId;
	}
	public void setEmulatorTargetId(String emulatorTargetId) {
		this.emulatorTargetId = emulatorTargetId;
	}
}