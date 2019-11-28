package com.example.busapp.BUSDTO;

// 버스아이디와 배차 시간 클래스
// 버스 노드 클래스
public class BUSNODE {

    private String busID;
    private String busName;
    private String interval;

    public String getBusID() {
        return busID;
    }
    public BUSNODE setBusID(String busID) {
        this.busID = busID;
        return this;
    }
    public String getBusName() {
        return busName;
    }
    public BUSNODE setBusName(String busName) {
        this.busName = busName;
        return this;
    }
    public String getInterval() {
        return interval;
    }
    public BUSNODE setInterval(String interval) {
        this.interval = interval;
        return this;
    }

    public BUSNODE() {}
    public BUSNODE(String busID, String busName, String interval) {
        this.busID = busID;
        this.busName = busName;
        this.interval = interval;
    }
}