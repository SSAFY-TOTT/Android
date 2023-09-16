# 전세역전(TOTT) Android Application
TOTT 프로젝트 안드로이드 앱입니다. <br><br><br>
![main_logo](https://github.com/SSAFY-TOTT/Android/assets/60271512/c1afed6e-303d-4cd4-a0e6-3e719116011b)

<br><br>

# 아키텍처
## Clean Architecture
![image](https://github.com/SSAFY-TOTT/Android/assets/60271512/d417be32-a3e3-4347-9700-195fff939e6e)

- Clean Architecture를 사용해 각 계층의 역할을 분리했습니다.
  - ![image](https://github.com/SSAFY-TOTT/Android/assets/60271512/a88fa874-00cb-4525-993b-43df92aedf67)
- Flow를 적용해서 비동기 데이터 처리를 적용했습니다.
  - ![image](https://github.com/SSAFY-TOTT/Android/assets/60271512/f86dd38c-12e0-4447-a024-914522f6759c)
- 일부 화면에 MVI 패턴을 적용해서 사용자에게 오류를 표시했습니다.
  - ![image](https://github.com/SSAFY-TOTT/Android/assets/60271512/3c1859d4-bb6b-4ead-8f34-4da7c6b366bf)


<br><br>

## 사용 언어 및 프레임워크

![image](https://github.com/SSAFY-TOTT/Android/assets/60271512/f0abf89c-7ca5-443b-bf12-7badac1fa9ae)

- Hilt를 사용해 의존성 주입을 함으로써 비효율적인 코드의 반복을 줄였습니다.

<br><br>

# 개발 환경
- Android Studio `2022.3.1(Giraffe)`
- Target SDK `34`
- Minimum SDK `28`
- Kotlin `1.9.0`
- JVM `11`
- gradle `8.0`
