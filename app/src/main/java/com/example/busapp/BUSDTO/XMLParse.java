package com.example.busapp.BUSDTO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParse extends Thread {
    final static int ALL_ROUTE_REQPAGE = 3; // 전체 노선 기본정보 조회 reqPage 카운트
    String busroute;
    String routenode;


    private static String getTagValue(String tag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return null;
        return nValue.getNodeValue();
    }

    //BUSNODE 클래스 만들기 ( 점점 추가될 것 )
    static void CreateBUSNODE() throws Exception {
        new Thread(new Runnable() {
            @Override
            public synchronized void run() {

                try {
                    for (int i = 1; i <= ALL_ROUTE_REQPAGE; i++) { // 전체 노선 기본정보 조회
                        URL url;
                        String urls = "http://openapitraffic.daejeon.go.kr/api/rest/busRouteInfo/getRouteInfoAll"
                                + "?ServiceKey=AdPGBAHwJiyLiNyZq9h004Q3lTqMzq2gnw9XgB0dFIJ%2Bs2ZlIdiPGu3cOWWBdtH%2FwpmOLTBUcsKTipV%2Bd7yVaQ%3D%3D"
                                + "&reqPage=" + i; // url 설정
                        url = new URL(urls);
                        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
                        Document doc = dBuilder.parse(new InputSource(url.openStream()));
                        doc.getDocumentElement().normalize();
                        NodeList nList = doc.getElementsByTagName("itemList"); // <item> </item> 을 잘라서 nList에 넣는다.

                        for (int temp = 0; temp < nList.getLength(); temp++) {
                            Node nNode = nList.item(temp); //
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;

                                BUSMAIN.list.add(new BUSNODE()
                                        .setBusID(getTagValue("ROUTE_CD", eElement))
                                        .setBusName(getTagValue("ROUTE_NO", eElement))
                                        .setInterval(getTagValue("ALLO_INTERVAL", eElement))
                                        .setAllo_Interval_sat(getTagValue("ALLO_INTERVAL_SAT", eElement))
                                        .setAllo_Interval_sun(getTagValue("ALLO_INTERVAL_SUN", eElement))

                                        .setBUSSTOP_CNT(getTagValue("BUSSTOP_CNT", eElement))

                                        .setORIGIN_START(getTagValue("ORIGIN_START", eElement))
                                        .setORIGIN_START_SAT(getTagValue("ORIGIN_START_SAT", eElement))
                                        .setORIGIN_START_SUN(getTagValue("ORIGIN_START_SUN", eElement))

                                        .setORIGIN_END(getTagValue("ORIGIN_END", eElement))
                                        .setORIGIN_END_SAT(getTagValue("ORIGIN_END_SAT", eElement))
                                        .setORIGIN_END_SUN(getTagValue("ORIGIN_END_SUN", eElement))
                                );
                            }    // for end
                        }    // if end
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void run() {
        try {
            if (busroute != null) {
                FindRouteNode(busroute);
                WhereBus(busroute);
                this.busroute = null;
            } else if (routenode != null) {
                RouteStatus(routenode);
                this.routenode = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void settingRoute(String str) {
        this.busroute = str;
    }

    public void settingRoutenode(String str) {
        this.routenode = str;
    }

    public void FindRouteNode(String str) throws Exception {
        URL url;
        String urls = "http://openapitraffic.daejeon.go.kr/api/rest/busRouteInfo/getStaionByRoute"
                +"?ServiceKey=AdPGBAHwJiyLiNyZq9h004Q3lTqMzq2gnw9XgB0dFIJ%2Bs2ZlIdiPGu3cOWWBdtH%2FwpmOLTBUcsKTipV%2Bd7yVaQ%3D%3D"
                +"&busRouteId=" + str; // url 설정
        url = new URL(urls);
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = dBuilder.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("itemList"); // <item> </item> 을 잘라서 nList에 넣는다.

        int BUSSTOP_TP = 0; //버스가 오는 방향 (기점, 종점)
        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp); //
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;

                if (getTagValue("BUSSTOP_TP", eElement).equals("1")) {
                    BUSSTOP_TP = 1;
                } else if (getTagValue("BUSSTOP_TP", eElement).equals("2")) {
                    BUSSTOP_TP = 2;
                }

                if (BUSSTOP_TP == 1) {
                    BUSMAIN.route_end.add(new BUSROUTE()
                            .setRouteID(getTagValue("BUS_NODE_ID", eElement))
                            .setRouteName(getTagValue("BUSSTOP_NM", eElement)));
                } else if (BUSSTOP_TP == 2) {
                    BUSMAIN.route_start.add(new BUSROUTE()
                            .setRouteID(getTagValue("BUS_NODE_ID", eElement))
                            .setRouteName(getTagValue("BUSSTOP_NM", eElement)));
                }

            }
        }
    }

    public void WhereBus (String str) throws Exception {
        URL url;
        String urls = "http://openapitraffic.daejeon.go.kr/api/rest/busposinfo/getBusPosByRtid"
                +"?ServiceKey=AdPGBAHwJiyLiNyZq9h004Q3lTqMzq2gnw9XgB0dFIJ%2Bs2ZlIdiPGu3cOWWBdtH%2FwpmOLTBUcsKTipV%2Bd7yVaQ%3D%3D"
                +"&busRouteId=" + str; // url 설정
        url = new URL(urls);
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = (Document) dBuilder.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("itemList"); // <item> </item> 을 잘라서 nList에 넣는다.

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp); //
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;
                BUSMAIN.where_now.add(getTagValue("BUS_NODE_ID", eElement));
            }	// for end
        }	// if end
    }

    public void RouteStatus (String str) throws Exception {
        URL url;
        String urls = "http://openapitraffic.daejeon.go.kr/api/rest/arrive/getArrInfoByStopID"
                +"?ServiceKey=AdPGBAHwJiyLiNyZq9h004Q3lTqMzq2gnw9XgB0dFIJ%2Bs2ZlIdiPGu3cOWWBdtH%2FwpmOLTBUcsKTipV%2Bd7yVaQ%3D%3D"
                +"&BusStopID=" + str; // url 설정
        url = new URL(urls);
        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
        Document doc = (Document) dBuilder.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("itemList"); // <item> </item> 을 잘라서 nList에 넣는다.

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp); //
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;

                BUSMAIN.route_node.add(new BUSROUTE_NODE()
                        .setBusroute_ID(getTagValue("ROUTE_CD", eElement))
                        .setBusroute_Name(getTagValue("ROUTE_NO", eElement))
                        .setDestination(getTagValue("DESTINATION", eElement))
                        .setStatus_pos(getTagValue("STATUS_POS", eElement))
                        .setExtime_min(getTagValue("EXTIME_MIN", eElement))
                        .setExtime_sec(getTagValue("EXTIME_SEC", eElement))
                );

            }	// for end
        }	// if end
    }

    public void gc() {
        System.gc();
    }

}