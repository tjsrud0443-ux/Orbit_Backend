package com.study.app.domains.approval;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.app.domains.users.UsersDTO;
import com.study.app.domains.users.UsersService;

@RestController
@RequestMapping("/approval")
public class ApprovalController {

	@Autowired
	private UsersService usersServ;
	@Autowired
	private ApprovalService appServ;

	@GetMapping("all")
	public ResponseEntity<List<UsersDTO>> getAllEmployees(){
		List<UsersDTO> allEmployees = usersServ.getAllEmployees();
		return ResponseEntity.ok(allEmployees);
	}

	@PostMapping("submit/vacation")
	public ResponseEntity<Void> submitVacation(@RequestBody VacationSubmitRequestDTO dto){ 
		appServ.insertVacation(dto);
		return ResponseEntity.ok().build();
	}
}
