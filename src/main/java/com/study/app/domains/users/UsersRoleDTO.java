package com.study.app.domains.users;

public class UsersRoleDTO {
    private Long role_seq;
    private String users_id;
    private String auth_group;
    
    public UsersRoleDTO() {}
	public UsersRoleDTO(Long role_seq, String users_id, String auth_group) {
		super();
		this.role_seq = role_seq;
		this.users_id = users_id;
		this.auth_group = auth_group;
	}
	public Long getRole_seq() {
		return role_seq;
	}
	public void setRole_seq(Long role_seq) {
		this.role_seq = role_seq;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}
	public String getAuth_group() {
		return auth_group;
	}
	public void setAuth_group(String auth_group) {
		this.auth_group = auth_group;
	}
    
    
}
