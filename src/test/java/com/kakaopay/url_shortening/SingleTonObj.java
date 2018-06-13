package com.kakaopay.url_shortening;

import java.util.ArrayList;
import java.util.List;

public class SingleTonObj {

    private static final SingleTonObj INSTANCE;
    private List<String> list;

    static {
        INSTANCE = new SingleTonObj();
        INSTANCE.list = new ArrayList<>();
    }

    private SingleTonObj() {

    }

    public static SingleTonObj getInstance() {
        return INSTANCE;
    }

    public List<String> getList() {
        return INSTANCE.list;
    }

}
