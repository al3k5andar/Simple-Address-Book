package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/groups/{groupId}")
public class GroupDetailsController {

    private final GroupService groupService;

    public GroupDetailsController(GroupService groupService) {
        this.groupService = groupService;
    }

    @ModelAttribute("group")
    public Group findGroupById(@PathVariable Long groupId){
        return groupService.findById(groupId);
    }

    @GetMapping("/view")
    public ModelAndView showGroupDetails(){
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("groups/details/index");

        return modelAndView;
    }
}
