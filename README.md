# placesearch

### 개발 환경 
* framework 
  * spring boot 3.0.1
* build
  * gradle 7.6
* Java
  * corretto 17
* in-memory data space
  * embedded redis(local)
  * redis single(remote)

### 외부 라이브러리
* lombok
  * VO, DTO 관련 mutator 관리를 위해 사용
* jackson
  * JSON 2 VO 간 객체 변환 기능 사용 (ObjectMapper)
* redis
  * embedded -> single redis -> redis cluster 로의 성능 확장 가능
    * redis cluster 사용 시 master-slave간 failover 정책 사용 가능
  * in-memory 저장으로 상대적으로 빠른 속도 보장 가능 
  * redis queue, pub-sub 구조 등 응용하기 좋은 인터페이스 존재  
  * 사용 경험이 있어 러닝 커브가 적음 

### 기술 요구사항
#### 1. 동시성 이슈 처리 - 키워드 별로 검색된 횟수 증가 처리
    redis zIncrBy를 통한 증가 처리
      redis의 single thread 구조와 increment를 통한 중복 update 방지 처리  
#### 2. 카카오, 네이버 등 검색 API 제공자의 “다양한” 장애 발생 상황에 대한 고려
    HTTP 200 이외의 API 결과에는 의미있는 데이터가 없어 사용하지 않도록 처리 
#### 3. 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
    서비스 로직 내 open API 조회 기능 추가 
    신규 장소 검색 관련 도메인 및 데이터 관리용 기능 추가
    * Domain placesearch.placelist.placeinfo 하위 도메인 추가 후 작업 
#### 4. 서비스 오류 및 장애 처리 방법에 대한 고려
    오류 및 장애 발생 시 이력 손실 위험이 있는 검색어 대상 파일 로그 저장
      error 로그 수집 후 재처리 진행 가능  
#### 5. 반응성(Low Latency)
    장소 정보 api 호출 시, 키워드 처리 비용을 redis queue로 넘겨서 api 호출 시점에서 처리 비용을 발생시키지 않음
#### 6. 확장성(Scalability)
    redis queue 스펙업을 통한 성능 향상 가능 
    scheduler에서 multithread 동작을 사용해도 동시성 이슈 없이 동일하게 동작 가능
#### 7. 가용성(Availability)
    redis queue를 통한 소모성 데이터 처리
      데이터 전달을 목표로하는 redis pub-sub, streams와는 달리, redis queue는 push/pop 인터페이스를 쓰는 list 단위 데이터 저장
      redis 서버가 예기치 못한 이유로 종료되지 않는 한, list 타입으로 저장된 상태로 존재
    * 기능 요구 사항에 없어 미구현했지만 lpop된 queue 데이터는 rdb 이력 테이블에 저장되어야함 
#### 8. 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계
    Domain 단위 개발 진행 - Root 도메인을 기준으로 한 도메인 간 상하 구조로 관리 
      각 비지니스 로직은 관련 도메인 객체에 귀속
      각 프로젝트 고유의 서비스 로직과 infra-resource(DB, cache 등) 로직은 서비스 객체에 귀속 