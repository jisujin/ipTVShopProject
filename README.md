# ipTVShopProject (인터넷 설치 가입신청 서비스)

4조 인터넷&인터넷TV 가입신청 서비스 CNA개발 실습을 위한 프로젝트 입니다.

# Table of contents

- [ipTVShopProject (인터넷 설치 가입신청 서비스)](#---)
  - [서비스 시나리오](#서비스-시나리오)
  - [체크포인트](#체크포인트)
  - [분석/설계](#분석설계)
  - [구현:](#구현-)
    - [DDD 의 적용](#ddd-의-적용)
    - [폴리글랏 퍼시스턴스](#폴리글랏-퍼시스턴스)
    - [폴리글랏 프로그래밍](#폴리글랏-프로그래밍)
    - [동기식 호출 과 Fallback 처리](#동기식-호출-과-Fallback-처리)
    - [비동기식 호출 과 Eventual Consistency](#비동기식-호출-과-Eventual-Consistency)
  - [운영](#운영)
    - [CI/CD 설정](#cicd설정)
    - [동기식 호출 / 서킷 브레이킹 / 장애격리](#동기식-호출-서킷-브레이킹-장애격리)
    - [오토스케일 아웃](#오토스케일-아웃)
    - [무정지 재배포](#무정지-재배포)
  - [신규 개발 조직의 추가](#신규-개발-조직의-추가)

# 서비스 시나리오

고객이 인터넷 가입신청을 하여 설치기사가 설치를 완료하거나, 가입 취소를 하였을 때 처리할 수 있도록 한다.

기능적 요구사항
1. 고객이 인터넷 가입신청을 한다.
1. 가입신청에 대한 접수가 되면, 고객서비스 담당자가 가입요청 지역 설치 기사를 배정한다.
1. 기사배정이 완료되면 해당지역 설치기사에게 설치 요청이 된다
1. 설치기사는 설치요청을 접수한다
1. 설치기사는 설치를 완료 후 설치 완료 처리를 한다.
1. 설치가 완료되면 인터넷가입신청이 완료 처리를 된다.
1. 고객이 가입 신청을 취소할 수 있다.
1. 가입신청이 취소되면 설치 취소된다.(설치취소 처리는 Req/Res 테스트를 위해 임의로 동기처리)
1. 고객서비스 담당자는 설치진행상태를 수시로 확인할 수 있다.

비기능적 요구사항
1. 트랜잭션
    1. 가입취소 신청은 설치취소가 동시 이루어 지도록 한다
1. 장애격리
    1. 인터넷 가입신청과 취소는 고객서비스 담당자의 접수, 설치 처리와 관계없이 항상 처리 가능하다.

1. 성능
    1. 고객서비스 담당자는 설치 진행상태를 수시로 확인하여 모니터링 한다.(CQRS)



# 체크포인트

- 분석 설계


  - 이벤트스토밍: 
    -    

  - 서브 도메인, 바운디드 컨텍스트 분리
    - 
  - 컨텍스트 매핑 / 이벤트 드리븐 아키텍처 
    - 

  - 헥사고날 아키텍처
    - 
    
- 구현
  - [DDD] 
    - 
    - [헥사고날 아키텍처] 
- 운영
  - SLA 준수
    - 
  - 무정지 운영 CI/CD (10)
    - 


# 분석/설계


## AS-IS 조직 (Horizontally-Aligned)
![image](https://user-images.githubusercontent.com/56263370/87296744-32433480-c542-11ea-9683-6b792f12cf55.png)  

## TO-BE 조직 (Vertically-Aligned)
![image](https://user-images.githubusercontent.com/56263370/87296805-4d15a900-c542-11ea-8fc2-15640ee62906.png)


## Event Storming 결과
* MSAEz 로 모델링한 이벤트스토밍 결과:  
  - http://msaez.io/#/storming/tumGnckjgrc4UVXq2EBT4EFYhnT2/mine/c03f2bb6625a2ed5bef6fcf78dde4b26/-MC01LpwJ3zz9a4MgvCj

### 이벤트 도출
![image](https://user-images.githubusercontent.com/56263370/87490118-ce268a80-c67f-11ea-9e0f-28725998ecf4.png)


### 부적격 이벤트 탈락
![image](https://user-images.githubusercontent.com/56263370/87490154-edbdb300-c67f-11ea-9923-d08c29203bc7.png)

    - 과정중 도출된 잘못된 도메인 이벤트들을 걸러내는 작업을 수행함
        - 중복/불필요, 처리 프로세스에 해당하는 이벤트 제거

### 폴리시 부착
![image](https://user-images.githubusercontent.com/56263370/87490165-f8784800-c67f-11ea-919b-edee122caf1f.png)


### 액터, 커맨드 부착하여 읽기 좋게
![image](https://user-images.githubusercontent.com/56263370/87490182-04fca080-c680-11ea-87e3-829b12b1df15.png)


### 어그리게잇으로 묶기
![image](https://user-images.githubusercontent.com/56263370/87490218-19409d80-c680-11ea-83de-464d8c9e1d47.png)

    -가입신청, 서비스관리센터, 설치 부분을 정의함

### 바운디드 컨텍스트로 묶기
![image](https://user-images.githubusercontent.com/56263370/87490225-2198d880-c680-11ea-9aaa-1210b8455719.png)


    - 도메인 서열 분리 : 가입신청 -> 서비스관리센터 -> 설치 순으로 정의
        


### 폴리시의 이동과 컨텍스트 매핑 (파란색점선은 Pub/Sub, 빨간색실선은 Req/Resp)
![image](https://user-images.githubusercontent.com/56263370/87490238-2e1d3100-c680-11ea-8d63-9b9626cf0fd4.png)


### 완성된 1차 모형
![image](https://user-images.githubusercontent.com/56263370/87490104-bfd86e80-c67f-11ea-95d9-8d6d41dd1eea.png)


    - View Model 추가
![image](https://user-images.githubusercontent.com/56263370/87490657-2ca03880-c681-11ea-9a88-0161e94cdf71.png)	

### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증
#### 시나리오 Coverage Check (1)
![image](https://user-images.githubusercontent.com/56263370/87491137-4a21d200-c682-11ea-9e3e-66540f9c0af8.png)

#### 시나리오 Coverage Check (2)
![image](https://user-images.githubusercontent.com/56263370/87491151-59088480-c682-11ea-86a6-53df001934d1.png)

#### 비기능 요구사항 coverage
![image](https://user-images.githubusercontent.com/56263370/87491175-66be0a00-c682-11ea-865f-9ee9e6113ed8.png)



## 헥사고날 아키텍처 다이어그램 도출
![image](https://user-images.githubusercontent.com/56263370/87491116-3d9d7980-c682-11ea-83b3-c9501c7875ba.png)


# 구현:
분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 8084 이다)

	cd Order
	mvn spring-boot:run

	cd ManagementCenter
	mvn spring-boot:run

	cd Installation
	mvn spring-boot:run

	cd orderstatus
	mvn spring-boot:run


## DDD 의 적용

- 각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언하였다: Order, ManagementCenter, Installation
- Installation(설치) 마이크로서비스 예시

	package ipTVShopProject;

	import javax.persistence.*;
	import org.springframework.beans.BeanUtils;
	import java.util.List;

	@Entity
	@Table(name="Installation_table")
	public class Installation {

		@Id
		@GeneratedValue(strategy=GenerationType.AUTO)
		private Long id;
		private Long engineerId;
		private String engineerName;
		private String installReservationDate;
		private String installCompleteDate;
		private Long orderId;
		private String status;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public Long getEngineerId() {
			return engineerId;
		}

		public void setEngineerId(Long engineerId) {
			this.engineerId = engineerId;
		}
		public String getEngineerName() {
			return engineerName;
		}

		public void setEngineerName(String engineerName) {
			this.engineerName = engineerName;
		}
		public String getInstallReservationDate() {
			return installReservationDate;
		}

		public void setInstallReservationDate(String installReservationDate) {
			this.installReservationDate = installReservationDate;
		}
		public String getInstallCompleteDate() {
			return installCompleteDate;
		}

		public void setInstallCompleteDate(String installCompleteDate) {
			this.installCompleteDate = installCompleteDate;
		}
		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}





## 폴리글랏 퍼시스턴스
My-SQL DB를 적용을 위해 다음 사항을 수정하여 적용

	pom.xml dependency 추가
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>

application.yml 파일 수정

	datasource:
	driver-class-name: com.mysql.cj.jdbc.Driver
	url: jdbc:mysql://localhost:3306/example?serverTimezone=UTC&characterEncoding=UTF-8
	username: root
	password: 


## 폴리글랏 프로그래밍
- Spring-Boot, JPA, My-SQL 적용(개발 테스트에서는 H2 DB사용)
- Java 외 다른 프로그램언어는 적용하지 않았음.


## 동기식 호출 과 Fallback 처리

분석단계에서의 조건 중 하나로 ManagementCenter에서 인터넷가입신청 취소를 요청 받으면, 설치진행상태를 확인하여 취소/취소불가 처리하는 부분을 동기식 호출하는 트랜잭션으로 처리하기로 하였다. 호출 프로토콜은 이미 앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다.

설치서비스를 호출하기 위하여 Stub과 (FeignClient) 를 이용하여 Service 대행 인터페이스 (Proxy) 를 구현
# (ManagementCenter) InstallationService.java

	package ipTVShopProject.external;


	@FeignClient(name="Installation", url="http://Installation:8080")
	public interface InstallationService {

		@RequestMapping(method= RequestMethod.PATCH, path="/installations")
		public void installationCancellation(@RequestBody Installation installation);

	}

인터넷가입 취소 요청(cancelRequest)을 받은 후, 처리하는 부분
# (Installation) InstallationController.java

	package ipTVShopProject;

	 @RestController
	 public class InstallationController {
	  @Autowired
	  InstallationRepository installationRepository;

	  @RequestMapping(method=RequestMethod.GET, path="/installations")
	  public String installationCancellation(@RequestBody Installation installation) {

	   Installation installationCancel = installationRepository.findByOrderId(installation.getOrderId());

	   if (installationCancel.getStatus().equals("INSTALLCOMPLETED")) { // 설치 완료상태일 때 거절
		   return "NotAccepted";
	   }
	   else {
		   installationCancel.setStatus("INSTALLATIONCANCELED");  // 설치 완료가 아닐 때 취소 허용
		   installationRepository.save(installationCancel);
		   return "Accepted";
	   }
	  }
	}


취소가능상태를 확인 하여 처리 후, (@PostUpdate) 자신의 설치 상태를 변경하도록 처리
# Installation.java (Entity)

    @PostUpdate
    public void onPostUpdate(){
        if(this.getStatus().equals("INSTALLCOMPLETED")) {
            InstallationCompleted installationCompleted = new InstallationCompleted();
            BeanUtils.copyProperties(this, installationCompleted);
            installationCompleted.publishAfterCommit();
        }

        if(this.getStatus().equals("INSTALLATIONCANCELED")) {
            InstallationCanceled installationCanceled = new InstallationCanceled();
            BeanUtils.copyProperties(this, installationCanceled);
            installationCanceled.publishAfterCommit();
        }


    }



## 비동기식 호출 / 시간적 디커플링 / 장애격리 / 최종 (Eventual) 일관성 테스트



# 운영

## CI/CD 설정


각 구현체들은 각자의 source repository 에 구성되었고, 사용한 CI/CD 플랫폼은 AWS를 사용하였으며, pipeline build script 는 각 프로젝트 폴더 이하에 cloudbuild.yml 에 포함되었다.
- https://github.com/ChaSang-geol/ipTVShopProject_gateway
- https://github.com/ChaSang-geol/ipTVShopProject_Order
- https://github.com/ChaSang-geol/ipTVShopProject_ManagementCenter
- https://github.com/ChaSang-geol/ipTVShopProject_Installation
- https://github.com/ChaSang-geol/ipTVShopProject_orderstatus

## 동기식 호출 / 서킷 브레이킹 / 장애격리

* 서킷 브레이킹 프레임워크의 선택: Spring FeignClient + Hystrix 옵션을 사용하여 구현함


```
# application.yml

hystrix:
  command:
    # 전역설정
    default:
      execution.isolation.thread.timeoutInMilliseconds: 610

```

- 피호출 서비스(설치 진행상태 확인:installation) 의 임의 부하 처리 - 400 밀리에서 증감 220 밀리 정도 왔다갔다 하게


* 부하테스터 siege 툴을 통한 서킷 브레이커 동작 확인:
- 동시사용자 100명
- 60초 동안 실시

- 운영시스템은 죽지 않고 지속적으로 CB 에 의하여 적절히 회로가 열림과 닫힘이 벌어지면서 자원을 보호하고 있음을 보여줌. 하지만, 63.55% 가 성공하였고, 46%가 실패했다는 것은 고객 사용성에 있어 좋지 않기 때문에 Retry 설정과 동적 Scale out (replica의 자동적 추가,HPA) 을 통하여 시스템을 확장 해주는 후속처리가 필요.

- Retry 의 설정 (istio)
- Availability 가 높아진 것을 확인 (siege)

### 오토스케일 아웃



## 무정지 재배포

* 먼저 무정지 재배포가 100% 되는 것인지 확인하기 위해서 Autoscaler 이나 CB 설정을 제거함

- seige 로 배포작업 직전에 워크로드를 모니터링 함.
- 새버전으로의 배포 시작
```
kubectl set image ...
```

- seige 의 화면으로 넘어가서 Availability 가 100% 미만으로 떨어졌는지 확인





