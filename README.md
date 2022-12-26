# placesearch

### 개발 환경 
* Framework 
  * spring boot 3.0.1
* gradle 7.6
* Java
  * corretto 17
* in-memory data space
  * embedded redis(local)
  * redis single(remote)

### 외부 라이브러리
* lombok
  * VO, DTO 내 mutator 생성 및 관리에 사용
* jackson
  * JSON 2 VO 간 객체 변환 기능 사용 (ObjectMapper)
* redis
  * embedded -> single redis -> redis cluster 로의 성능 확장 가능
  * redis cluster의 master-slave간 failover 정책을 통한 안전성 확보 가능
  * in-memory 저장소로 상대적으로 빠른 속도 보장 가능 
  * redis queue, pub-sub 구조 등 응용하기 좋은 인터페이스 존재  

### 기술 요구사항
#### 1. 동시성 이슈 처리 - 키워드 별로 검색된 횟수 증가 처리
    redis zIncrBy를 통한 증가 처리 - redis의 single thread 구조와 increment를 통한 중복 update 방지 처리  
#### 2. 카카오, 네이버 등 검색 API 제공자의 “다양한” 장애 발생 상황에 대한 고려
    HTTP 200 이외의 API 결과에는 의미있는 데이터가 없어 사용하지 않도록 처리 
#### 3. 구글 장소 검색 등 새로운 검색 API 제공자의 추가 시 변경 영역 최소화에 대한 고려
    PlaceServiceImpl->getPlaceList 하위 외부 자원 조회 기능 추가 (함수 내 주석 2번 로직 구간)
    placesearch->placeinfo 하위 google 관련 비지니스 로직 추가 
#### 4. 서비스 오류 및 장애 처리 방법에 대한 고려
#### 5. 반응성(Low Latency)
    장소 정보 api 호출 시, 키워드 처리 비용을 api 호출 시점에서 발생시키지 않음
#### 6. 확장성(Scalability)
    redis queue 스펙업을 통한 성능 향상 가능 
    scheduler에서 multithread 동작을 사용해도 동시성 이슈 없이 동일하게 동작 가능
#### 7. 가용성(Availability)
    redis queue를 통한 소모성 데이터 처리
      데이터 전달을 목표로하는 redis pub-sub, streams와는 달리, redis queue는 push/pop 인터페이스를 쓰는 list 단위 데이터 저장
#### 8. 지속적 유지 보수 및 확장에 용이한 아키텍처에 대한 설계
    Domain 단위 개발 진행 
      Root 도메인을 기준으로 한 도메인 간 상하 구조로 관리 