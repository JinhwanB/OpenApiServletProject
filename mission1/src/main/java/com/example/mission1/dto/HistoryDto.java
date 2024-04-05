package com.example.mission1.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class HistoryDto {
    Long id; // pk
    double x; // x좌표
    double y; // y좌표
    LocalDateTime date; // 검색한 날짜
}
