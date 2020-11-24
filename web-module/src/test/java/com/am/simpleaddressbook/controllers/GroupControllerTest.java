package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

class GroupControllerTest {

    @Mock
    GroupService groupService;

    GroupController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller= new GroupController(groupService);

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showAllGroups() throws Exception {
//        Given
        Group group1= new Group();
        group1.setId(1L);
        Group group2= new Group();
        group2.setId(2L);

        Set<Group> groups= new HashSet<>();
        groups.add(group1);
        groups.add(group2);

        Mockito.when(groupService.findAll()).thenReturn(groups);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("groups"))
                .andExpect(MockMvcResultMatchers.view().name("groups/index"));

//        Then
        Assertions.assertEquals(2, groupService.findAll().size());
    }

    @Test
    void showNewGroupForm() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.view().name("groups/group-form"));
//        Then
        Mockito.verify(groupService, Mockito.never()).findAll();
    }

    @Test
    void processNewGroupForm() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);

        Mockito.when(groupService.save(Mockito.any())).thenReturn(group);

//        When
        mockMvc.perform(MockMvcRequestBuilders.post("/groups/new"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/index"));

//        Then
        Mockito.verify(groupService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void showUpdateGroupForm() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);

//        When
        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        Then
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("group"))
                .andExpect(MockMvcResultMatchers.view().name("groups/group-form"));
    }

    @Test
    void processUpdateGroupForm() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        mockMvc.perform(MockMvcRequestBuilders.post("/groups/1/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("groupId","1L")
                    .param("group","group"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/index"));

//        Then
        Mockito.verify(groupService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void deleteGroup() throws Exception {
//        Given
        Group group= new Group();
        group.setId(1L);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/index"));
//        Then
        Mockito.verify(groupService,Mockito.times(1)).deleteById(Mockito.anyLong());
    }
}