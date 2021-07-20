# Spring Cloud로 개발하는 마이크로 서비스 애플리케이션(MSA) 강의 스터디 예제
* 강의: [강의소개](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4/)
* 강사: [이도원님](https://www.inflearn.com/users/@kenneth)

## 강의 내용
## 기술
| 유형 | 내용 |
------|------
| 서비스 디스커버리 | Netflix 유레카(Eureka) |
| API Gateway | SpringCloud Gateway <- Netflix Zuul |
| 로드밸런서 | SpringCloud Loadbalancer <- Netflix Ribbon |
| 구성 외부화 | SpringCloud Config -> SpringCloud Bux + AMQP |
| 마이크로서비스간 인증 | JWT |
| Circuit Breaker | Resilience4J-CircuitBreaker <- Netflix Hystrix |
| 분산 트레이싱 + 분산 추적 | SpringCloud Sleuth + twitter Zipkin |
| 모니터링 | Micrometer + Prometheus/Grafana |
| 빌드 및 배포 자동화 | Docker |
| 기타 | Kafka, FeignClient |

### 강의 목차
![001  Lec Table](https://user-images.githubusercontent.com/261011/126288598-49207848-bb20-4017-903f-ed39bb507f15.png)

### MSA Tech Stack
![002  MSA Tech Stack](https://user-images.githubusercontent.com/261011/126288609-861ce429-fa7b-4659-9ac2-3b81d4201a09.png)

### MSA Components
![003  MSA Components](https://user-images.githubusercontent.com/261011/126288611-6b548a4e-853d-45da-b456-fbe47fcee654.png)

### MSA Ecosystems
![004  MSA Main Stack](https://user-images.githubusercontent.com/261011/126288612-8afbb70f-6a49-4ceb-a4e2-ce0f7acb2d2b.png)

### SpringCloud Ecosystems
![005  Spring Cloud Stack](https://user-images.githubusercontent.com/261011/126288615-934ab179-643b-4fa8-809f-8d54760969a8.png)

### SpringCloud Gateway: Filter
![](https://user-images.githubusercontent.com/261011/126288621-07e08d54-cc18-404f-8cbe-8e9f34580272.png)

### 샘플 시스템 구조
![](https://user-images.githubusercontent.com/261011/126288625-c5cd6cd4-f729-4a44-b38e-67e05cbe5645.png)

### SpringCloud Bus
![](https://user-images.githubusercontent.com/261011/126288632-5fd8142b-eb82-4fb0-bfd1-185b9ca6da3c.png)

### SpringCloud Config: Encrypt/Decrypt
![](https://user-images.githubusercontent.com/261011/126288633-9cc4e187-4214-4ddc-a481-e8f9bd93e302.png)

### Kafa - Meassage Based API
![](https://user-images.githubusercontent.com/261011/126288636-7551f1c9-7e83-4bb4-8d3b-31c908f833ca.png)

### CircuitBreaker
![](https://user-images.githubusercontent.com/261011/126288637-37c6e24a-a2cc-4450-af25-ee909797bb67.png)

### Sleuth + Zipkin
![](https://user-images.githubusercontent.com/261011/126288639-3aeedbb5-ed21-4298-8bc4-eac1c3f2c82b.png)

### Hystrix Dashboard + Turbinserver (Deprecated)
![](https://user-images.githubusercontent.com/261011/126288640-e20c04d3-a8ef-4daa-a156-b05a825a3800.png) 

### CQRS Example
![](https://user-images.githubusercontent.com/261011/126288642-0f836e2b-c22b-4a55-8b80-11f3d07fdf22.png) 

### TX: Saga Pattern - Choreography based
![](https://user-images.githubusercontent.com/261011/126288644-82ac4ff8-f606-4d4a-86af-cd8b8c1d89dd.png)

### TX: Saga Pattern - Orchestration based
![](https://user-images.githubusercontent.com/261011/126288647-cac87fa5-cc7c-4d51-9e1f-6e755261864d.png)
