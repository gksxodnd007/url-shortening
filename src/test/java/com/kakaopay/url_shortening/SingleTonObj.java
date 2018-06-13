package com.kakaopay.url_shortening;

import java.util.ArrayList;
import java.util.List;

class SingleTonObj {

    private static final SingleTonObj INSTANCE;
    private List<String> list;

    static {
        INSTANCE = new SingleTonObj();
        INSTANCE.list = new ArrayList<>();
    }

    private SingleTonObj() {

    }

    static SingleTonObj getInstance() {
        return INSTANCE;
    }

    List<String> getList() {
        return INSTANCE.list;
    }

}
