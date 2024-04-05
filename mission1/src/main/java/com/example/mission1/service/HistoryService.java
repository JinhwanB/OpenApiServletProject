package com.example.mission1.service;

import com.example.mission1.dto.HistoryDto;
import com.example.mission1.model.History;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 히스토리 관련
public class HistoryService extends MariaDBConnector {
    /**
     * 현재 검색한 히스토리 저장
     *
     * @param x 검색한 x좌표
     * @param y 검색한 y좌표
     */
    public void addHistory(double x, double y) {
        LocalDateTime nowDate = getNowDate(); // 검색한 시점의 현재 시간
        History entity = History.builder()
                .x(x)
                .y(y)
                .date(nowDate)
                .build();
        insertHistoryToDb(entity);
    }

    // 전체 히스토리를 가져와 dto로 변환
    public List<HistoryDto> getHistoryList() {
        List<History> historyList = selectAllHistory();
        List<HistoryDto> result = new ArrayList<>();
        historyList.forEach(x -> result.add(x.toDto()));

        return result;
    }

    /**
     * 히스토리 삭제
     *
     * @param id 삭제할 히스토리 pk
     */
    public void deleteOneHistory(Long id) {
        deleteHistory(id);
    }
}
