package com.nuc.libra.jni;

import com.sun.jna.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 杨晓辉 2019-02-07 16:44
 */
public class GoString extends Structure {

    public String str;
    public long length;

    public GoString() {

    }

    public GoString(String str) {
        this.str = str;
        this.length = str.length();
    }

    @Override
    protected List<String> getFieldOrder() {
        List<String> files = new ArrayList<>();
        files.add("str");
        files.add("length");
        return files;
    }

    public static class ByValue extends GoString implements Structure.ByValue {
        public ByValue() {
        }

        public ByValue(String str) {
            super(str);
        }
    }

    public static class ByReference extends GoString implements Structure.ByReference {
        public ByReference() {
        }

        public ByReference(String str) {
            super(str);
        }
    }
}
