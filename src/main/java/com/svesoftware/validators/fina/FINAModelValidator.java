package com.svesoftware.validators.fina;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FINAModelValidator implements ConstraintValidator<FINAModel, FINAModelAndReference> {

    private static Logger log = LoggerFactory.getLogger(FINAModelValidator.class);
    
    private static final String VALIDATOR_RULES = "com.svesoftware.validators.fina.validator";
    private static ResourceBundle rb = ResourceBundle.getBundle(VALIDATOR_RULES);
    private static final String MOD11INI = "MOD11INI";
    private static final String MOD11P7 = "MOD11P7";
    private static final String MODUL10_MODUL11 = "MODUL10_MODUL11";
    private static final String MODUL10 = "MODUL10";
    private static final String MODUL11 = "MODUL11";
    private static final String ISO7064 = "ISO7064";
    private static final String MOD10ZB = "MOD10ZB";
    private static final String MOD11JMB = "MOD11JMB";
    private static final String EMPTY = "EMPTY";
    private static final String NOMODEL = "NOMODEL";
    private static final String HR = "HR";
    private static final String ALGO_SEPARATOR = "@";

    public void initialize(FINAModel constraintAnnotation) {

    }

    public boolean isValid(FINAModelAndReference value, ConstraintValidatorContext context) {

        String modelLookup = null;

        /* prepend model with 'HR' if consists of two digits only or if empty */
        if (value != null && StringUtils.trimToNull(value.getModel()) != null && !value.getModel().startsWith(HR)) {
            modelLookup = HR + value.getModel();
        }
        if (value != null && StringUtils.trimToNull(value.getModel()) != null && value.getModel().startsWith(HR)) {
            modelLookup = value.getModel();
        }
        if (value != null && StringUtils.trimToNull(value.getModel()) == null) {
            modelLookup = HR;
        }
        if (value != null && StringUtils.trimToNull(value.getReferenceNumber()) == null) {
            value.setReferenceNumber("");
        }

        String algoString = null;
        String referenceNumber = null;
        Pattern modelPattern = null;

        try {

            String modelDefinitions = rb.getString(modelLookup);
            /* check for multiple model definitions */
            for (String str : modelDefinitions.split(ALGO_SEPARATOR)) {
                String splits[] = str.split(" ");
                String strPattern = splits[1];
                algoString = splits[0];
                modelPattern = Pattern.compile(strPattern);
                /*
                 * if reference number does not match current algo, try with
                 * next one
                 */
                if (modelPattern.matcher(value.getReferenceNumber()).matches() == false) {
                    continue;
                } else {
                    break;
                }
            }
            referenceNumber = value.getReferenceNumber();
            referenceNumber = org.apache.commons.lang.StringUtils.trimToNull(referenceNumber);
        } catch (MissingResourceException e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        } catch (NullPointerException e) {
            log.error(e.getMessage(), e.getCause());
            return false;
        }

        /* check pattern */
        if (modelPattern.matcher(value.getReferenceNumber()).matches() == false) {
            return false;
        }

        /* extract number groups from reference number and add them to the list */
        Pattern patternParts = Pattern.compile("(\\d)+");
        Matcher matcherParts = patternParts.matcher(value.getReferenceNumber());
        List<String> refNumParts = new ArrayList<String>();
        while (matcherParts.find()) {
            String referenceNumberPart = matcherParts.group();
            refNumParts.add(referenceNumberPart);
        }

        /* apply algo on the reference number groups according to specification */
        Pattern patternCode = Pattern.compile("\\(([\\w-])*\\)([\\w_]*)");
        Matcher matcherCode = patternCode.matcher(algoString);
        while (matcherCode.find()) {
            String group = matcherCode.group();
            String algo = group.substring(group.lastIndexOf(")") + 1);
            String descr = group.substring(group.indexOf("(") + 1, group.lastIndexOf(")"));
            descr = descr.replaceAll("P", "");
            String[] parts = descr.split("-");
            StringBuffer composed = new StringBuffer();
            for (String part : parts) {
                int partNum = Integer.valueOf(part) - 1;
                if (partNum < refNumParts.size()) {
                    composed.append(refNumParts.get(partNum));
                } else {
                    break;
                }
            }
            /* apply algo */
            boolean res = algo(algo, composed.toString());
            if (res == false) {
                return false;
            }
        }

        return true;

    }

    public boolean algo(String model, String referenceNumber) {
        if (model != null && model.equals(NOMODEL)) {
            return true;
        } else if (model != null && model.equals(EMPTY)) {
            return referenceNumber == null;
        } else if (model != null && model.equals(MOD11INI)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod11ini(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(MOD11P7)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod11p7(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(MODUL10)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod10(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(MODUL11)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod11(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(ISO7064)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return iso7064(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(MOD10ZB)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod10zb(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(MOD11JMB)) {
            int kbu = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod11jmbg(referenceNumber.substring(0, referenceNumber.length() - 1)) == kbu;
        } else if (model != null && model.equals(MODUL10_MODUL11)) {
            int kbu1 = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 2, referenceNumber.length() - 1));
            int kbu2 = Integer.valueOf(referenceNumber.substring(referenceNumber.length() - 1));
            return mod10(referenceNumber.substring(0, referenceNumber.length() - 2)) == kbu1 && mod11(referenceNumber.substring(0, referenceNumber.length() - 2)) == kbu2;
        } else {
            return false;
        }
    }

    public int mod11ini(String value) {
        int length = value.length();
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum = sum + Integer.valueOf(value.substring(length - i - 1, length - i)) * (i + 2);
        }
        int res = sum % 11;
        if (res > 1) {
            res = 11 - res;
        } else {
            res = 0;
        }
        return res;
    }

    public int iso7064(String value) {
        int t = 10;
        for (int i = 0; i < value.length(); i++) {
            int c = Integer.valueOf(value.substring(i, i + 1));
            int k = (t + c) % 10;
            if (k == 0) {
                k = 10;
            }
            t = (2 * k) % 11;
        }
        return ((11 - t) % 10);
    }

    public int mod11p7(String value) {
        int length = value.length();
        if (value.charAt(0) != '3') {
            return -1;
        }
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += Integer.valueOf(value.substring(length - i - 1, length - i)) * ((i % 6) + 2);
        }
        int res = sum % 11;
        if (res == 0) {
            return 5;
        } else if (res == 1) {
            return 0;
        } else {
            return 11 - res;
        }

    }

    public int mod10zb(String value) {
        int l = value.length();
        int res = 0;
        for (int i = 0; i < l; i++) {
            res += Integer.valueOf(value.substring(l - i - 1, l - i)) * (i % 2 + 1);
        }
        return res % 10;
    }

    public int mod10(String value) {
        int l = value.length();
        int res = 0;
        for (int i = 0; i < l; i++) {
            int num = Integer.valueOf(value.substring(l - i - 1, l - i)) * (((i + 1) % 2) + 1);
            res += ((num / 10) + (num % 10));
        }
        res = res % 10;
        if (res == 0) {
            return 0;
        } else {
            return 10 - res;
        }
    }

    public int mod11(String value) {
        int l = value.length();
        int res = 0;
        for (int i = 0; i < l; i++) {
            res += Integer.valueOf(value.substring(l - i - 1, l - i)) * (i % 6 + 2);

        }
        res = res % 11;
        if (res > 1) {
            return 11 - res;
        } else {
            return 0;
        }
    }

    public int mod11jmbg(String value) {
        char c = value.charAt(0);
        boolean differs = false;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != c) {
                differs = true;
            }
        }
        if (!differs) {
            return -1;
        }

        int length = value.length();
        int sum = 0;

        int s = 1;
        for (int i = 0; i < length; i++) {
            sum = sum + Integer.valueOf(value.substring(length - i - 1, length - i)) * (s);
            s = s + 1;
            if (i == 6) {
                s = 2;
            }
        }
        int res = sum % 11;
        return res;
    }

}
