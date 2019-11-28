package com.example.busapp.BUSDTO;

import java.util.ArrayList;

public class BUSMAIN {
    protected static ArrayList<BUSNODE> list = new ArrayList<BUSNODE>();     // BUSNODE ArrayList 생성
    protected static ArrayList<BUSROUTE> route_end = new ArrayList<BUSROUTE>();     // BUSROUTE ArrayList 생성
    protected static ArrayList<BUSROUTE> route_start = new ArrayList<BUSROUTE>();     // BUSROUTE ArrayList 생성
    protected static ArrayList<String> where_now = new ArrayList<String>();     // BUSROUTE ArrayList 생성
    protected static ArrayList<BUSROUTE_NODE> route_node = new ArrayList<BUSROUTE_NODE>();

    public BUSMAIN() {
        try {
            list.clear();
            XMLParse.CreateBUSNODE();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<BUSNODE> lists() {
        return list;
    }

    public static ArrayList<BUSROUTE> route_lists(String str, int i) throws Exception  {
        route_end.clear();
        route_start.clear();
        where_now.clear();
        // 싹 다 초기화

        XMLParse xmlParse = new XMLParse();
        xmlParse.settingRoute(str);
        xmlParse.start();
        xmlParse.join();
        xmlParse.gc();
        if (i == 0) {
            return route_end;
        } else if (i == 1){
            return route_start;
        }
        return null;
    }

    public static ArrayList<String> where_list() {
        return where_now;
    }

    public static ArrayList<BUSROUTE_NODE> routenode_lists(String str) throws Exception {
        route_node.clear();
        XMLParse xmlParse = new XMLParse();
        xmlParse.settingRoutenode(str);
        xmlParse.start();
        xmlParse.join();
        xmlParse.gc();
        return route_node;
    }
}
