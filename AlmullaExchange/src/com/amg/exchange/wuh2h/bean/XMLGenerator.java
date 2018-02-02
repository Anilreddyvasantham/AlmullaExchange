package com.amg.exchange.wuh2h.bean;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public final class XMLGenerator {

	private static XStream xstream = null;

	static {
		xstream = new XStream(new DomDriver());
		xstream.setMode(XStream.NO_REFERENCES);
	}

	private XMLGenerator() {

	}

	public static String generateXML(Object obj) {
		return xstream.toXML(obj);
	}

}
