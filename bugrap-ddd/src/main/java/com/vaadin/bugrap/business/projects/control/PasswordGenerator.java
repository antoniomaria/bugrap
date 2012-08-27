package com.vaadin.bugrap.business.projects.control;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple password generator.
 */
public class PasswordGenerator {

	private static final String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";

	public static String generate(int length) {
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int pos = rand.nextInt(charset.length());
			sb.append(charset.charAt(pos));
		}

		return sb.toString();
	}

	public static String encrypt(String plaintext){
		MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
            md.update(plaintext.getBytes("UTF-8"));
        } catch (Exception ex) {
            throw new IllegalStateException("Problem encrypting ",ex);
        }

                byte raw[] = md.digest();
		StringBuilder hash = new StringBuilder();
		for (int i = 0; i < raw.length; i++) {
			String hex = Integer.toHexString(0xFF & raw[i]);
			if (hex.length() == 1) {
				hash.append('0');
			}
			hash.append(hex);
		}
		return hash.toString();
	}
}
