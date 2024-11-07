package io.devload.applicationinfoexample

import android.app.ActivityManager
import android.app.Application
import android.app.ApplicationExitInfo
import android.app.ApplicationStartInfo
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.function.Consumer

class MyApp : Application() {

    @RequiresApi(35)
    override fun onCreate() {
        super.onCreate()

        // ActivityManager 가져오기
        val activityManager = getSystemService(ActivityManager::class.java)

        // 애플리케이션 시작 정보 리스너 추가
        activityManager.addApplicationStartInfoCompletionListener(
            ContextCompat.getMainExecutor(this),
            Consumer { startInfo ->
                logApplicationStartInfo(startInfo)
            }
        )

        // 애플리케이션 종료 정보 로그
        logApplicationExitInfo(activityManager)
    }

    @RequiresApi(35)
    private fun logApplicationStartInfo(startInfo: ApplicationStartInfo) {
        //STARTUP_STATE_STARTED(0): 프로세스가 시작되었음을 나타냅니다.
        //STARTUP_STATE_ERROR(1): 프로세스를 시작하지 못했음을 나타냅니다.
        //STARTUP_STATE_FIRST_FRAME_DRAWN(2): 프로세스가 시작되어 첫 번째 프레임 제작이 완료되었음을 나타냅니다.

        if (startInfo.startupState == ApplicationStartInfo.STARTUP_STATE_FIRST_FRAME_DRAWN) {
            // 관련 정보 처리
            val reason = startInfo.reason // 앱이 시작된 이유
            val startType = startInfo.startType // 콜드, 웜 또는 핫 시작

            // 타임스탬프 가져오기 및 로그 출력
            val launchTimeMillis = startInfo.getStartupTimestamps()[ApplicationStartInfo.START_TIMESTAMP_LAUNCH]?.div(1_000_000)
            val forkTimeMillis = startInfo.getStartupTimestamps()[ApplicationStartInfo.START_TIMESTAMP_FORK]?.div(1_000_000)
            val appCreateTimeMillis = startInfo.getStartupTimestamps()[ApplicationStartInfo.START_TIMESTAMP_APPLICATION_ONCREATE]?.div(1_000_000)
            val appBindTimeMillis = startInfo.getStartupTimestamps()[ApplicationStartInfo.START_TIMESTAMP_BIND_APPLICATION]?.div(1_000_000)
            val appFirstFrameTimeMillis = startInfo.getStartupTimestamps()[ApplicationStartInfo.START_TIMESTAMP_FIRST_FRAME]?.div(1_000_000)
            val appFullyDrawnTimeMillis = startInfo.getStartupTimestamps()[ApplicationStartInfo.START_TIMESTAMP_FULLY_DRAWN]?.div(1_000_000)

            Log.i("ApplicationInfo", "시작 이유: ${getStartReasonMessage(reason)}")
            Log.i("ApplicationInfo", "시작 유형: ${startTypeToString(startType)}")
            Log.i("ApplicationInfo", "시작 시간 (ms): $launchTimeMillis")
            Log.i("ApplicationInfo", "포크 시간 (ms): $forkTimeMillis")
            Log.i("ApplicationInfo", "Application onCreate 시간 (ms): $appCreateTimeMillis")
            Log.i("ApplicationInfo", "Application 바인딩 시간 (ms): $appBindTimeMillis")
            Log.i("ApplicationInfo", "첫 번째 프레임 시간 (ms): $appFirstFrameTimeMillis")
            Log.i("ApplicationInfo", "완전히 그려진 시간 (ms): $appFullyDrawnTimeMillis")
        } else {
            Log.e("ApplicationInfo", "ApplicationStartInfo가 아직 완료되지 않았습니다.")
        }
    }

    @RequiresApi(30)
    private fun logApplicationExitInfo(activityManager: ActivityManager) {
        // [앱 종료 로그 획득 : getHistoricalProcessExitReasons] : [10 개 지정]
        val exitList: List<ApplicationExitInfo> = activityManager.getHistoricalProcessExitReasons(packageName, 0, 10)

        // [널 체크 수행]
        if (exitList.isNotEmpty()) {
            val exitInfo = exitList[0] // 첫 번째 항목만 가져오기

            // [ApplicationExitInfo 생성]
            Log.i("ApplicationInfo", "종료 프로세스 이름: ${exitInfo.processName}")
            Log.i("ApplicationInfo", "종료 프로세스 ID: ${exitInfo.pid}")
            Log.i("ApplicationInfo", "종료 날짜: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(Date(exitInfo.timestamp))}")

            // 종료 이유 로그 출력
            Log.i("ApplicationInfo", "종료 이유: ${getExitReasonMessage(exitInfo)}")
            Log.i("ApplicationInfo", "종료 설명: ${exitInfo.description}")
        } else {
            Log.e("ApplicationInfo", "종료 리스트를 가져오는 데 실패했습니다. 리스트가 비어 있습니다.")
        }
    }

    // 종료 이유에 대한 메시지를 반환하는 메서드
    private fun getExitReasonMessage(exitInfo: ApplicationExitInfo): String {
        return when (exitInfo.reason) {
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
            else -> "알 수 없는 종료 이유: ${exitInfo.reason}"
        }
    }

    // 시작 이유에 대한 메시지를 반환하는 메서드
    private fun getStartReasonMessage(reason: Int): String {
        return when (reason) {
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
            else -> "알 수 없음" // 예상치 못한 이유 코드 처리
        }
    }

    // 시작 유형 열거형 값을 문자열로 변환하는 도우미 함수
    fun startTypeToString(startType: Int): String {
        return when (startType) {
            ApplicationStartInfo.START_TYPE_UNSET -> "설정되지 않음"
            ApplicationStartInfo.START_TYPE_COLD -> "콜드"
            ApplicationStartInfo.START_TYPE_WARM -> "웜"
            ApplicationStartInfo.START_TYPE_HOT -> "핫"
            else -> "알 수 없음" // 예상치 못한 시작 유형 처리
        }
    }
}
