package com.amg.exchange.common;

import java.util.Random;

import org.apache.log4j.Logger;


/**
 * Dynamic Token Generation Algorithm
 * @author biju
 *
 */
public class TokenGeneration {

	private final Random random = new Random();

	private char[] buf;
	private char[] symbols;

	public String getRandomIdentifier(int length) throws Exception {
		buf = new char[length];
		try {
			if (length < 1)
				throw new IllegalArgumentException("length < 1: " + length);

			StringBuilder tokenBuffer = new StringBuilder();
			for (char ch = '0'; ch <= '9'; ++ch)
				tokenBuffer.append(ch);
			for (char ch = 'a'; ch <= 'z'; ++ch)
				tokenBuffer.append(ch);
			for (char ch = 'A'; ch <= 'Z'; ++ch)
				tokenBuffer.append(ch);
			symbols = tokenBuffer.toString().toCharArray();

			for (int idx = 0; idx < buf.length; ++idx)
				buf[idx] = symbols[random.nextInt(symbols.length)];
			return new String(buf);

		} catch (ArrayIndexOutOfBoundsException aioobe) {
			Logger.getLogger("");
		}

		return null;

	}
}