package com.example.busapp.BUSDTO;

import android.graphics.drawable.Drawable;

public class BUSROUTE {

    private String routeID;
    private String routeName;
    private boolean whereis;

    public String getRouteID() {
        return routeID;
    }

    public BUSROUTE setRouteID(String routeID) {
        this.routeID = routeID;
        return this;
    }

    public String getRouteName() {
        return routeName;
    }

    public BUSROUTE setRouteName(String routeName) {
        this.routeName = routeName;
        return this;
    }

    public boolean getWhereis() {
        return whereis;
    }

    public BUSROUTE setWhereis(boolean whereis) {
        this.whereis = whereis;
        return this;
    }


    BUSROUTE() {}

    public BUSROUTE(String routeID, String routeName) {
        this.routeID = routeID;
        this.routeName = routeName;
    }

    public BUSROUTE(String routeID, String routeName, boolean whereis) {
        this.routeID = routeID;
        this.routeName = routeName;
        this.whereis = whereis;
    }
}