package com.example.mission1.service;

import com.example.mission1.dto.WifiInfoDetailDto;
import com.example.mission1.exception.FailPublicWifiToDbException;
import com.example.mission1.model.WifiInfoDetail;
import com.example.mission1.openApi.OpenApi;
import com.google.gson.JsonArray;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 와이파이 관련
public class WifiService extends MariaDBConnector {
    /**
     * 총 와이파이 갯수구하는 함수
     *
     * @return 총 화이파이 갯수
     */
    public int getWifiInfoCount() {
        OpenApi openApi = new OpenApi();
        return openApi.getOpenApi(1, 1)
                .get("TbPublicWifiInfo").getAsJsonObject()
                .get("list_total_count").getAsInt();
    }

    /**
     * 총 와이파이 갯수만큼 DB에 저장하는 함수
     *
     * @return 총 와이파이 갯수 (만약 이미 저장되있다면 -1)
     */
    public int getWifiInfoList() {
        long curWifiTableCount = getWifiTableCnt();
        if (curWifiTableCount != 0) { // 이미 한 번 실행하였음
            return -1;
        }

        int totalCount = getWifiInfoCount();
        int num = totalCount / 1000; // 1000개씩 불러오기 위해 나눈다. -> 1000개씩 데이터를 몇번 불러와야 하는지 구하는 변수 (1000개 초과로 불러오면 NullPointException 발생)
        int last = totalCount % 1000; // 1000개로 딱 나눠 떨어지지 않는 경우

        int start;
        int end;
        int count = 0; // 총 데이터 갯수
        for (int i = 0; i <= num; i++) {
            start = 1 + i * 1000;
            end = (1 + i) * 1000;
            if (last > 0 && i == num) { // 마지막 차례에 last 에 값이 있는 경우에는 end 는 last 갯수 만큼만 불러와야 한다.(예 : 총 1100개였다면 마지막에는 1001 ~ 1100 까지 불러와야 한다.)
                end = start + last - 1;
            }

            OpenApi openApi = new OpenApi();
            JsonArray jsonArray = openApi.getOpenApi(start, end)
                    .get("TbPublicWifiInfo").getAsJsonObject()
                    .get("row").getAsJsonArray();
            try {
                if (insertWifiToDb(jsonArray)) count += jsonArray.size();
                else throw new FailPublicWifiToDbException("와이파이 저장에 실패했습니다.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return count;
    }

    /**
     * 근처 와이파이 리스트 반환 메소드
     *
     * @param myLat 현재 검색된 lat위치
     * @param myLnt 현재 검색된 lnt위치
     * @return 전체 와이파이 리스트를 검색된 위치와 가까운 순으로 정렬한 dto리스트로 변경
     */
    public List<WifiInfoDetailDto> getNearWifi(double myLat, double myLnt) {
        List<WifiInfoDetailDto> result = new ArrayList<>();
        List<WifiInfoDetail> wifiInfoDetails = selectAllWifi();
        wifiInfoDetails.forEach(x -> result.add(x.toDto(myLat, myLnt))); // 검색된 위치와의 거리를 계산한 값을 저장한 dto로 변경

        HistoryService historyService = new HistoryService();
        historyService.addHistory(myLat, myLnt); // 현재 히스토리 저장

        return result.stream().sorted(Comparator.comparing(WifiInfoDetailDto::getDistance)).collect(Collectors.toList());
    }
}
