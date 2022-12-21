#
# 지자체 교통 데이터 구축 및 연계

## 1. 프로젝트 구성
- 서버 OS : Ubuntu 18.04.4 LTS


- SW 목록

  |구분|SW명|버전|License|
  |:---:|:---:|:---:|:---|
  |Database|PostgreSQL|12.8|PostgreSQL License|
  |Message Broker|Apache Kafka|2.3.1|Apache2.0 License|
  |ETL|Apache NiFi|1.10.1|Apache2.0 License|


- 프로그램 목록

  |프로그램명|버전|설명|
  |:---:|:---:|:---:|
  |dj_etri_realtime_loader|0.1.0|Kafka로 전송된 데이터를 읽어서 대상 테이블을 확인한 다음 PostgreSQL 데이터베이스에 적재한다.|
  |dj_etri_file_loader|0.1.0|SFTP 서버로 전송된 데이터를 읽어서 대상 테이블을 확인한 다음 PostgreSQL 데이터베이스에 적재한다.|



- 데이터셋 목록

  |번호|테이블명|정의|
  |:---:|:---:|:---:|
  |1|CTB_CCTV_EQUIP_MST|CCTV 장비 정보|
  |2|CTB_CCTV_INSTL_MST|CCTV 설치 위치 정보|
  |3|CTB_CRRD_TRA_5MIN|교차로 교통량 5분|
  |4|CTB_CRSRD_MST|교차로 정보|
  |5|CTB_RSE_MST|RSE 정보|
  |6|CTB_RSE_TRA_DTL|RSE 교통량 상세|
  |7|CTB_VDS_MST|VDS 정보|
  |8|CTB_VDS_TRA_5MIN|VDS 교통량 5분|

### 1-1. SW 구성
#### 1-1-1. PostgreSQL

- 설치 경로

      /var/lib/postgresql/

- 서비스 실행

      $ sudo systemctl start postgresql

- 서비스 종료

      $ sudo systemctl stop postgresql

- 서비스 상태 확인

      $ sudo systemctl status postgresql

- 참고 자료

  https://www.postgresql.org/download/linux/ubuntu/

#### 1-1-2. Apache Kafka

- 설치 경로

      /home/dj_etri/pkgs/kafka

- 서비스 실행

      $ bash /home/dj_etri/pkgs/kafka-start.sh

- 서비스 종료

      $ bash /home/dj_etri/pkgs/kafka-stop.sh

- 서비스 상태 확인

      $ ps -ef | grep kafka.Kafka

- 참고 자료

  https://kafka.apache.org/quickstart

#### 1-1-3. Apache NiFi

- 설치 경로

      /home/dj_etri/pkgs/nifi/nifi-1.10.0/

- 서비스 실행

      $ sudo systemctl start nifi


- 서비스 종료

      $ sudo systemctl stop nifi

- 서비스 상태 확인

      $ sudo systemctl status nifi

- 참고 자료

  https://nifi.apache.org/docs.html

### 1-2. 프로그램 구성
#### 1-2-1. dj_etri_realtime_loader

- 설치 경로

      /home/dj_etri/apps/dj_etri_realtime_loader/

- 로그 경로

      /home/dj_etri/apps/dj_etri_realtime_loader/logs/

- 프로그램 실행

      $ ./bin/start_consumer.sh

- 프로그램 종료

      $ ./bin/stop_consumer.sh

- 프로그램 실행 확인

      $ ps -ef | grep consumer_main.py

- 프로그램 설정 정보
  ```ini
  [app]
  mapping.excel.file = table-mapping_loader.xlsx
  execute.db.write = true
  message.charset = UTF-8
  row.field.sep = |
  
  [db_writer]
  postgres.retry.limit = 2
  postgres.fail.retry.wait.time.ms = 10000
  
  postgres.database = "DatabaseName"
  postgres.user = dj_etri
  postgres.password = dj_etri123.$
  postgres.host = "Host"
  postgres.port = "Port"
  
  [kafka]
  consume.num.messages = 10000
  consume.timeout = 1.0
  consume.fail.retry.wait.time.ms = 10000
  
  consumer.bootstrap.servers = "Server":"Port"
  consumer.topics = dj-etri-ods
  consumer.group.id = dj_etri_realtime_loader
  consumer.enable.auto.commit = true
  consumer.auto.offset.reset = earliest
  consumer.fetch.max.bytes = 100000000
  
  message.max.bytes = 100000000
  socket.timeout.ms = 10000
  ```
  - app.mapping.excel.file : 수집 대상 소스 데이터의 설정 정보 파일명
  - app.execute.db.write : 데이터 DB 적재 여부
  - message.charset : 데이터의 charset
  - row.field.sep : 데이터의 필드 구분자
  - postgres.retry.limit : PostgreSQL 저장 실패에 대한 재시도 수
  - postgres.fail.retry.wait.time.ms : 재시도에 대한 wait time
  - postgres.[database|user|password|host|port] : PostgreSQL 저장을 위한 접속 정보
  - kafka 설정 : kafka 설정 문서 https://kafka.apache.org/documentation/ 페이지를 참고


#### 1-2-2. dj_etri_file_loader

- 설치 경로

      /home/dj_etri/apps/dj_etri_file_loader/

- 로그 경로

      /home/dj_etri/apps/dj_etri_file_loader/logs

- 프로그램 실행

      $ ./run.sh

- 프로그램 설정 정보
  ```yml
  logging:
    level:
      root: error
      com.hubilon: info
  
  dj_etri:
    datasource:
      driverClassName: org.postgresql.Driver
      url: jdbc:postgresql://"Host":"Port"/"DatabaseName"
      username: dj_etri
      password: dj_etri123.$
      maximumPoolSize: 5
      poolName: file-loader-cp
  
    fileloader:
      rootDirectory: /data/raw_data
      maxDepth4FindCsv: 5
      readCsvSeparator: PIPE
      csvQuote: NO_QUOTE
      csvFileEncode: UTF_8
      batchSize: 10000
      exceptionIfUnknownCsvFile: false
  
      # 서비스에 필요한 query 정의
  query:
        selectTargetDirectory: >
        selectLoadSeq:
        selectTableExists: >
        createBackupTable:
        insertCsvLoadInfo: >
        updateCsvLoadInfo: >
        updateCsvLoadInfo4Rollback: >
        updateCsvLoadInfo4FileHash: >
        selectAlreadyLoadInfo: >
        truncateTable: 
        insertAsSelect: 
        dropTable: 
        insertTable: 
        selectColumnInfo: >
  ```
  - dj_etri.fileloader.query.selectTargetDirectory: 작업에 대한 기본 디렉토리명, 기본 일자 정의
  - dj_etri.fileloader.query.selectLoadSeq: DB에 원시 데이터를 입력하는 작업에 대한 시쿼스 조회 쿼리
  - dj_etri.fileloader.query.selectTableExists: DB에 입력할 원시 데이터의 테이블 존재 여부 조회 쿼리 정의
  - dj_etri.fileloader.query.createBackupTable: DB에 입력할 원시 데이터의 백업 테이블 생성 쿼리 정의
  - dj_etri.fileloader.query.insertCsvLoadInfo: 원시 데이터 입력 작업에 대한 정보 입력 쿼리 정의
  - dj_etri.fileloader.query.updateCsvLoadInfo: 원시 데이터 입력 작업에 대한 상태와 입력 row 수 입력 쿼리 정의
  - dj_etri.fileloader.query.updateCsvLoadInfo4Rollback: 원시 데이터 입력 실패 시 상태 정보 입력 쿼리 정의
  - dj_etri.fileloader.query.updateCsvLoadInfo4FileHash: 원시 데이터 파일의 해쉬 정보 입력 쿼리 정의
  - dj_etri.fileloader.query.selectAlreadyLoadInfo: 원시 데이터 입력에 대한 상태 정보 조회 쿼리 정의
  - dj_etri.fileloader.query.truncateTable: 테이블 데이터 제거 쿼리 정의
  - dj_etri.fileloader.query.insertAsSelect: 데이터 조회 및 입력 쿼리 정의
  - dj_etri.fileloader.query.dropTable: 테이블 제거 쿼리 정의
  - dj_etri.fileloader.query.insertTable: 데이터 입력 쿼리 정의
  - dj_etri.fileloader.query.selectColumnInfo: 원시 데이터 테이블에 대한 컬럼 조회 쿼리 정의


