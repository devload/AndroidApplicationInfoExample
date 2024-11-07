# AndroidApplicationInfoExample

Android 30부터 `ApplicationExitInfo` , 35부터 `ApplicationStartInfo` 를 지원합니다

---

https://github.com/devload/AndroidApplicationInfoExample

ApplicationStartInfo를 통해 다음과 같은 데이터를 찍을 수 있습니다

```java
시작 이유: 런처
시작 유형: 콜드
시작 시간 (ms): 35515476
포크 시간 (ms): 35515672
Application onCreate 시간 (ms): 35516911
Application 바인딩 시간 (ms): 35516428
첫 번째 프레임 시간 (ms): 35517264
완전히 그려진 시간 (ms): null
```

시작 이유는 다음과 같이 리스트업되어 있어요

```java
ApplicationStartInfo.START_REASON_ALARM -> "알람"
ApplicationStartInfo.START_REASON_BACKUP -> "백업"
ApplicationStartInfo.START_REASON_BOOT_COMPLETE -> "부팅 완료"
ApplicationStartInfo.START_REASON_BROADCAST -> "브로드캐스트"
ApplicationStartInfo.START_REASON_CONTENT_PROVIDER -> "컨텐츠 제공자"
ApplicationStartInfo.START_REASON_JOB -> "작업"
ApplicationStartInfo.START_REASON_LAUNCHER -> "런처"
ApplicationStartInfo.START_REASON_LAUNCHER_RECENTS -> "런처 최근"
ApplicationStartInfo.START_REASON_OTHER -> "기타"
ApplicationStartInfo.START_REASON_PUSH -> "푸시"
ApplicationStartInfo.START_REASON_SERVICE -> "서비스"
ApplicationStartInfo.START_REASON_START_ACTIVITY -> "활동 시작"
```

ApplicationExitInfo를 통해 다음과 같은 데이터를 찍을 수 있습니다

```java
ProcessName : io.devload.applicationinfoexample
ProcessId : 25597
ExitDate : 2024-11-07 15:15:44:700
ExitReason : 응답이 없어(ANR) 애플리케이션 프로세스가 종료되었습니다
ExitDescription : user request after error: Input dispatching timed out (1311cc0 io.devload.applicationinfoexample/io.devload.applicationinfoexample.MainActivity (server) is not responding. Waited 5005ms for MotionEvent).
```

종료이유는 다음과 같이 리스트업되어 있습니다

```java
ApplicationExitInfo.REASON_ANR -> "응답이 없어(ANR) 애플리케이션 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_CRASH -> "소스코드에서 처리되지 않은 예외로 인해 애플리케이션 프로세스가 중단되었습니다"
ApplicationExitInfo.REASON_CRASH_NATIVE -> "네이티브 충돌로 인해 애플리케이션 프로세스가 중단되었습니다"
ApplicationExitInfo.REASON_DEPENDENCY_DIED -> "종속성이 사라져 애플리케이션 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_EXCESSIVE_RESOURCE_USAGE -> "과도한 리소스 사용으로 인해 시스템에서 응용 프로그램 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_EXIT_SELF -> "System.exit() 종료 코드 사용으로 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_FREEZER -> "응용 프로그램 프로세스가 정지된 동안 동기화 바인더 트랜잭션을 수신했기 때문에 App Freezer에 의해 종료되었습니다"
ApplicationExitInfo.REASON_INITIALIZATION_FAILURE -> "초기화 실패로 인해 애플리케이션 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_LOW_MEMORY -> "응용 프로그램 프로세스가 시스템 메모리 부족으로 인해 종료되었습니다"
ApplicationExitInfo.REASON_OTHER -> "앱에서 조치를 취할 수 없는 다양한 다른 이유로 인해 시스템에서 애플리케이션 프로세스를 종료했습니다 (ex: 시스템 업데이트)"
ApplicationExitInfo.REASON_PERMISSION_CHANGE -> "런타임 권한 변경으로 인해 애플리케이션 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_SIGNALED -> "OS 신호의 결과로 인해 애플리케이션 프로세스가 중단되었습니다"
ApplicationExitInfo.REASON_UNKNOWN -> "알 수 없는 이유로 프로세스가 중단되었습니다"
ApplicationExitInfo.REASON_USER_REQUESTED -> "사용자 요청으로 인해 애플리케이션 프로세스가 종료되었습니다"
ApplicationExitInfo.REASON_USER_STOPPED -> "여러 사용자가 있는 장치에서 실행 중인 사용자가 중지되었기 때문에 응용 프로그램 프로세스가 종료되었습니다"
```
