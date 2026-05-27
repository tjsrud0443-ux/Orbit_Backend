package com.study.app.domains.approval;

import java.util.List;

public class VacationSubmitRequestDTO {
	
	private String title;
	private String doc_type;
    private String users_id;
    
    private List<ApprovalLinesDTO> approvers; 
    private List<ApprovalCcDTO> referrers;
    
    private VacationDTO docData;
    
    public VacationSubmitRequestDTO() {}

	public VacationSubmitRequestDTO(String title, String doc_type, String users_id, List<ApprovalLinesDTO> approvers,
			List<ApprovalCcDTO> referrers, VacationDTO docData) {
		super();
		this.title = title;
		this.doc_type = doc_type;
		this.users_id = users_id;
		this.approvers = approvers;
		this.referrers = referrers;
		this.docData = docData;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDoc_type() {
		return doc_type;
	}

	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public List<ApprovalLinesDTO> getApprovers() {
		return approvers;
	}

	public void setApprovers(List<ApprovalLinesDTO> approvers) {
		this.approvers = approvers;
	}

	public List<ApprovalCcDTO> getReferrers() {
		return referrers;
	}

	public void setReferrers(List<ApprovalCcDTO> referrers) {
		this.referrers = referrers;
	}

	public VacationDTO getDocData() {
		return docData;
	}

	public void setDocData(VacationDTO docData) {
		this.docData = docData;
	}
}
