package com.example.mission1.model;

import com.example.mission1.dto.WifiInfoDetailDto;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class WifiInfoDetail {
    private String X_SWIFI_MGR_NO; // 관리번호
    private String X_SWIFI_WRDOFC; // 자치구
    private String X_SWIFI_MAIN_NM; // 와이파이명
    private String X_SWIFI_ADRES1; // 도로명주소
    private String X_SWIFI_ADRES2; // 상세주소
    private String X_SWIFI_INSTL_FLOOR; // 설치위치(층)
    private String X_SWIFI_INSTL_TY; // 설치유형
    private String X_SWIFI_INSTL_MBY; // 설치기관
    private String X_SWIFI_SVC_SE; // 서비스구분
    private String X_SWIFI_CMCWR; // 망종류
    private String X_SWIFI_CNSTC_YEAR; // 설치년도
    private String X_SWIFI_INOUT_DOOR; // 실내외구분
    private String X_SWIFI_REMARS3; // wifi접속환경
    private String LAT; // x좌표
    private String LNT; // y좌표
    private String WORK_DTTM; // 작업날짜

    /**
     * dto로 변환하는 메소드
     *
     * @param x 검색한 x좌표
     * @param y 검색한 y좌표
     * @return dto
     */
    public WifiInfoDetailDto toDto(double x, double y) {
        return WifiInfoDetailDto.builder()
                .distance(distanceInKilometerByHaversine(Double.parseDouble(LAT), Double.parseDouble(LNT), x, y)) // 현재 검색된 위치와의 거리
                .X_SWIFI_MGR_NO(X_SWIFI_MGR_NO)
                .X_SWIFI_WRDOFC(X_SWIFI_WRDOFC)
                .X_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM)
                .X_SWIFI_ADRES1(X_SWIFI_ADRES1)
                .X_SWIFI_ADRES2(X_SWIFI_ADRES2)
                .X_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR)
                .X_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY)
                .X_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY)
                .X_SWIFI_SVC_SE(X_SWIFI_SVC_SE)
                .X_SWIFI_CMCWR(X_SWIFI_CMCWR)
                .X_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR)
                .X_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR)
                .X_SWIFI_REMARS3(X_SWIFI_REMARS3)
                .LAT(LAT)
                .LNT(LNT)
                .WORK_DTTM(WORK_DTTM)
                .build();
    }

    // 두 위치 사이의 거리를 구하는 하버사인 공식을 활용한 메소드
    private double distanceInKilometerByHaversine(double x1, double y1, double x2, double y2) {
        double theta = y1 - y2;
        double dist = Math.sin(deg2rad(x1)) * Math.sin(deg2rad(x2)) + Math.cos(deg2rad(x1)) * Math.cos(deg2rad(x2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return dist / 1000;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
