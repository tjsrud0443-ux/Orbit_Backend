package com.study.app.domains.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApprovalService {
	
	@Autowired
	private ApprovalDAO dao;
	
	@Transactional
	public void insertVacation(VacationSubmitRequestDTO dto) {
		// [단계 1] 공통 정보 저장 (테이블 1)
        // request에서 필요한 정보(docType, drafterSeq 등)를 꺼내 마스터 테이블에 넣습니다.
        DraftDocumentsDTO common = new DraftDocumentsDTO();
        common.setDoc_type(dto.getDoc_type());
        common.setUsers_id(dto.getUsers_id());
        common.setStatus("DRAFT");
        common.setTitle(dto.getTitle());
        
        // 💡 DAO의 메서드를 호출합니다.
        // 오라클 selectKey(BEFORE)에 의해 실행 후 master 객체 내부의 docSeq 필드에 시퀀스 값이 채워집니다.
        dao.insertDraftOfVacation(common); 
        
        Long generatedDocSeq = common.getDoc_seq(); // 채워진 PK(시퀀스) 꺼내기

        // [단계 2] 결재라인 정보 저장 (테이블 2)
        List<ApprovalLinesDTO> approvers = dto.getApprovers();
        for (ApprovalLinesDTO app : approvers) {
            app.setDoc_seq(generatedDocSeq); // 외래키 주입
            // 프론트에서 보낸 approval_order(결재순서)를 확인
            if (app.getStep_order() == 1) {
                app.setStatus("IN_PROGRESS"); 
            } else {
                app.setStatus("WAITING");
            }
            dao.insertApprovalLines(app);
        }

        // [단계 3] 참조자 정보 저장 (테이블 3)
        List<ApprovalCcDTO> referrers = dto.getReferrers();
        if (referrers != null) {
            for (ApprovalCcDTO ref : referrers) {
                ref.setDoc_seq(generatedDocSeq); // 외래키 주입
                dao.insertReferrers(ref); // 💡 DAO 호출
            }
        }

        // [단계 4] 휴가 상세 정보 저장 (테이블 4)
        VacationDTO vacation = dto.getDocData();
        vacation.setDoc_seq(generatedDocSeq); // 외래키 주입
        dao.insertVacationDetail(vacation); // 💡 DAO 호출
        
        // 만약 구매신청서라면 여기서 아이템 리스트(테이블 5)를 한 번 더 for문 돌려 넣으면 됩니다!
    
	}
}
