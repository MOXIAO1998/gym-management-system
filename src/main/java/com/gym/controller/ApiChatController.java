package com.gym.controller;


import com.gym.pojo.ClassOrder;
import com.gym.pojo.Member;
import com.gym.service.ChatService;
import com.gym.service.ClassOrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/chat")
public class ApiChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private ClassOrderService classOrderService;

    @PostMapping("/query")
    public Map<String,Object> query(@RequestParam("content") String content,
                                    @RequestParam(required = false) String model,
                                    HttpSession session){
        HashMap<String, Object> resp = new HashMap<>();

        try{
            Member member = (Member) session.getAttribute("user");
            String enhancedContent = buildContentWithMemberClasses(content, member);

            String reply = chatService.queryChat(enhancedContent, model);
            resp.put("success", true);
            resp.put("reply", reply);
            return resp;


        }catch (Exception e){
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return resp;

    }


    private String buildContentWithMemberClasses(String originalContent, Member member){
        if (member == null || member.getMemberAccount() == null) return originalContent;

        List<ClassOrder> classOrders = classOrderService.selectClassOrderByMemberAccount(member.getMemberAccount());

        if (classOrders == null || classOrders.isEmpty()) {
            return originalContent;
        }

        String classInfo = classOrders.stream().map(order -> String.format("class name: %s, coach: %s, class begin: %s",
                order.getClassName(), order.getCoach(), order.getClassBegin()
        )).collect(Collectors.joining(";"));

        String memberInfo = String.format("member name: %s, member account: %s", member.getMemberName(), member.getMemberAccount());


        StringBuilder sb = new StringBuilder();
        sb.append(originalContent == null ? "":originalContent.trim());
        sb.append("\n\n");
        sb.append("[Supplementary Information of the System] Below are the current course information that this member has registered for. " +
                "Please first clearly inform the user 'Which courses have I registered for' based on this information, " +
                "and then answer the subsequent questions in combination with the course schedule:\n");
        sb.append(memberInfo).append("\n");
        sb.append(classInfo);


        return sb.toString();
    }


}
