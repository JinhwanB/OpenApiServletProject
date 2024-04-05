package com.example.mission1.service;

import com.example.mission1.exception.NullOfJsonArrayException;
import com.example.mission1.exception.PrimaryKeyException;
import com.example.mission1.model.History;
import com.example.mission1.model.WifiInfoDetail;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// db연결 및 해제, CRUD
public class MariaDBConnector {
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // db연결
    protected Connection getConnect() {
        Connection con = null;

        final String host = "127.0.0.1:3306/";
        final String dbName = "mission1";
        final String user = "root";
        final String pwd = "7819";

        final String url = "jdbc:mariadb://" + host + dbName;

        try {
            con = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            System.err.println("db 연결 실패!!" + e.getMessage());
        }

        return con;
    }

    // db연결 해제
    protected void close(ResultSet rs, PreparedStatement statement, Connection connection) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // wifi 테이블의 갯수를 구하는 메소드 (현재 테이블이 비어있는지 확인용)
    public long getWifiTableCnt() {
        Connection connection = getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        long result = 0;
        try {
            String selectWifiCnt = "select count(*)"
                    + " from wifi;";
            preparedStatement = connection.prepareStatement(selectWifiCnt);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                result = rs.getLong(1);
            }
        } catch (SQLException e) {
            System.out.println("wifi table count fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }

        return result;
    }

    // wifi 테이블의 전체를 가져오는 메소드
    public List<WifiInfoDetail> selectAllWifi() {
        Connection connection = getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        WifiInfoDetail wifiInfoDetail = null;
        List<WifiInfoDetail> list = new ArrayList<>();
        try {
            String selectWifiSql = "select X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM" +
                    " from wifi;";
            preparedStatement = connection.prepareStatement(selectWifiSql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                wifiInfoDetail = WifiInfoDetail.builder()
                        .X_SWIFI_MGR_NO(rs.getString(1))
                        .X_SWIFI_WRDOFC(rs.getString(2))
                        .X_SWIFI_MAIN_NM(rs.getString(3))
                        .X_SWIFI_ADRES1(rs.getString(4))
                        .X_SWIFI_ADRES2(rs.getString(5))
                        .X_SWIFI_INSTL_FLOOR(rs.getString(6))
                        .X_SWIFI_INSTL_TY(rs.getString(7))
                        .X_SWIFI_INSTL_MBY(rs.getString(8))
                        .X_SWIFI_SVC_SE(rs.getString(9))
                        .X_SWIFI_CMCWR(rs.getString(10))
                        .X_SWIFI_CNSTC_YEAR(rs.getString(11))
                        .X_SWIFI_INOUT_DOOR(rs.getString(12))
                        .X_SWIFI_REMARS3(rs.getString(13))
                        .LAT(rs.getString(14))
                        .LNT(rs.getString(15))
                        .WORK_DTTM(rs.getString(16))
                        .build();
                list.add(wifiInfoDetail);
            }
        } catch (SQLException e) {
            System.out.println("select all wifi fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }

        return list;
    }

    /**
     * 와이파이 전체 데이터 db에 저장하는 함수
     *
     * @param jsonArray - api에서 받아온 json데이터
     * @return 저장 완료 시 - true, 실패 시 exception
     */
    public boolean insertWifiToDb(JsonArray jsonArray) throws SQLException {
        if (jsonArray == null) {
            throw new NullOfJsonArrayException("jsonArray는 null일 수 없습니다.");
        }

        // 데이터를 1000개씩 한번에 쿼리를 보내서 저장한다. (속도 향상)
        Connection connection = getConnect();
        connection.setAutoCommit(false); // 데이터가 1000개가 되는 순간 commit하기 위해 autoCommit을 off한다.
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        try {
            String insertWifiSql = "insert into wifi(X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM)" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            preparedStatement = connection.prepareStatement(insertWifiSql);
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                preparedStatement.setString(1, jsonObject.get("X_SWIFI_MGR_NO").getAsString());
                preparedStatement.setString(2, jsonObject.get("X_SWIFI_WRDOFC").getAsString());
                preparedStatement.setString(3, jsonObject.get("X_SWIFI_MAIN_NM").getAsString());
                preparedStatement.setString(4, jsonObject.get("X_SWIFI_ADRES1").getAsString());
                preparedStatement.setString(5, jsonObject.get("X_SWIFI_WRDOFC").getAsString());
                preparedStatement.setString(6, jsonObject.get("X_SWIFI_INSTL_FLOOR").getAsString());
                preparedStatement.setString(7, jsonObject.get("X_SWIFI_INSTL_TY").getAsString());
                preparedStatement.setString(8, jsonObject.get("X_SWIFI_INSTL_MBY").getAsString());
                preparedStatement.setString(9, jsonObject.get("X_SWIFI_SVC_SE").getAsString());
                preparedStatement.setString(10, jsonObject.get("X_SWIFI_CMCWR").getAsString());
                preparedStatement.setString(11, jsonObject.get("X_SWIFI_CNSTC_YEAR").getAsString());
                preparedStatement.setString(12, jsonObject.get("X_SWIFI_INOUT_DOOR").getAsString());
                preparedStatement.setString(13, jsonObject.get("X_SWIFI_REMARS3").getAsString());
                preparedStatement.setString(14, jsonObject.get("LAT").getAsString());
                preparedStatement.setString(15, jsonObject.get("LNT").getAsString());
                preparedStatement.setString(16, jsonObject.get("WORK_DTTM").getAsString());

                preparedStatement.addBatch();
                preparedStatement.clearParameters();

                if ((i + 1) % 1000 == 0) {
                    preparedStatement.executeBatch();
                    connection.commit();
                }
            }

            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("insert wifi fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }

        return true;
    }

    // db를 조회하는 시점의 현재 시간을 가져오는 메소드
    public LocalDateTime getNowDate() {
        Connection connection = getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        LocalDateTime nowDate = null;
        try {
            String curDateSql = "select now();";
            preparedStatement = connection.prepareStatement(curDateSql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Timestamp cur = rs.getTimestamp(1);
                nowDate = cur.toLocalDateTime();
            }
        } catch (SQLException e) {
            System.out.println("get now date fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }

        return nowDate;
    }

    /**
     * 근처 와이파이 정보를 조회한 히스토리 db에 저장
     *
     * @param history 히스토리 정보가 담긴 객체(model)
     */
    public void insertHistoryToDb(History history) {
        Connection connection = getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            String insertHistorySql = "insert into history(x, y, date)" +
                    " values(?, ?, ?);";
            preparedStatement = connection.prepareStatement(insertHistorySql);
            preparedStatement.setDouble(1, history.getX());
            preparedStatement.setDouble(2, history.getY());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(history.getDate())); // LocalDateTime을 db에 저장하기 위해 Timestamp로 변환 후 저장
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("history save fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }
    }

    /**
     * 히스토리 삭제
     *
     * @param id 삭제할 히스토리 pk
     */
    public void deleteHistory(Long id) {
        if (id < 1) {
            throw new PrimaryKeyException("id값은 pk로 1 이상의 수 입니다. 현재 id = " + id);
        }

        Connection connection = getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            String deleteHistorySql = "delete from history" +
                    " where history_id = ?;";
            preparedStatement = connection.prepareStatement(deleteHistorySql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("delete history fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }
    }

    // 히스토리 테이블의 전체를 가져오는 메소드
    public List<History> selectAllHistory() {
        Connection connection = getConnect();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        List<History> historys = new ArrayList<>();
        try {
            String selectHistorySql = "select history_id, x, y, date" +
                    " from history;";
            preparedStatement = connection.prepareStatement(selectHistorySql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                History history = History.builder()
                        .id(rs.getLong(1))
                        .x(rs.getDouble(2))
                        .y(rs.getDouble(3))
                        .date(rs.getTimestamp(4).toLocalDateTime())
                        .build();
                historys.add(history);
            }
        } catch (SQLException e) {
            System.out.println("select all history fail!!" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs, preparedStatement, connection);
        }

        return historys;
    }
}
