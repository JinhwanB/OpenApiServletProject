# 내 위치 기반 공공 WIFI 제공 sevlet 서비스

## 프로젝트 기간

- 2024.03.25 ~ 2024.04.xx

## 주요 기능
1. 서울공공데이터 api를 활용하여 공공 와이파이 정보 가져오기 기능 구현
2. 입력한 위치 정보 기반 가까운 공공 와이파이 정보 20개 보여주는 기능 구현
3. 가까운 공공 과이파이 정보를 조회하는 시점에 입력한 위치 정보와 조회한 날짜를 저장한 히스토리를 DB에 저장 및 보여주는 기능 구현
4. 히스토리 삭제 기능 구현

## 사용 기술

![java](https://github.com/JinhwanB/OpenApiServletProject/assets/123534245/13d655a7-b9d8-48e3-a166-0c4f3d7830e6)
![html](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white)
![css](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white)
![javascript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)

- API
  - [서울공공데이터](https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do)<br><br>
- 개발 환경
  - inteliJ IDEA, Maven, JDBC
  - lombok 1.18.24, Tomcat 8.5, gson 2.9.0, okhttp3 4.9.3

## ERD

|                                                            전체 테이블                                                            |api 가져오기
|:----------------------------------------------------------------------------------------------------------------------------:|:-:
|                                          ![table](mission1%2Fdb%2FdatabaseERD.png)                                           |![api 가져오기](https://github.com/JinhwanB/OpenApiServletProject/assets/123534245/587b99c5-fa3d-43f8-9a8f-71a5f7c63255)
|                                                    위치 기반 근처 wifi 20개 가져오기                                                    |히스토리 가져오기 / 삭제
| ![근처 wifi 20개 가져오기](https://github.com/JinhwanB/OpenApiServletProject/assets/123534245/7b4c2889-88a3-4dd4-9bbb-dd93dab7ce01) |![히스토리 가져오기 / 삭제](https://github.com/JinhwanB/OpenApiServletProject/assets/123534245/d0cfa0d4-7df1-450b-b40a-780ffc8d910e)