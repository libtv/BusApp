package com.example.busapp.BUSDTO;

//버스 루트 클릭시 나오는 버스루트의 노드들.

public class BUSROUTE_NODE {

    private String busroute_ID; // 버스아이디
    private String busroute_Name; // 버스 이름
    private String destination; // 도착지명 ( --방향 )
    private String status_pos; // 남은 정거장 수
    private String extime_min; // 도착 예정 시간
    private String extime_sec; // 출발 예정 시간

    public String getBusroute_ID() {
        return busroute_ID;
    }

    public BUSROUTE_NODE setBusroute_ID(String busroute_ID) {
        this.busroute_ID = busroute_ID;
        return this;
    }

    public String getBusroute_Name() {
        return busroute_Name;
    }

    public BUSROUTE_NODE setBusroute_Name(String busroute_Name) {
        this.busroute_Name = busroute_Name;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public BUSROUTE_NODE setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getStatus_pos() {
        return status_pos;
    }

    public BUSROUTE_NODE setStatus_pos(String status_pos) {
        this.status_pos = status_pos;
        return this;
    }

    public String getExtime_min() {
        return extime_min;
    }

    public BUSROUTE_NODE setExtime_min(String extime_min) {
        this.extime_min = extime_min;
        return this;
    }

    public String getExtime_sec() {
        return extime_sec;
    }

    public BUSROUTE_NODE setExtime_sec(String extime_sec) {
        this.extime_sec = extime_sec;
        return this;
    }

    BUSROUTE_NODE() {
    }

    public BUSROUTE_NODE(String busroute_ID, String busroute_Name, String destination, String status_pos, String extime_min, String extime_sec) {
        this.busroute_ID = busroute_ID;
        this.busroute_Name = busroute_Name;
        this.destination = destination;
        this.status_pos = status_pos;
        this.extime_min = extime_min;
        this.extime_sec = extime_sec;
    }
}