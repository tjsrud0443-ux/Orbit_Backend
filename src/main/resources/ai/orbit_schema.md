# Orbit AI DB View Schema

이 문서는 Spring Boot DB RAG에서 Orbit AI 챗봇이 사용할 Oracle DB VIEW 스키마 설명서이다.

## 공통 조회 규칙

- AI는 반드시 `V_AI_`로 시작하는 VIEW만 조회해야 한다.
- 원본 테이블 `USERS`, `SIGNUP`, `AI_MESSAGES`, `RAG_CHUNKS`, `RAG_DOCUMENTS` 등은 직접 조회하지 않는다.
- 비밀번호, 주민번호, 주소, 파일 UUID 같은 민감정보는 조회 대상이 아니다.
- 사용자가 "나", "내", "본인"이라고 말하면 현재 로그인 사용자 ID와 각 VIEW의 사용자 식별 컬럼을 비교한다.
- 현재 로그인 사용자 ID는 런타임에서 `'{loginId}'`로 제공된다고 가정한다.
- 직원 본인 기준 조회는 기본적으로 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 기안자 기준 조회는 `DRAFTER_ID = '{loginId}'` 조건을 사용한다.
- 결재자 기준 조회는 `APPROVER_ID = '{loginId}'` 조건을 사용한다.
- 작업 담당자 기준 조회는 `PIC_ID = '{loginId}'` 조건을 사용한다.
- 프로젝트 생성자 기준 조회는 `CREATOR_ID = '{loginId}'` 조건을 사용한다.

## Oracle 11g 날짜 조회 규칙

- 오늘: `TRUNC(SYSDATE)`
- 이번 주: `날짜컬럼 >= TRUNC(SYSDATE, 'IW') AND 날짜컬럼 < TRUNC(SYSDATE, 'IW') + 7`
- 이번 달: `>= TRUNC(SYSDATE, 'MM') AND < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)`
- 올해: `EXTRACT(YEAR FROM SYSDATE)`
- 날짜 컬럼에 시간이 포함될 수 있으므로 하루 단위 조회는 `TRUNC(날짜컬럼) = TRUNC(SYSDATE)` 형식을 우선 사용한다.

## 대표 SQL 예시

```sql
SELECT *
FROM V_AI_USERS
WHERE EMP_ID = '{loginId}';
```

```sql
SELECT *
FROM V_AI_ATTENDANCE
WHERE EMP_ID = '{loginId}'
  AND TRUNC(WORK_DATE) = TRUNC(SYSDATE);
```

```sql
SELECT *
FROM V_AI_SCHEDULES
WHERE START_DT >= TRUNC(SYSDATE, 'MM')
  AND START_DT < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)
  AND (EMP_ID = '{loginId}' OR IS_PUBLIC = 1);
```

## V_AI_USERS

### 사용 목적

직원 기본 정보 조회용 VIEW이다. 사용자의 기본 정보를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `EMP_ID` | 직원 로그인 ID |
| `EMP_NO` | 사번 |
| `EMP_NAME` | 직원명 |
| `EMP_EMAIL` | 이메일 |
| `EMP_PHONE` | 연락처 |
| `EMP_STATUS` | 재직 상태 `ACTIVE` / `INACTIVE` / `RETIRE` |
| `HIRE_DATE` | 입사일 |
| `DEPT_SEQ` | 부서 번호 |
| `DEPT_NAME` | 부서명 |
| `RANK_SEQ` | 직급 번호 |
| `RANK_NAME` | 직급명 |
| `RANK_ORDER` | 직급 정렬 순서 |

### 자주 들어올 사용자 질문 예시

- 내 기본 정보 알려줘.
- 내 부서와 직급이 뭐야?
- 특정 직원의 이메일을 알려줘.

### 조회 시 주의사항

- 본인 정보 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 직원 목록 조회 시 필요한 컬럼만 선택한다.
- 원본 사용자 테이블은 직접 조회하지 않는다.

## V_AI_ANNUAL_LEAVE

### 사용 목적

직원 연차 현황 조회용 VIEW이다. 사용자의 총 연차, 사용 연차, 잔여 연차를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `LEAVE_SEQ` | 연차 현황 고유 번호 |
| `EMP_ID` | 직원 로그인 ID |
| `EMP_NO` | 사번 |
| `EMP_NAME` | 직원명 |
| `DEPT_NAME` | 부서명 |
| `RANK_NAME` | 직급명 |
| `LEAVE_YEAR` | 연차 적용 연도 |
| `TOTAL_DAYS` | 해당 연도에 부여된 총 연차 일수 |
| `USED_DAYS` | 사용한 연차 일수 |
| `REMAINING_DAYS` | 남은 연차 일수 |

### 자주 들어올 사용자 질문 예시

- 내 남은 연차가 며칠이야?
- 올해 내가 사용한 연차 알려줘.
- 내 총 연차와 잔여 연차를 보여줘.

### 조회 시 주의사항

- 본인 연차 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 올해 연차는 `LEAVE_YEAR = EXTRACT(YEAR FROM SYSDATE)` 조건을 사용한다.

## V_AI_ATTENDANCE

### 사용 목적

직원 근태 기록 조회용 VIEW이다. 사용자의 출근 시간, 퇴근 시간, 총 근무 시간, 연장 근무 시간을 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `ATTENDANCE_SEQ` | 출퇴근 기록 고유 번호 |
| `EMP_ID` | 직원 로그인 ID |
| `EMP_NO` | 사번 |
| `EMP_NAME` | 직원명 |
| `DEPT_NAME` | 부서명 |
| `RANK_NAME` | 직급명 |
| `WORK_DATE` | 근무 날짜 |
| `CHECK_IN` | 출근 시간 |
| `CHECK_OUT` | 퇴근 시간 |
| `TOTAL_WORK_MIN` | 총 근무 시간, 분 단위 |
| `OVERTIME_MIN` | 연장 근무 시간, 분 단위 |

### 자주 들어올 사용자 질문 예시

- 오늘 내 출근 시간이 언제야?
- 이번 달 내 근태 기록 보여줘.
- 내가 연장 근무한 시간이 얼마나 돼?

### 조회 시 주의사항

- 본인 근태 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 오늘 근태는 `TRUNC(WORK_DATE) = TRUNC(SYSDATE)` 조건을 사용한다.
- 이번 달 근태는 `WORK_DATE >= TRUNC(SYSDATE, 'MM') AND WORK_DATE < ADD_MONTHS(TRUNC(SYSDATE, 'MM'), 1)` 조건을 사용한다.

## V_AI_CHECKOUT_REQUEST

### 사용 목적

퇴근 정정 신청 조회용 VIEW이다. 사용자가 퇴근 시간 정정을 신청한 내역과 처리 상태를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `CHECKOUT_SEQ` | 퇴근 정정 신청 고유 번호 |
| `ATTENDANCE_SEQ` | 연결된 출퇴근 기록 번호 |
| `EMP_ID` | 신청자 로그인 ID |
| `EMP_NO` | 신청자 사번 |
| `EMP_NAME` | 신청자 이름 |
| `DEPT_NAME` | 신청자 부서명 |
| `RANK_NAME` | 신청자 직급명 |
| `CHECKOUT_DATE` | 변경 희망 일시 |
| `REQ_CHECK_OUT` | 변경 요청한 최종 퇴근 일시 |
| `REASON` | 정정 신청 사유 |
| `REQUEST_STATUS` | 처리 상태. `PENDING`, `APPROVED`, `REJECTED` |
| `APPROVER_ID` | 처리 관리자 ID |
| `APPROVER_NAME` | 처리 관리자 이름 |
| `APPROVED_AT` | 승인 또는 반려 처리 일시 |

### 자주 들어올 사용자 질문 예시

- 내 퇴근 정정 신청 상태 알려줘.
- 반려된 퇴근 정정 신청이 있어?
- 이번 달 퇴근 정정 신청 내역 보여줘.

### 조회 시 주의사항

- 본인 신청 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 신청 상태별 조회는 `REQUEST_STATUS` 값을 사용한다.

## V_AI_OVERTIME_REQUEST

### 사용 목적

연장근무 신청 조회용 VIEW이다. 연장근무 신청 내역, 신청 시간, 처리 상태를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `OVERTIME_SEQ` | 연장근무 신청 고유 번호 |
| `ATTENDANCE_SEQ` | 연결된 출퇴근 기록 번호 |
| `EMP_ID` | 신청자 로그인 ID |
| `EMP_NO` | 신청자 사번 |
| `EMP_NAME` | 신청자 이름 |
| `DEPT_NAME` | 신청자 부서명 |
| `RANK_NAME` | 신청자 직급명 |
| `WORK_DATE` | 연장근무를 한 날짜 |
| `START_DT` | 연장근무 시작 시간 |
| `END_DT` | 연장근무 종료 시간 |
| `REQUEST_MIN` | 신청한 연장근무 시간 |
| `REASON` | 연장근무 신청 사유 |
| `REQUEST_STATUS` | 처리 상태. `PENDING`, `APPROVED`, `REJECTED` |
| `APPROVER_ID` | 처리 관리자 ID |
| `APPROVER_NAME` | 처리 관리자 이름 |
| `APPROVED_AT` | 승인 또는 반려 처리 일시 |

### 자주 들어올 사용자 질문 예시

- 내 연장근무 신청 상태 알려줘.
- 이번 달 승인된 연장근무 시간이 얼마나 돼?
- 대기 중인 연장근무 신청이 있어?

### 조회 시 주의사항

- 본인 신청 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 연장근무 날짜 기준 조회는 `WORK_DATE`를 사용한다.

## V_AI_DRAFT_DOCUMENTS

### 사용 목적

내가 올린 전자결재 문서 조회용 VIEW이다. 기안자가 본인이 작성한 문서의 결재 상태를 확인할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `DOC_SEQ` | 기안 문서 고유 번호 |
| `DOC_TITLE` | 기안 문서 제목 |
| `DOC_TYPE` | 문서 종류. `VACATION`, `PAYMENT`, `GENERAL`, `PURCHASE` |
| `DRAFTER_ID` | 기안자 로그인 ID |
| `DRAFTER_NO` | 기안자 사번 |
| `DRAFTER_NAME` | 기안자 이름 |
| `DRAFTER_DEPT_NAME` | 기안자 부서명 |
| `DRAFTER_RANK_NAME` | 기안자 직급명 |
| `DOC_STATUS` | 문서 결재 상태. `TEMP`, `DRAFT`, `IN_PROGRESS`, `APPROVED`, `REJECTED` |
| `REJECT_REASON` | 문서 반려 사유 |
| `IS_TEMP` | 임시 저장 여부. 일반 문서 = IS_TEMP 0, 임시저장 문서 = IS_TEMP 1 |
| `TEMP_EXPIRES_AT` | 임시 저장 만료 일시 |
| `CREATED_AT` | 기안 생성 일시 |
| `UPDATED_AT` | 기안 수정 일시 |

### 자주 들어올 사용자 질문 예시

- 내가 올린 결재 문서 상태 알려줘.
- 반려된 내 문서가 있어?
- 임시 저장한 문서 목록 보여줘.
- 곧 만료되는 임시 저장 문서가 있어?

### 조회 시 주의사항

- 본인이 작성한 문서는 `DRAFTER_ID = '{loginId}'` 조건을 사용한다.
- 문서 상태는 `DOC_STATUS` 값을 기준으로 필터링한다.
- 임시 저장 문서만 조회할 때는 `IS_TEMP = 1` 또는 `DOC_STATUS = 'TEMP'` 조건을 사용한다.
- 임시 저장 만료 여부는 `TEMP_EXPIRES_AT`을 기준으로 판단한다.
- 문서 상태는 `DOC_STATUS` 값을 기준으로 필터링한다.

## V_AI_APPROVAL_LINES

### 사용 목적

결재선 및 결재 대기 문서 조회용 VIEW이다. 사용자가 결재자로 지정된 문서, 결재 순서, 결재 처리 상태를 확인할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `LINE_SEQ` | 결재선 고유 번호 |
| `DOC_SEQ` | 기안 문서 고유 번호 |
| `DOC_TITLE` | 기안 문서 제목 |
| `DOC_TYPE` | 문서 종류. `VACATION`, `PAYMENT`, `GENERAL`, `PURCHASE` |
| `DOC_STATUS` | 전체 문서 결재 상태 |
| `DRAFTER_ID` | 기안자 로그인 ID |
| `DRAFTER_NAME` | 기안자 이름 |
| `DRAFTER_DEPT_NAME` | 기안자 부서명 |
| `APPROVER_ID` | 결재자 로그인 ID |
| `APPROVER_NAME` | 결재자 이름 |
| `APPROVER_DEPT_NAME` | 결재자 부서명 |
| `APPROVER_RANK_NAME` | 결재자 직급명 |
| `STEP_ORDER` | 결재 순서 |
| `APPROVAL_STATUS` | 해당 결재자의 결재 상태. `WAITING`, `IN_PROGRESS`, `APPROVED`, `REJECTED` |
| `HANDLE_AT` | 승인 또는 반려 처리 일시 |
| `REJECT_REASON` | 해당 결재자의 반려 사유 |
| `CREATED_AT` | 문서 생성 일시 |

### 자주 들어올 사용자 질문 예시

- 내가 결재해야 할 문서가 있어?
- 내 결재 대기 문서 목록 보여줘.
- 내가 반려한 결재 문서가 뭐야?

### 조회 시 주의사항

- 내가 결재자인 문서는 `APPROVER_ID = '{loginId}'` 조건을 사용한다.
- 대기 문서는 `APPROVAL_STATUS`가 `WAITING` 또는 `IN_PROGRESS`인지 확인한다.

## V_AI_SCHEDULES

### 사용 목적

캘린더 일정 조회용 VIEW이다. 개인 일정, 회사 일정, 프로젝트 일정, 회의 일정 등을 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `SCHEDULE_SEQ` | 일정 고유 번호 |
| `SCHEDULE_TITLE` | 일정 제목 |
| `SCHEDULE_TYPE` | 일정 유형. `PERSONAL`, `TODO`, `PROJECT`, `MEETING`, `COMPANY` |
| `EMP_ID` | 개인 일정 소유자 로그인 ID. 공용 일정이면 NULL 가능 |
| `EMP_NAME` | 개인 일정 소유자 이름 |
| `DEPT_NAME` | 개인 일정 소유자 부서명 |
| `START_DT` | 일정 시작 일시 |
| `END_DT` | 일정 종료 일시 |
| `SKED_REASON` | 일정 설명 |
| `IS_PUBLIC` | 공용 캘린더 여부. 1은 공용, 0은 개인 |
| `REF_SEQ` | 연결 대상 번호. 회의실 예약, 프로젝트 등과 연계될 수 있음 |
| `REF_TYPE` | 연결 대상 유형 |
| `CREATED_AT` | 일정 생성 일시 |

### 자주 들어올 사용자 질문 예시

- 오늘 내 일정 알려줘.
- 이번 달 회사 일정 보여줘.
- 내 회의 일정이 뭐가 있어?

### 조회 시 주의사항

- 개인 일정은 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 공용 일정은 `IS_PUBLIC = 1` 조건을 사용할 수 있다.
- 일정 날짜 범위는 `START_DT`, `END_DT`를 기준으로 판단한다.

## V_AI_ROOM_RSVN

### 사용 목적

회의실 예약 조회용 VIEW이다. 회의실 예약 현황, 예약자, 예약 시간, 회의실 위치를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `RSVN_SEQ` | 회의실 예약 고유 번호 |
| `ROOM_SEQ` | 회의실 고유 번호 |
| `ROOM_NAME` | 회의실 이름 |
| `MAX_PEOPLE` | 회의실 최대 수용 인원 |
| `ROOM_FLOOR` | 회의실 층수 |
| `EMP_ID` | 예약자 로그인 ID |
| `EMP_NAME` | 예약자 이름 |
| `DEPT_NAME` | 예약자 부서명 |
| `RSVN_TITLE` | 예약 제목 |
| `START_DT` | 예약 시작 일시 |
| `END_DT` | 예약 종료 일시 |
| `CREATED_AT` | 예약 생성 일시 |

### 자주 들어올 사용자 질문 예시

- 오늘 회의실 예약 현황 알려줘.
- 내가 예약한 회의실이 있어?
- 3층 회의실 예약 목록 보여줘.

### 조회 시 주의사항

- 내가 예약한 회의실은 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 예약 시간 조회는 `START_DT`, `END_DT`를 기준으로 한다.

## V_AI_PROJECTS

### 사용 목적

프로젝트 기본 정보 조회용 VIEW이다. 프로젝트명, 기간, 상태, 생성자를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `PROJECT_SEQ` | 프로젝트 고유 번호 |
| `PROJECT_NAME` | 프로젝트명 |
| `PROJECT_CONTENTS` | 프로젝트 설명 |
| `START_DATE` | 프로젝트 시작일 |
| `END_DATE` | 프로젝트 종료일 |
| `PROJECT_STATUS` | 프로젝트 진행 상태. `IN_PROGRESS`, `DONE` |
| `CREATOR_ID` | 프로젝트 생성자 로그인 ID |
| `CREATOR_NAME` | 프로젝트 생성자 이름 |
| `CREATOR_DEPT_NAME` | 프로젝트 생성자 부서명 |
| `CREATED_AT` | 프로젝트 생성 일시 |

### 자주 들어올 사용자 질문 예시

- 진행 중인 프로젝트 목록 보여줘.
- 특정 프로젝트 기간 알려줘.
- 내가 만든 프로젝트가 뭐야?

### 조회 시 주의사항

- 내가 생성한 프로젝트는 `CREATOR_ID = '{loginId}'` 조건을 사용한다.
- 참여 중인 프로젝트는 `V_AI_PROJECT_MEMBERS`를 사용한다.

## V_AI_PROJECT_MEMBERS

### 사용 목적

프로젝트 참여자 조회용 VIEW이다. 사용자가 참여 중인 프로젝트를 조회하거나, 특정 프로젝트의 참여자 목록을 확인할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `MEMBER_SEQ` | 프로젝트 참여자 고유 번호 |
| `PROJECT_SEQ` | 프로젝트 고유 번호 |
| `PROJECT_NAME` | 프로젝트명 |
| `PROJECT_STATUS` | 프로젝트 진행 상태 |
| `PROJECT_START_DATE` | 프로젝트 시작일 |
| `PROJECT_END_DATE` | 프로젝트 종료일 |
| `EMP_ID` | 참여자 로그인 ID |
| `EMP_NO` | 참여자 사번 |
| `EMP_NAME` | 참여자 이름 |
| `DEPT_NAME` | 참여자 부서명 |
| `RANK_NAME` | 참여자 직급명 |
| `JOIN_AT` | 프로젝트 참여 일시 |

### 자주 들어올 사용자 질문 예시

- 내가 참여 중인 프로젝트 알려줘.
- 이 프로젝트 참여자 목록 보여줘.
- 진행 중인 내 프로젝트가 뭐야?

### 조회 시 주의사항

- 내가 참여 중인 프로젝트는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 특정 프로젝트 참여자는 `PROJECT_SEQ` 또는 `PROJECT_NAME`으로 필터링한다.

## V_AI_KANBAN_TASK

### 사용 목적

칸반 작업 조회용 VIEW이다. 사용자가 맡은 작업, 프로젝트별 작업 상태, 우선순위, 마감일을 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `TASK_SEQ` | 칸반 작업 고유 번호 |
| `PROJECT_SEQ` | 프로젝트 고유 번호 |
| `PROJECT_NAME` | 프로젝트명 |
| `TASK_TITLE` | 작업 제목 |
| `TASK_CONTENT` | 작업 내용 |
| `TASK_STATUS` | 작업 상태. `TODO`, `DOING`, `DONE` |
| `PRIORITY` | 작업 우선순위. `LOW`, `MEDIUM`, `HIGH` |
| `PIC_ID` | 담당자 로그인 ID |
| `PIC_NAME` | 담당자 이름 |
| `PIC_DEPT_NAME` | 담당자 부서명 |
| `CREATOR_ID` | 작업 생성자 로그인 ID |
| `CREATOR_NAME` | 작업 생성자 이름 |
| `START_DATE` | 작업 시작일 |
| `DUE_DATE` | 작업 마감일 |
| `POSITION` | 칸반 내 카드 정렬 순서 |
| `CREATED_AT` | 작업 생성 일시 |
| `UPDATED_AT` | 작업 수정 일시 |

### 자주 들어올 사용자 질문 예시

- 내가 맡은 작업 알려줘.
- 이번 주 마감인 작업이 있어?
- 우선순위 높은 내 작업 보여줘.

### 조회 시 주의사항

- 내가 담당자인 작업은 `PIC_ID = '{loginId}'` 조건을 사용한다.
- 내가 만든 작업은 `CREATOR_ID = '{loginId}'` 조건을 사용한다.
- 마감일 조회는 `DUE_DATE`를 기준으로 한다.

## V_AI_SUPPLIES

### 사용 목적

비품 재고 조회용 VIEW이다. 비품명, 카테고리, 총 보유 수량, 현재 재고, 재고 상태를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `SUPPLY_SEQ` | 비품 고유 번호 |
| `SUPPLY_NAME` | 비품명 |
| `CATEGORY` | 비품 카테고리. 전자기기, 사무용품, 가구, 네트워크장비 등 |
| `SUPPLY_CODE` | 비품 코드 |
| `TOTAL_QTY` | 총 보유 수량 |
| `STOCK_QTY` | 현재 재고 수량 |
| `MIN_STOCK_QTY` | 최소 재고 기준 수량 |
| `SUPPLY_STATUS` | 재고 상태. `ENOUGH`, `LOW`, `EMPTY` |
| `CREATED_AT` | 비품 등록 일시 |

### 자주 들어올 사용자 질문 예시

- 노트북 재고가 있어?
- 재고 부족 비품 목록 보여줘.
- 사무용품 재고 현황 알려줘.

### 조회 시 주의사항

- 비품 재고 조회는 사용자 ID 조건이 필요하지 않을 수 있다.
- 재고 부족 조회는 `SUPPLY_STATUS = 'LOW'` 또는 `SUPPLY_STATUS = 'EMPTY'` 조건을 사용한다.

## V_AI_SUPPLY_REQUEST

### 사용 목적

비품 신청 조회용 VIEW이다. 사용자의 비품 신청 내역, 신청 품목, 신청 수량, 승인 상태를 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `REQ_SEQ` | 비품 신청 고유 번호 |
| `EMP_ID` | 신청자 로그인 ID |
| `EMP_NO` | 신청자 사번 |
| `EMP_NAME` | 신청자 이름 |
| `DEPT_NAME` | 신청자 부서명 |
| `RANK_NAME` | 신청자 직급명 |
| `REQ_DATE` | 신청 일자 |
| `REASON` | 신청 사유 |
| `REQUEST_STATUS` | 신청 처리 상태. `PENDING`, `APPROVED`, `REJECTED` |
| `ITEM_SEQ` | 신청 상세 품목 고유 번호 |
| `SUPPLY_SEQ` | 비품 고유 번호 |
| `SUPPLY_NAME` | 비품명 |
| `SUPPLY_CATEGORY` | 비품 카테고리 |
| `EA` | 신청 수량 |
| `USE_TYPE` | 사용 목적. `DEV`, `GENERAL`, `REPLACE` |

### 자주 들어올 사용자 질문 예시

- 내 비품 신청 상태 알려줘.
- 승인 대기 중인 비품 신청이 있어?
- 이번 달 내가 신청한 비품 목록 보여줘.

### 조회 시 주의사항

- 본인 비품 신청 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 신청 처리 상태는 `REQUEST_STATUS` 값을 사용한다.

## V_AI_NOTIFICATIONS

### 사용 목적

알림 조회용 VIEW이다. 사용자의 읽지 않은 알림, 결재/회의/프로젝트 관련 알림을 조회할 때 사용한다.

### 컬럼 목록과 설명

| 컬럼명 | 설명 |
|---|---|
| `NOTI_SEQ` | 알림 고유 번호 |
| `EMP_ID` | 알림 수신자 로그인 ID |
| `EMP_NAME` | 알림 수신자 이름 |
| `DEPT_NAME` | 알림 수신자 부서명 |
| `NOTI_TYPE` | 알림 종류. `APPROVAL`, `APPROVED`, `REJECTED`, `MEETING`, `PROJECT`, `TASK` |
| `CONTENT` | 알림 내용 |
| `REF_TYPE` | 연결 대상 타입. `APPROVAL`, `ROOM_RSVN`, `PROJECT`, `MEETING`, `TASK` 등 |
| `REF_SEQ` | 연결 대상 고유 번호 |
| `CREATED_AT` | 알림 생성 일시 |
| `READ_YN` | 읽음 여부. `Y`는 읽음, `N`은 안 읽음 |

### 자주 들어올 사용자 질문 예시

- 내 읽지 않은 알림 보여줘.
- 결재 관련 알림이 있어?
- 오늘 온 알림 알려줘.

### 조회 시 주의사항

- 본인 알림 조회는 `EMP_ID = '{loginId}'` 조건을 사용한다.
- 읽지 않은 알림은 `READ_YN = 'N'` 조건을 사용한다.
- 알림 생성일 조회는 `CREATED_AT`을 기준으로 한다.