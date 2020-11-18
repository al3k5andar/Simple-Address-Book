package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/groups")
public class GroupController
{
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping({"","/","/index"})
    public String showAllGroups(Model model){
        model.addAttribute("groups", groupService.findAll());

        return "groups/index";
    }

    @GetMapping("/new")
    public String showNewGroupForm(Model model){
        model.addAttribute("group", new Group());

        return "groups/group-form";
    }

    @PostMapping("/new")
    public String processNewGroupForm(@ModelAttribute Group group){
        groupService.save(group);
        return "redirect:/groups/index";
    }

    @GetMapping("/{groupId}/update")
    public String showUpdateGroupForm(@PathVariable Long groupId, Model model){
        model.addAttribute("group", groupService.findById(groupId));
        return "groups/group-form";
    }

    @PostMapping("/{groupId}/update")
    public String processUpdateGroupForm(@PathVariable Long groupId, @ModelAttribute Group group){
        Group groupInBase= groupService.findById(groupId);
        groupInBase.setName(group.getName());
        groupInBase.setDescription(group.getDescription());

        groupService.save(groupInBase);

        return "redirect:/groups/index";
    }
}
