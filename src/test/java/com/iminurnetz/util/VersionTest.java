package com.iminurnetz.util;

import com.iminurnetz.util.Version;

import junit.framework.TestCase;

public class VersionTest extends TestCase {
    public void testVersion() {
        Version v = new Version("1.1");
        assertEquals("1.1", v.toString());
        assertTrue(v.isEarlierVersion(new Version("1.2")));
        assertTrue(v.isLaterVersion(new Version("1.0")));
    }

    public void testLatest() {
        Version v = new Version("1.2.3");
        assertEquals("1.2.3", v.toString());
    }

    public void testMoreThanVersion() {
        Version v = new Version("1.2.3\t1.1-R5");
        assertEquals("1.2.3", v.toString());

        v = new Version("1.2.3-1.1-R5");
        assertEquals("1.2.3", v.toString());
    }
    
    public void testEmptyVersion() {
        Version v = new Version();
        assertTrue(v.isEarlierVersion(new Version("1")));
        assertFalse(v.isLaterVersion(new Version("1")));
    }
}
