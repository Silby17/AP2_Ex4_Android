package com.example.yossi.ap2_ex4;

import com.squareup.otto.Bus;

public class BusProvider {
    private static final Bus BUS = new Bus();

    public BusProvider(){}

    public static Bus getInstance(){
        return BUS;
    }
}