package org.liblouis;

import java.io.UnsupportedEncodingException;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

public class Louis {

	private final int outlenMultiplier;
	private final String encoding;
	private final LouisLibrary INSTANCE;

	private static Louis instance;

	public static String translate(final String trantab, final String inbuf) {
		return getInstance().translateString(trantab, inbuf);
	}

	public static Louis getInstance() {
		if (instance == null) {
			instance = new Louis();
		}
		return instance;
	}

	public String version() {
		return INSTANCE.lou_version();
	}

	private Louis() {
		INSTANCE = (LouisLibrary) Native.loadLibrary(("louis"),
				LouisLibrary.class);
		outlenMultiplier = INSTANCE.lou_charSize();
		switch (outlenMultiplier) {
		case 2:
			encoding = "UTF-16LE";
			break;
		case 4:
			encoding = "UTF-32LE";
			break;
		default:
			throw new RuntimeException(
					"unsuported char size configured in liblouis: "
							+ outlenMultiplier);
		}
	}

	private String translateString(final String trantab, final String inbuf) {
		try {
			final int inlen = inbuf.length();
			if (inlen == 0) {
				return "";
			}
			final byte[] inbufArray = inbuf.getBytes(encoding);
			final byte[] outbufArray = new byte[outlenMultiplier
					* inbufArray.length];
			final IntByReference poutlen = new IntByReference(inlen
					* outlenMultiplier);
			if (INSTANCE.lou_translateString(trantab, inbufArray,
					new IntByReference(inlen), outbufArray, poutlen, null,
					null, 0) == 0) {
				throw new RuntimeException("Unable to complete translation");
			}
			return new String(outbufArray, 0, poutlen.getValue()
					* (inbufArray.length / inlen), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Encoding not supported by JVM:"
					+ encoding);
		}
	}

	public void louFree() {
		INSTANCE.lou_free();
	}

	interface LouisLibrary extends Library {

		public int lou_translateString(final String trantab,
				final byte[] inbuf, final IntByReference inlen,
				final byte[] outbuf, final IntByReference outlen,
				final byte[] typeform, final byte[] spacing, final int mode);

		public int lou_charSize();

		public String lou_version();

		public String lou_free();
	}

	public static void main(final String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: prog table(s) string");
			System.exit(1);
		}
		else {
			System.out.println(Louis.translate(args[0], args[1]));
		}

	}
}
