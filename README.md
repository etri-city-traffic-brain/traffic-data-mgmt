# traffic-data-mgmt

## 1. ETRI Traffic data REST API
본 프로젝트는 대전/세종 지역의 교통정보(edge, node, traffic, connection)를 REST API 기능을 통해 제공하고자 한다.

![스크린샷](https://github.com/etri-city-traffic-brain/traffic-data-mgmt/blob/master/ETRI_Traffic_data_REST_API_01.png?raw=true)


## 2. 프로젝트 구성

#### 2-1. 프로젝트 개발 환경

    - 서버 OS : CentOS Linux release 7.9.2009 (Core)
    - platform: spring boot 
    - 언어: java8+
    - IDE :Intellij
	   
#### 2-2. Database(Mysql5.7 이상) 설치

   - MySql다운로드
    
         $ wget https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
	 
   - MySql 설치

         $ sudo rpm -ivh mysql57-community-release-el7-11.noarch.rpm
		 
   - MySql 서버 설치
   
         $ sudo yum install mysql-server
		 
   - MySql 서비스 시작
   
         $ sudo systemctl start mysqld
		 
   - 환경옵션설정
   
         $ systemctl set-environment MYSQLD_OPTS="--skip-grant-tables"
		 
   - MySql 로그인
   
         $ mysql -u root -p        
		 
   - 사용자추가
   
          mysql> create user '사용자'@'localhost' identified by '비밀번호';
		  
   - 사용자권한부여
   
          mysql> grant all privileges on *.* to '사용자'@'%';       
		  
                   
   - 참고자료
   
          https://cherrypick.co.kr/how-to-install-mysql5-7-in-centos7/
          
            
#### 2-3. Tomcat8.5 설치

   - OpenJDK 설치
   
         $ yum install -y java-1.8.0-openjdk-devel.x86_64
		 
   - Apache Tomcat 다운로드
   
         $ wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.79/bin/apache-tomcat-8.5.79.zip -P /usr/local/lib
		 
   - 압축 해제
   
         $ cd /usr/local/lib
         $ unzip apache-tomcat-8.5.79.zip
		 
   - 서비스 환경 등록(catalna.sh)
   
          export CATALINA_HOME=/usr/local/lib/apache-tomcat-8.5.79
          export TOMCAT_HOME=/usr/local/lib/apache-tomcat-8.5.79
          export CATALINA_BASE=/usr/local/lib/apache-tomcat-8.5.79
          CATALINA_PID=/usr/local/lib/apache-tomcat-8.5.79/bin/tomcat.pid
		  
   - tomcat 실행
   
         $ cd /usr/local/lib/apache-tomcat-8.5.79/bin/
         $ ./start.sh
            
   - 참고자료
   
          https://www.infracody.com/2022/05/install-apache-tomcat-8-on-centos-7.html
	  
          
#### 2-4. API Application 등록 및 실행

   - war파일 생성
   - git에 업로드된 소스를 compile로 restapi.war 파일 생성
          
   - war파일 업로드
   
         $ /usr/local/lib/apache-tomcat-8.5.79/webapp/ 폴더에 restapi.war 파일 업로드
          
   - tomcat 재실행
   
         $ cd /usr/local/lib/apache-tomcat-8.5.79/bin/
         $ ./shutdown.sh
         $ ./start.sh
	 
	 
#### 2-5. 실행 확인
   - 웹 브라우저를 사용해서 http://서버IP/restapi/main 접속하여 UI를 확인한다.
       

