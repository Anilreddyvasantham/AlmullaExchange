package com.amg.exchange.bundle;

import java.util.HashMap;

@SuppressWarnings("rawtypes")
public class MessageProvider extends HashMap{

    /**
	 * Generated serialversionUID.
	 */
	private static final long serialVersionUID = -4439477716898541411L;
	private MessageManager msgMgr;
    public MessageProvider() {
    }


    @Override
    public Object get(Object key) {
        return msgMgr.getMessage((String)key);
    }

    public void setMsgMgr(MessageManager msgMgr) {
        this.msgMgr = msgMgr;
    }

    public MessageManager getMsgMgr() {
        return msgMgr;
    }
}

