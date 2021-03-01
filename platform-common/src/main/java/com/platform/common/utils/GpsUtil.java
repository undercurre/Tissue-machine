package com.platform.common.utils;

/**
 *
 * 坐标系转换工具类
 */
public class GpsUtil {

private static double x_PI = 3.14159265358979324 * 3000.0 / 180.0;
    private static double PI = 3.14159265358979324;
    private static double a = 6378245.0;
    private static double ee = 0.00669342162296594323;

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     * @param bd_lon
     * @param bd_lat
     * @return Double[lon,lat]
     */
    public static Double[] BD09ToGCJ02(Double bd_lon,Double bd_lat){
        double x = bd_lon - 0.0065;
        double y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_PI);
        Double[] arr = new Double[2];
        arr[0] = z * Math.cos(theta);
        arr[1] = z * Math.sin(theta);
        return arr;
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     * @param gcj_lon
     * @param gcj_lat
     * @return Double[lon,lat]
     */
    public static Double[] GCJ02ToBD09(Double gcj_lon,Double gcj_lat){
        double z = Math.sqrt(gcj_lon * gcj_lon + gcj_lat * gcj_lat) + 0.00002 * Math.sin(gcj_lat * x_PI);
        double theta = Math.atan2(gcj_lat, gcj_lon) + 0.000003 * Math.cos(gcj_lon * x_PI);
        Double[] arr = new Double[2];
        arr[0] = z * Math.cos(theta) + 0.0065;
        arr[1] = z * Math.sin(theta) + 0.006;
        return arr;
    }

    /**
     * WGS84转GCJ02
     * @param wgs_lon
     * @param wgs_lat
     * @return Double[lon,lat]
     */
    public static Double[] WGS84ToGCJ02(Double wgs_lon,Double wgs_lat){
        if(outOfChina(wgs_lon, wgs_lat)){
            return new Double[]{wgs_lon,wgs_lat};
        }
        double dlat = transformlat(wgs_lon - 105.0, wgs_lat - 35.0);
        double dlng = transformlng(wgs_lon - 105.0, wgs_lat - 35.0);
        double radlat = wgs_lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        Double[] arr = new Double[2];
        arr[0] = wgs_lon + dlng;
        arr[1] = wgs_lat + dlat;
        return arr;
    }

    /**
     * GCJ02转WGS84
     * @param gcj_lon
     * @param gcj_lat
     * @return Double[lon,lat]
     */
    public static Double[] GCJ02ToWGS84(Double gcj_lon,Double gcj_lat){
        if(outOfChina(gcj_lon, gcj_lat)){
            return new Double[]{gcj_lon,gcj_lat};
        }
        double dlat = transformlat(gcj_lon - 105.0, gcj_lat - 35.0);
        double dlng = transformlng(gcj_lon - 105.0, gcj_lat - 35.0);
        double radlat = gcj_lat / 180.0 * PI;
        double magic = Math.sin(radlat);
        magic = 1 - ee * magic * magic;
        double sqrtmagic = Math.sqrt(magic);
        dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI);
        dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI);
        double mglat = gcj_lat + dlat;
        double mglng = gcj_lon + dlng;
        return new Double[]{gcj_lon * 2 - mglng, gcj_lat * 2 - mglat};
    }

    private static Double transformlat(double lng, double lat) {
        double ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static Double transformlng(double lng,double lat) {
        double ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
        ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    /**
     * outOfChina
     * @描述: 判断是否在国内，不在国内则不做偏移
     * @param lng
     * @param lat
     * @return {boolean}
     */
    private static boolean outOfChina(Double lng,Double lat) {
        return (lng < 72.004 || lng > 137.8347) || ((lat < 0.8293 || lat > 55.8271) || false);
    };

        private static double retain6(double num){
            String result = String .format("%.6f", num);
            return Double.valueOf(result);
        }
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double getDistance(double lat1, double lng1, double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    public static void main(String[] args) {

        double lon = Double.parseDouble("113.118423");
        double lat = Double.parseDouble("23.032507");
        Double[] doubles = BD09ToGCJ02(lon, lat);
        System.out.println(retain6(doubles[0]));
        System.out.println(retain6(doubles[1]));

    }
}
