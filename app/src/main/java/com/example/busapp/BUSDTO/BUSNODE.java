package com.example.busapp.BUSDTO;

// 버스아이디와 배차 시간 클래스
// 버스 노드 클래스
public class BUSNODE {

    private String busID;
    private String busName;
    /* 버스 배차 */
    private String allo_Interval;
    private String allo_Interval_sat;
    private String allo_Interval_sun;
    /* 버스 카운트 */
    private String BUSSTOP_CNT;
    /* 운행 출발 시간 */
    private String ORIGIN_START;
    private String ORIGIN_START_SAT;
    private String ORIGIN_START_SUN;
    /* 운행 종료 시간 */
    private String ORIGIN_END;
    private String ORIGIN_END_SAT;
    private String ORIGIN_END_SUN;

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
        return allo_Interval;
    }
    public BUSNODE setInterval(String interval) {
        this.allo_Interval = interval;
        return this;
    }

    /* 기존 */

    public BUSNODE() {}

    public BUSNODE(String busID, String busName, String interval, String allo_Interval_sat, String allo_interval_sun, String BUSSTOP_CNT, String ORIGIN_START,
                   String ORIGIN_START_SAT, String ORIGIN_START_SUN, String ORIGIN_END, String ORIGIN_END_SAT, String ORIGIN_END_SUN) {
        this.busID = busID;
        this.busName = busName;
        this.allo_Interval = interval;
        this.allo_Interval_sat = allo_Interval_sat;
        this.allo_Interval_sun = allo_interval_sun;
        this.BUSSTOP_CNT = BUSSTOP_CNT;
        this.ORIGIN_START = ORIGIN_START;
        this.ORIGIN_START_SAT = ORIGIN_START_SAT;
        this.ORIGIN_START_SUN = ORIGIN_START_SUN;
        this.ORIGIN_END = ORIGIN_END;
        this.ORIGIN_END_SAT = ORIGIN_END_SAT;
        this.ORIGIN_END_SUN = ORIGIN_END_SUN;
    }

    public String[] getToString() {
        String[] myStrings = new String[] {this.busID, this.busName, this.allo_Interval, this.allo_Interval_sat, this.allo_Interval_sun,
                this.BUSSTOP_CNT, this.ORIGIN_START, this.ORIGIN_START_SAT, this.ORIGIN_START_SUN, this.ORIGIN_END, this.ORIGIN_END_SAT,
                this.ORIGIN_END_SUN};
        return myStrings;
    }

    public BUSNODE(String busID, String busName, String interval) {
        this.busID = busID;
        this.busName = busName;
        this.allo_Interval = interval;
    }

    /* 추가 */




    public String getAllo_Interval_sat() {
        return allo_Interval_sat;
    }

    public BUSNODE setAllo_Interval_sat(String allo_Interval_sat) {
        this.allo_Interval_sat = allo_Interval_sat;
        return this;
    }

    public String getAllo_Interval_sun() {
        return allo_Interval_sun;
    }

    public BUSNODE setAllo_Interval_sun(String allo_Interval_sun) {
        this.allo_Interval_sun = allo_Interval_sun;
        return this;
    }

    public String getBUSSTOP_CNT() {
        return BUSSTOP_CNT;
    }

    public BUSNODE setBUSSTOP_CNT(String BUSSTOP_CNT) {
        this.BUSSTOP_CNT = BUSSTOP_CNT;
        return this;
    }

    public String getORIGIN_START() {
        return ORIGIN_START;
    }

    public BUSNODE setORIGIN_START(String ORIGIN_START) {
        this.ORIGIN_START = ORIGIN_START;
        return this;
    }

    public String getORIGIN_START_SAT() {
        return ORIGIN_START_SAT;
    }

    public BUSNODE setORIGIN_START_SAT(String ORIGIN_START_SAT) {
        this.ORIGIN_START_SAT = ORIGIN_START_SAT;
        return this;
    }

    public String getORIGIN_START_SUN() {
        return ORIGIN_START_SUN;
    }

    public BUSNODE setORIGIN_START_SUN(String ORIGIN_START_SUN) {
        this.ORIGIN_START_SUN = ORIGIN_START_SUN;
        return this;
    }

    public String getORIGIN_END() {
        return ORIGIN_END;
    }

    public BUSNODE setORIGIN_END(String ORIGIN_END) {
        this.ORIGIN_END = ORIGIN_END;
        return this;
    }

    public String getORIGIN_END_SAT() {
        return ORIGIN_END_SAT;
    }

    public BUSNODE setORIGIN_END_SAT(String ORIGIN_END_SAT) {
        this.ORIGIN_END_SAT = ORIGIN_END_SAT;
        return this;
    }

    public String getORIGIN_END_SUN() {
        return ORIGIN_END_SUN;
    }

    public BUSNODE setORIGIN_END_SUN(String ORIGIN_END_SUN) {
        this.ORIGIN_END_SUN = ORIGIN_END_SUN;
        return this;
    }
}