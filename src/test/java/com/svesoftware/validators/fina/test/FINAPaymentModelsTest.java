package com.svesoftware.validators.fina.test;

import org.junit.Assert;
import org.junit.Test;

import com.svesoftware.validators.fina.FINAModelAndReference;
import com.svesoftware.validators.fina.FINAModelValidator;

public class FINAPaymentModelsTest {

	@Test
	public void testHR01_1() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR01");
		modelAndReference.setReferenceNumber("334445556669");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR16() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR16");
		modelAndReference.setReferenceNumber("12343-1236-12345678");
		FINAModelValidator fmv = new FINAModelValidator();
		fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testAlgorithms() {
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.mod11p7("3456789012") == 2);
		Assert.assertTrue(fmv.mod10("054370395") == 3);
		Assert.assertTrue(fmv.mod11("054370395") == 8);
		Assert.assertTrue(fmv.mod10zb("223344556") == 8);
		Assert.assertTrue(fmv.mod11ini("33444555666") == 9);
		Assert.assertTrue(fmv.iso7064("234000") == 9);
		Assert.assertTrue(fmv.mod11jmbg("2004940339319") == 0);

	}

	@Test
	public void testHR40() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR40");
		modelAndReference.setReferenceNumber("05437039538-12345-12345");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR15() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR15");
		modelAndReference.setReferenceNumber("12345674-12345678903");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR26() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR26");
		modelAndReference.setReferenceNumber("1236-12345674-12345674-99999");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR14() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR14");
		modelAndReference.setReferenceNumber("2233445568");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR13() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR13");
		modelAndReference.setReferenceNumber("3456789012");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR12() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR12");
		modelAndReference.setReferenceNumber("16049813530601");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertFalse(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR01() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR01");
		modelAndReference.setReferenceNumber("334445-5-56669");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR99Fail() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR99");
		modelAndReference.setReferenceNumber("1-359638-166304715");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertFalse(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR99Pass() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR99");
		modelAndReference.setReferenceNumber("");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR_1() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR");
		modelAndReference.setReferenceNumber("222");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR_2() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR");
		modelAndReference.setReferenceNumber("123-44-22");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertTrue(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR_3() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR");
		modelAndReference.setReferenceNumber("123-44-1234567890123456");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertFalse(fmv.isValid(modelAndReference, null));
	}

	@Test
	public void testHR10() {
		FINAModelAndReference modelAndReference = new FINAModelAndReference();
		modelAndReference.setModel("HR10");
		modelAndReference.setReferenceNumber("123-44-1234");
		FINAModelValidator fmv = new FINAModelValidator();
		Assert.assertFalse(fmv.isValid(modelAndReference, null));
	}

}
