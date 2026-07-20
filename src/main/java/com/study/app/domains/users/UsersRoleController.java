package com.study.app.domains.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usersRole")
public class UsersRoleController {

	@Autowired
	private UsersRoleService usersRoleServ;
	
	// 특정 사용자의 권한 목록 조회
    @GetMapping("/{users_id}/roles")
    public ResponseEntity<List<String>> getUserRoles(
            @PathVariable("users_id") String usersId,
            @RequestAttribute String loginId) {

    	if (!usersRoleServ.isHrAuthorized(loginId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    	
        List<String> roles = usersRoleServ.getUserRoles(usersId);
        return ResponseEntity.ok(roles);
    }

    // 특정 사용자의 권한 목록 수정 (삭제 후 재삽입)
    @PutMapping("/{users_id}/roles")
    public ResponseEntity<Void> updateUserRoles(
            @PathVariable("users_id") String usersId,
            @RequestBody List<String> roles,
            @RequestAttribute String loginId) {

        if (!usersRoleServ.isHrAuthorized(loginId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        usersRoleServ.updateUserRoles(usersId, roles);
        return ResponseEntity.ok().build();
    }
}
