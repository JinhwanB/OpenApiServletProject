drop table wifi;

create table wifi
(
    wifi_id             int          NOT NULL AUTO_INCREMENT primary key,
    X_SWIFI_MGR_NO      varchar(100) NOT NULL,
    X_SWIFI_WRDOFC      varchar(100) NOT NULL,
    X_SWIFI_MAIN_NM     varchar(100) NOT NULL,
    X_SWIFI_ADRES1      varchar(100) NOT NULL,
    X_SWIFI_ADRES2      varchar(100) NOT NULL,
    X_SWIFI_INSTL_FLOOR varchar(100) NOT NULL,
    X_SWIFI_INSTL_TY    varchar(100) NOT NULL,
    X_SWIFI_INSTL_MBY   varchar(100) NOT NULL,
    X_SWIFI_SVC_SE      varchar(100) NOT NULL,
    X_SWIFI_CMCWR       varchar(100) NOT NULL,
    X_SWIFI_CNSTC_YEAR  varchar(100) NOT NULL,
    X_SWIFI_INOUT_DOOR  varchar(100) NOT NULL,
    X_SWIFI_REMARS3     varchar(100) NOT NULL,
    LAT                 varchar(100) NOT NULL,
    LNT                 varchar(100) NOT NULL,
    WORK_DTTM           varchar(100) NOT NULL
);

drop table history;

create table history
(
    history_id int      not null auto_increment primary key,
    x          double   not null,
    y          double   not null,
    date       datetime not null
);