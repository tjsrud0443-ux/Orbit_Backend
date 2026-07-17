package com.study.app.domains.approval;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.study.app.domains.annualLeave.AnnualLeaveDAO;
import com.study.app.domains.file.FileService;
import com.study.app.domains.notifications.NotificationsDTO;
import com.study.app.domains.notifications.NotificationsService;
import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersService;

@Service
public class ApprovalService {

	@Autowired
	private ApprovalDAO dao;
	@Autowired
	private AnnualLeaveDAO annualDao;
	@Autowired
	private FileService fileServ;
	@Autowired
	private UsersService usersServ;
	@Autowired
	private NotificationsService notiServ;

	public List<UsersDTO> getAllEmployees() {
		return usersServ.getAllEmployees();
	}

	private void insertCommonApprovalData(DraftDocumentsDTO dto) {
		if(dto.getIs_temp() == 1) {
			LocalDate expireDate = LocalDate.now().plusDays(7);
			dto.setTemp_expires_at(expireDate.toString());
		}else {
			dto.setTemp_expires_at(null);
		}
		String currentStampSysname = usersServ.selectUserStampSysname(dto.getUsers_id());
		dto.setStamp_sysname((currentStampSysname != null && !currentStampSysname.isBlank()) 
				? currentStampSysname 
						: null);
		dao.insertDraftDocument(dto); 
		Long docSeq = dto.getDoc_seq();

		// 결재라인 정보 저장
		if(dto.getApprovers() != null) {
			for (ApprovalLinesDTO app : dto.getApprovers()) {
				app.setDoc_seq(docSeq);
				if (app.getStep_order() == 1) {
					app.setStatus("IN_PROGRESS"); 
					if (dto.getIs_temp() == 0) {
						NotificationsDTO noti = new NotificationsDTO();
						noti.setRef_seq(docSeq);
						noti.setUsers_id(app.getUsers_id());
						noti.setNoti_type("APPROVAL");
						noti.setContent("기안 결재 요청이 도착했습니다.");
						noti.setRef_type("APPROVAL");
						noti.setRead_yn("N");
						noti.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
						notiServ.insertNoti(noti);
					}
				} else {
					app.setStatus("WAITING");
				}
				dao.insertApprovalLines(app);
			}
		}

		// 참조자 정보 저장
		if(dto.getReferrers() != null) {
			for (ApprovalCcDTO ref : dto.getReferrers()) {
				ref.setDoc_seq(docSeq);
				dao.insertReferrers(ref);
			}
		}
	}

	// 휴가 신청서
	@Transactional
	public void insertVacation(VacationDTO dto, List<MultipartFile> files) {
		String users_id = dto.getUsers_id();
		Double used_days = dto.getDays();
		Map<String, Object> leaveData = annualDao.selectAnnualLeaveData(users_id);
		if (leaveData != null) {
			Double currentRemaining = Double.parseDouble(String.valueOf(leaveData.get("remaining_days")));

			if (used_days > currentRemaining) {
				throw new IllegalArgumentException("잔여 연차가 부족하여 휴가 신청서를 상신할 수 없습니다. \n(잔여: " 
						+ currentRemaining + "일)");
			}
			dto.setRemaining_days(currentRemaining);
		}
		insertCommonApprovalData(dto);
		dao.insertVacationDetail(dto);
		Long vac_seq = dto.getVac_seq();

		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				try {
					Map<String, String> upload = fileServ.upload(file);

					VacationAttachmentsDTO attach = new VacationAttachmentsDTO();
					attach.setVac_seq(vac_seq);
					attach.setOriname(upload.get("oriname"));
					attach.setSysname(upload.get("sysname"));

					dao.insertVacationAttachment(attach);

				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
				}
			}
		}
	}

	// 일반 품의서
	@Transactional
	public void insertGeneral(GeneralDTO dto, List<MultipartFile> files) {
		insertCommonApprovalData(dto);
		dao.insertGeneralDetail(dto);
		Long general_seq = dto.getGeneral_seq();

		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				try {
					Map<String, String> upload = fileServ.upload(file);

					GeneralAttachmentsDTO attach = new GeneralAttachmentsDTO();
					attach.setGeneral_seq(general_seq);
					attach.setOriname(upload.get("oriname"));
					attach.setSysname(upload.get("sysname"));

					dao.insertGeneralAttachment(attach);

				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
				}
			}
		}
	}

	// 지출 결의서
	@Transactional
	public void insertPayment(PaymentDTO dto, List<MultipartFile> files) {
		insertCommonApprovalData(dto);
		dao.insertPaymentDetail(dto);

		if(dto.getItems() != null) {
			for(int i = 0; i < dto.getItems().size(); i++) {
				PaymentItemsDTO item = dto.getItems().get(i);

				item.setPay_seq(dto.getPay_seq());
				item.setItem_order((Long.valueOf(i + 1)));

				if(files != null && i < files.size() && !files.get(i).isEmpty()) {
					try {
						Map<String, String> upload = fileServ.upload(files.get(i));

						item.setOriname(upload.get("oriname"));
						item.setSysname(upload.get("sysname"));

					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException("영수증 파일 업로드 중 오류 발생", e);
					}
				}
				dao.insertPaymentItem(item);
			}
		}
	}

	// 구매 신청서
	@Transactional
	public void insertPurchase(PurchaseDTO dto, List<MultipartFile> files) {
		insertCommonApprovalData(dto);
		dao.insertPurchaseDetail(dto);
		Long purchase_seq = dto.getPurchase_seq();

		if (dto.getItems() != null) {
			for (int i = 0; i < dto.getItems().size(); i++) {
				PurchaseItemsDTO item = dto.getItems().get(i);
				item.setPurchase_seq(purchase_seq);
				item.setItem_order((Long.valueOf(i + 1)));
				dao.insertPurchaseItem(item);
			}
		}

		if(files != null && !files.isEmpty()) {
			for(MultipartFile file : files) {
				try {
					Map<String, String> upload = fileServ.upload(file);

					PurchaseAttachmentsDTO attach = new PurchaseAttachmentsDTO();
					attach.setPurchase_seq(purchase_seq);
					attach.setOriname(upload.get("oriname"));
					attach.setSysname(upload.get("sysname"));

					dao.insertPurchaseAttachment(attach);

				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
				}
			}
		}
	}

	public Map<String, Object> getApprovalDetail(String doc_type, Long doc_seq) throws Exception {
		Map<String, Object> result = new HashMap<>();

		// 공통 정보 조회
		Map<String, Object> common = dao.selectCommonDetail(doc_seq);
		if (common == null) {
			throw new IllegalArgumentException("존재하지 않는 결재 문서입니다.");
		}
		result.put("common", common);

		// 결재자 및 참조자 리스트 조회
		List<Map<String, Object>> approvers = dao.selectApprovers(doc_seq);
		List<Map<String, Object>> referrers = dao.selectReferrers(doc_seq);
		result.put("approvers", approvers);
		result.put("referrers", referrers);

		// 하위 데이터 조회
		Map<String, Object> detail = null;

		switch (doc_type) {
		case "VACATION":
			detail = dao.selectVacationDetail(doc_seq);
			if (detail != null) {
				Object vacSeqObj = detail.get("vac_seq");
				Long vac_seq = Long.parseLong(String.valueOf(vacSeqObj));

				List<Map<String, Object>> attachments = dao.selectVacationAttachments(vac_seq);
				result.put("attachments", attachments);
			}
			break;

		case "GENERAL":
			detail = dao.selectGeneralDetail(doc_seq);
			if (detail != null) {
				Object generalSeqObj = detail.get("general_seq");
				Long general_seq = Long.parseLong(String.valueOf(generalSeqObj));

				List<Map<String, Object>> attachments = dao.selectGeneralAttachments(general_seq);
				result.put("attachments", attachments);
			}
			break;

		case "PAYMENT":
			detail = dao.selectPaymentDetail(doc_seq);
			if (detail != null) {
				Object paySeqObj = detail.get("pay_seq");
				Long pay_seq = Long.parseLong(String.valueOf(paySeqObj));

				List<Map<String, Object>> paymentItems = dao.selectPaymentItems(pay_seq);
				result.put("items", paymentItems);
			}
			break;

		case "PURCHASE":
			detail = dao.selectPurchaseDetail(doc_seq);
			if (detail != null) {
				Object purchaseSeqObj = detail.get("purchase_seq");
				Long purchase_seq = Long.parseLong(String.valueOf(purchaseSeqObj));

				List<Map<String, Object>> purchaseItems = dao.selectPurchaseItems(purchase_seq);
				result.put("items", purchaseItems);

				List<Map<String, Object>> attachments = dao.selectPurchaseAttachments(purchase_seq);
				result.put("attachments", attachments);
			}
			break;

		default:
			throw new IllegalArgumentException("잘못된 문서 타입입니다: " + doc_type);
		}

		result.put("detail", detail);

		return result;
	}

	@Transactional
	public void approveDraft(Long doc_seq, String loginId, String doc_type) {
		String currentStampSysname = usersServ.selectUserStampSysname(loginId);
		String safeStampSysname = (currentStampSysname != null && !currentStampSysname.isBlank())
				? currentStampSysname
						: null;

		Map<String, Object> currentLine = dao.selectMyApprovalLine(doc_seq, loginId);
		Long currentStepOrder = Long.parseLong(String.valueOf(currentLine.get("step_order")));

		dao.updateApprovalLineStatus(doc_seq, loginId, "APPROVED", safeStampSysname);

		Map<String, Object> nextLine = dao.selectNextApprovalLine(doc_seq, currentStepOrder);

		if (nextLine != null) {
			String nextUsersId = String.valueOf(nextLine.get("users_id"));
			dao.updateNextApprovalLineStatus(doc_seq, nextUsersId, "IN_PROGRESS");
			dao.updateDocumentStatus(doc_seq, "IN_PROGRESS");

			NotificationsDTO noti = new NotificationsDTO();
			noti.setRef_seq(doc_seq);
			noti.setUsers_id(nextUsersId);
			noti.setNoti_type("APPROVAL");
			noti.setContent("기안 결재 요청이 도착했습니다.");
			noti.setRef_type("APPROVAL");
			noti.setRead_yn("N");
			noti.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			notiServ.insertNoti(noti);
		} else {
			dao.updateDocumentStatus(doc_seq, "APPROVED");

			Map<String, Object> common = dao.selectCommonDetail(doc_seq);
			String drafterId = String.valueOf(common.get("users_id"));

			NotificationsDTO noti = new NotificationsDTO();
			noti.setRef_seq(doc_seq);
			noti.setUsers_id(drafterId);
			noti.setNoti_type("APPROVED");
			noti.setContent("기안이 승인되었습니다.");
			noti.setRef_type("APPROVAL");
			noti.setRead_yn("N");
			noti.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			notiServ.insertNoti(noti);

			if(doc_type.equals("VACATION")) {
				Map<String, Object> vac_info = dao.selectVacationDays(doc_seq);
				String users_id = String.valueOf(vac_info.get("users_id"));
				Double used_days = Double.parseDouble(String.valueOf(vac_info.get("days")));

				Map<String, Object> leaveData = annualDao.selectAnnualLeaveData(users_id);
				Double currentUsed = Double.parseDouble(String.valueOf(leaveData.get("used_days")));
				Double currentRemaining = Double.parseDouble(String.valueOf(leaveData.get("remaining_days"))) ;

				Double newUsed = currentUsed + used_days;
				Double newRemaining = currentRemaining - used_days;
				annualDao.updateAnnualLeaveUsedDays(users_id, newUsed, newRemaining);
			}

		}
	}

	@Transactional
	public void rejectApproval(Long doc_seq, String loginId, String reject_reason) {
		Map<String, Object> currentLine = dao.selectMyApprovalLine(doc_seq, loginId);
		Long currentStepOrder = Long.parseLong(String.valueOf(currentLine.get("step_order")));

		dao.updateApprovalLineStatusToReject(doc_seq, loginId, "REJECTED", reject_reason);

		dao.updateDocument(doc_seq, "REJECTED", reject_reason);

		Map<String, Object> common = dao.selectCommonDetail(doc_seq);
		String drafterId = String.valueOf(common.get("users_id"));

		NotificationsDTO noti = new NotificationsDTO();
		noti.setRef_seq(doc_seq);
		noti.setUsers_id(drafterId);
		noti.setNoti_type("REJECTED");
		noti.setContent("기안이 반려되었습니다.");
		noti.setRef_type("APPROVAL");
		noti.setRead_yn("N");
		noti.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		notiServ.insertNoti(noti);
	}

	private void updateCommonApprovalData(DraftDocumentsDTO dto) {
		if(dto.getIs_temp() == 1) {
			LocalDate expireDate = LocalDate.now().plusDays(7);
			dto.setTemp_expires_at(expireDate.toString());
		}else {
			dto.setStatus("DRAFT");
			dto.setIs_temp(Long.valueOf(0));
			dto.setTemp_expires_at(null);
		}
		String currentStampSysname = usersServ.selectUserStampSysname(dto.getUsers_id());
		dto.setStamp_sysname((currentStampSysname != null && !currentStampSysname.isBlank()) 
				? currentStampSysname 
						: null);
		dao.updateDraftDocument(dto);

		dao.deleteApprovalLines(dto.getDoc_seq());
		if(dto.getApprovers() != null) {
			for (int i = 0; i < dto.getApprovers().size(); i++) {
				ApprovalLinesDTO app = dto.getApprovers().get(i);
				app.setDoc_seq(dto.getDoc_seq());
				app.setStep_order((Long.valueOf(i + 1)));
				app.setStatus(i == 0 ? "IN_PROGRESS" : "WAITING");
				dao.insertApprovalLines(app);

				if (i == 0 && dto.getIs_temp() == 0) {
					NotificationsDTO noti = new NotificationsDTO();
					noti.setRef_seq(dto.getDoc_seq());
					noti.setUsers_id(app.getUsers_id());
					noti.setNoti_type("APPROVAL");
					noti.setContent("기안 결재 요청이 도착했습니다.");
					noti.setRef_type("APPROVAL");
					noti.setRead_yn("N");
					noti.setCreated_at(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
					notiServ.insertNoti(noti);
				}
			}
		}

		dao.deleteReferrers(dto.getDoc_seq());
		if(dto.getReferrers() != null) {
			for (ApprovalCcDTO ref : dto.getReferrers()) {
				ref.setDoc_seq(dto.getDoc_seq());
				dao.insertReferrers(ref);
			}
		}
	}

	@Transactional
	public void updateVacation(Long doc_seq, VacationDTO dto, List<MultipartFile> files) {
		String users_id = dto.getUsers_id();
		Double used_days = dto.getDays();
		Map<String, Object> leaveData = annualDao.selectAnnualLeaveData(users_id);
		if (leaveData != null) {
			Double currentRemaining = Double.parseDouble(String.valueOf(leaveData.get("remaining_days")));

			if (used_days > currentRemaining) {
				throw new IllegalArgumentException("잔여 연차가 부족하여 휴가 신청서를 상신할 수 없습니다. \n(잔여: " 
						+ currentRemaining + "일)");
			}
			dto.setRemaining_days(currentRemaining);
		}
		
		dto.setDoc_seq(doc_seq);
		updateCommonApprovalData(dto);
		Long vac_seq = dao.selectVac_seq(doc_seq);
		dto.setVac_seq(vac_seq);
		dao.updateVacationDetail(dto);

		List<String> keptSysnames = new ArrayList<>();
		if(dto.getAttachments() != null) {
			for(VacationAttachmentsDTO kept : dto.getAttachments()) {
				keptSysnames.add(kept.getSysname());
			}
		}

		List<String> oldSysnames = dao.selectVacationOldSysnames(vac_seq);
		for(String sysname : oldSysnames) {
			if(sysname != null && !keptSysnames.contains(sysname)) {
				fileServ.deleteFromGCS(sysname);
			}
		}

		dao.deleteVacationAttachments(vac_seq);

		if (dto.getAttachments() != null) {
			for (VacationAttachmentsDTO kept : dto.getAttachments()) {
				kept.setVac_seq(vac_seq);
				dao.insertVacationAttachment(kept);
			}
		}

		if(files != null) {
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
					try {
						Map<String, String> upload = fileServ.upload(file);

						VacationAttachmentsDTO attach = new VacationAttachmentsDTO();
						attach.setVac_seq(vac_seq);
						attach.setOriname(upload.get("oriname"));
						attach.setSysname(upload.get("sysname"));

						dao.insertVacationAttachment(attach);
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
					}
				}
			}
		}
	}

	@Transactional
	public void updateGeneral(Long doc_seq, GeneralDTO dto, List<MultipartFile> files) {
		dto.setDoc_seq(doc_seq);
		updateCommonApprovalData(dto);
		Long general_seq = dao.selectGeneral_seq(doc_seq);
		dto.setGeneral_seq(general_seq);
		dao.updateGeneralDetail(dto);

		List<String> keptSysnames = new ArrayList<>();
		if(dto.getAttachments() != null) {
			for(GeneralAttachmentsDTO kept : dto.getAttachments()) {
				keptSysnames.add(kept.getSysname());
			}
		}

		List<String> oldSysnames = dao.selectGeneralOldSysnames(general_seq);
		for(String sysname : oldSysnames) {
			if(sysname != null && !keptSysnames.contains(sysname)) {
				fileServ.deleteFromGCS(sysname);
			}
		}

		dao.deleteGeneralAttachments(general_seq);

		if (dto.getAttachments() != null) {
			for (GeneralAttachmentsDTO kept : dto.getAttachments()) {
				kept.setGeneral_seq(general_seq);
				dao.insertGeneralAttachment(kept);
			}
		}

		if(files != null) {
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
					try {
						Map<String, String> upload = fileServ.upload(file);

						GeneralAttachmentsDTO attach = new GeneralAttachmentsDTO();
						attach.setGeneral_seq(general_seq);
						attach.setOriname(upload.get("oriname"));
						attach.setSysname(upload.get("sysname"));

						dao.insertGeneralAttachment(attach);
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
					}
				}
			}
		}
	}

	@Transactional
	public void updatePayment(Long doc_seq, PaymentDTO dto, List<MultipartFile> files) {
		dto.setDoc_seq(doc_seq);
		updateCommonApprovalData(dto);

		Long pay_seq = dao.selectPay_seq(doc_seq);
		dto.setPay_seq(pay_seq);
		dao.updatePaymentDetail(dto);

		List<String> oldSysnames = dao.selectPayOldSysname(pay_seq);

		List<String> keptSysnames = new ArrayList<>();
		if(dto.getItems() != null) {
			for(PaymentItemsDTO item : dto.getItems()) {
				if(item.getSysname() != null) {
					keptSysnames.add(item.getSysname());
				}
			}
		}
		for(String sysname : oldSysnames) {
			if(sysname != null && !keptSysnames.contains(sysname)) { 
				fileServ.deleteFromGCS(sysname);
			}
		}

		dao.deletePaymentItems(pay_seq);

		if(dto.getItems() != null) {
			for(int i = 0; i < dto.getItems().size(); i++) {
				PaymentItemsDTO item = dto.getItems().get(i);
				item.setPay_seq(pay_seq);
				item.setItem_order((Long.valueOf(i + 1)));

				if(files != null && i < files.size() && !files.get(i).isEmpty()) {
					try {
						Map<String, String> upload = fileServ.upload(files.get(i));
						item.setOriname(upload.get("oriname"));
						item.setSysname(upload.get("sysname"));
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException("영수증 파일 업로드 중 오류 발생", e);
					}
				}
				dao.insertPaymentItem(item);
			}
		}
	}

	@Transactional
	public void updatePurchase(Long doc_seq, PurchaseDTO dto, List<MultipartFile> files) {
		dto.setDoc_seq(doc_seq);
		updateCommonApprovalData(dto);

		Long purchase_seq = dao.selectPurchase_seq(doc_seq);
		dto.setPurchase_seq(purchase_seq);
		dao.updatePurchaseDetail(dto);

		dao.deletePurchaseItems(purchase_seq);
		if (dto.getItems() != null) {
			for (int i = 0; i < dto.getItems().size(); i++) {
				PurchaseItemsDTO item = dto.getItems().get(i);
				item.setPurchase_seq(purchase_seq);
				item.setItem_order((Long.valueOf(i + 1)));
				dao.insertPurchaseItem(item);
			}
		}

		List<String> keptSysnames = new ArrayList<>();
		if(dto.getAttachments() != null) {
			for(PurchaseAttachmentsDTO kept : dto.getAttachments()) {
				keptSysnames.add(kept.getSysname());
			}
		}

		List<String> oldSysnames = dao.selectPurchaseOldSysnames(purchase_seq);
		for(String sysname : oldSysnames) {
			if(sysname != null && !keptSysnames.contains(sysname)) {
				fileServ.deleteFromGCS(sysname);
			}
		}

		dao.deletePurchaseAttachments(purchase_seq);

		if (dto.getAttachments() != null) {
			for (PurchaseAttachmentsDTO kept : dto.getAttachments()) {
				kept.setPurchase_seq(purchase_seq);
				dao.insertPurchaseAttachment(kept);
			}
		}

		if(files != null) {
			for(MultipartFile file : files) {
				if(!file.isEmpty()) {
					try {
						Map<String, String> upload = fileServ.upload(file);

						PurchaseAttachmentsDTO attach = new PurchaseAttachmentsDTO();
						attach.setPurchase_seq(purchase_seq);
						attach.setOriname(upload.get("oriname"));
						attach.setSysname(upload.get("sysname"));

						dao.insertPurchaseAttachment(attach);
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(file.getOriginalFilename() + " 파일 업로드 중 오류 발생", e);
					}
				}
			}
		}
	}

	public Map<String, Object> getApprovalHomeData(String users_id) {
		Map<String, Object> result = new HashMap<>();

		result.put("pendingCount", dao.countPendingApprovals(users_id)); // 결재 대기
		result.put("inProgressCount", dao.countInProgress(users_id)); // 진행 중
		result.put("approvedCount", dao.countApproved(users_id)); // 결재 완료
		result.put("rejectedCount", dao.countRejected(users_id)); // 반려
		result.put("recentDocs", dao.selectRecentDocs(users_id)); // 최근 문서 목록 5개

		return result;
	}

	public List<DraftDocumentsDTO> getCcWithLine(String loginId) {
		List<DraftDocumentsDTO> ccList = dao.getCcList(loginId);

		for(DraftDocumentsDTO draftDoc : ccList) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(draftDoc.getDoc_seq());

			draftDoc.setApprovers(lines);			
		}

		return ccList;
	}

	public Map<String, Object> getCcDocumentsByPage(String loginId, String status, Long cpage, 
			String keyword, String docType){
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);

		Map<String, Object> param = new HashMap<>();

		param.put("loginId", loginId);
		param.put("status", status);
		param.put("keyword", keyword);
		param.put("docType", docType);
		param.put("start", start);
		param.put("end", end);

		List<DraftDocumentsDTO> list = dao.getCcPage(param);

		for(DraftDocumentsDTO draftDoc : list) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(draftDoc.getDoc_seq());

			draftDoc.setApprovers(lines);			
		}

		int count = dao.getCcpageCount(param);
		Map<String, Object> result = new HashMap<>();

		result.put("list", list);
		result.put("count", count);

		return result;
	}

	public List<DraftDocumentsDTO> getMyDocWithLine(String loginId) {
		List<DraftDocumentsDTO> myDocList = dao.getMyDoc(loginId);

		for(DraftDocumentsDTO dto : myDocList) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(dto.getDoc_seq());
			dto.setApprovers(lines);
		}
		return myDocList;
	}

	public Map<String, Object> getMyDocumentsByPage(String loginId, String status, Long cpage,
			String keyword, String docType) {
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);

		Map<String, Object> param = new HashMap<>();

		param.put("loginId", loginId);
		param.put("status", status);
		param.put("keyword", keyword);
		param.put("docType", docType);
		param.put("start", start);
		param.put("end", end);

		List<DraftDocumentsDTO> list = dao.getMyDocPage(param);

		for(DraftDocumentsDTO dto : list) {
			List<ApprovalLinesDTO> lines = dao.getLinesBySeq(dto.getDoc_seq());

			dto.setApprovers(lines);
		}

		int count = dao.getMyDocPageCount(param);
		Map<String, Object> result = new HashMap<>();

		result.put("list", list);
		result.put("count", count);

		return result;
	}

	public List<DraftDocumentsDTO> getMydraftDoc(String loginId) {
		return dao.getMydraftDoc(loginId);
	}

	public Map<String, Object> getMyDoneDocByPage(String loginId, Long cpage, String keyword, String docType) {
		int start = (int)(cpage * 5 - 4);
		int end = (int)(cpage * 5);

		Map<String, Object> param = new HashMap<>();

		param.put("loginId", loginId);
		param.put("keyword", keyword);
		param.put("docType", docType);
		param.put("start", start);
		param.put("end", end);

		List<DraftDocumentsDTO> list = dao.getMyDoneDocByPage(param);

		int count = dao.getMyDoneDocCount(param);
		Map<String, Object> result = new HashMap<>();

		result.put("list", list);
		result.put("count", count);

		return result;
	}

	public List<DraftDocumentsDTO> getTempDoc(String loginId) {
		return dao.getTempDoc(loginId);
	}

	public List<DraftDocumentsDTO> tempList() {
		return dao.tempList();
	}

	@Transactional
	public void deleteDoc(Long doc_seq , String doc_type) {
		notiServ.deleteApprovalNotiBySeq(doc_seq);

		switch(doc_type) {
		case "VACATION" :
			deleteVacationDoc(doc_seq);
			break;

		case "GENERAL" :
			deleteGeneralDoc(doc_seq);
			break;

		case "PAYMENT" :
			deletePaymentDoc(doc_seq);
			break;

		case "PURCHASE" :
			deletePurchaseDoc(doc_seq);
			break;	

		default:
			throw new IllegalArgumentException("잘못된 문서 타입입니다: " + doc_type);
		}
	}

	// 공통 삭제 요소 (임시문서)
	private void deleteCommon(Long doc_seq) {
		dao.deleteAppLine(doc_seq);
		dao.deleteAppCc(doc_seq);
		dao.deleteDraftDoc(doc_seq);
	}

	// 휴가신청서 (임시문서)
	private void deleteVacationDoc(Long doc_seq) {
		Long vacSeq = dao.findVacSeq(doc_seq);
		List<String> file = dao.findVacAttach(vacSeq);
		for(String sysname : file) {
			if(sysname == null || sysname.isBlank()) {
				continue;
			}
			fileServ.deleteFromGCS(sysname);
		}
		dao.deleteVacAttach(doc_seq);
		dao.deleteVacDoc(doc_seq);
		deleteCommon(doc_seq);
	}

	// 일반품의서 (임시문서)
	private void deleteGeneralDoc(Long doc_seq) {
		Long genSeq = dao.findGenSeq(doc_seq);
		List<String> file = dao.findGenAttach(genSeq);
		for(String sysname : file) {
			if(sysname == null || sysname.isBlank()) {
				continue;
			}
			fileServ.deleteFromGCS(sysname);
		}
		dao.deleteGenAttach(doc_seq);
		dao.deleteGenDoc(doc_seq);
		deleteCommon(doc_seq);
	}

	// 지출결의서 (임시문서)
	private void deletePaymentDoc(Long doc_seq) {
		Long paySeq = dao.findPaySeq(doc_seq);
		List<String> file = dao.findPaymentFile(paySeq);

		for(String sysname : file) {
			if(sysname == null || sysname.isBlank()) {
				continue;
			}
			fileServ.deleteFromGCS(sysname);
		}
		dao.deletePayItem(doc_seq);
		dao.deletePayDoc(doc_seq);

		deleteCommon(doc_seq);
	}

	// 구매신청서 (임시문서)
	private void deletePurchaseDoc(Long doc_seq) {
		Long purchaseSeq = dao.findPurSeq(doc_seq);
		List<String> file = dao.findPurAttach(purchaseSeq);

		for(String sysname : file) {
			if(sysname == null || sysname.isBlank()) {
				continue;
			}
			fileServ.deleteFromGCS(sysname);
		}
		dao.deletePurAttach(doc_seq);
		dao.deletePurItem(doc_seq);
		dao.deletePurDoc(doc_seq);
		deleteCommon(doc_seq);
	}

	public List<UsersDTO> getTopReferrers(String loginId) {
		return dao.getTopReferrers(loginId);
	}

	public List<VacationTypesDTO> getAllVacationTypes() {
		return dao.getAllVacationTypes();
	}
}
