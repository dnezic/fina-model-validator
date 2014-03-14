package com.svesoftware.validators.fina;

import java.io.Serializable;

import javax.validation.constraints.Size;

public class FINAModelAndReference implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Size(min = 2, max = 2)
	private String model;
	@Size(max = 22)
	private String referenceNumber;

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
