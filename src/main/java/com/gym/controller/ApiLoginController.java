package com.gym.controller;


import com.gym.pojo.Admin;
import com.gym.service.AdminService;
import com.gym.service.EmployeeService;
import com.gym.service.EquipmentService;
import com.gym.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiLoginController {

    private static final String SESSION_ADMIN = "admin";
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final MemberService memberService;
    private final EquipmentService equipmentService;

    public ApiLoginController(AdminService adminService,
                              EmployeeService employeeService,
                              MemberService memberService,
                              EquipmentService equipmentService) {
        this.adminService = adminService;
        this.employeeService = employeeService;
        this.memberService = memberService;
        this.equipmentService = equipmentService;
    }

    @PostMapping("/adminLogin")
    public ResponseEntity<Map<String, Object>> adminLogin(Admin admin, HttpSession session){
        Admin loggedIn = adminService.adminLogin(admin);
        if (loggedIn == null){
            return unauthorized("Either account or password is incorrect");
        }

        putAdminMainDataInSession(session, loggedIn);
        return ResponseEntity.ok(SingleSuccess());
    }

    @GetMapping("/toAdminMain")
    public ResponseEntity<Map<String, Object>> toAdminMain(HttpSession session){
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("memberTotal", session.getAttribute("memberTotal"));
        body.put("employeeTotal", session.getAttribute("employeeTotal"));
        body.put("equipmentTotal", session.getAttribute("equipmentTotal"));
        body.put("humanTotal", session.getAttribute("humanTotal"));
        return ResponseEntity.ok(body);

    }


    private static Map<String, Object> SingleSuccess(){
        Map<String, Object> m = new HashMap<>(2);
        m.put("success", true);
        return m;
    }

    private static ResponseEntity<Map<String, Object>> unauthorized(String message){
        Map<String, Object> m = new HashMap<>(4);
        m.put("success", false);
        m.put("message", message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
    }


    private void putAdminMainDataInSession(HttpSession session, Admin admin){
        session.setAttribute(SESSION_ADMIN, admin);
        Integer memberTotal = memberService.selectTotalCount();
        Integer employeeTotal = employeeService.selectTotalCount();
        Integer equipmentTotal = equipmentService.selectTotalCount();
        int humanTotal = memberTotal + employeeTotal;

        session.setAttribute("humanTotal", humanTotal);
        session.setAttribute("equipmentTotal", equipmentTotal);
        session.setAttribute("memberTotal", memberTotal);
        session.setAttribute("employeeTotal", employeeTotal);
    }



}
