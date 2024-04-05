package com.example.mission1.model;

import com.example.mission1.dto.HistoryDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class History {
    Long id; // pk
    double x; // x좌표
    double y; // y좌표
    LocalDateTime date; // 검색한 날짜

    // dto로 변환하는 메소드
    public HistoryDto toDto() {
        return HistoryDto.builder()
                .id(id)
                .x(x)
                .y(y)
                .date(date)
                .build();
    }
}
