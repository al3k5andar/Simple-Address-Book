package com.am.simpleaddressbook.controllers;

import com.am.simpleaddressbook.domain.Group;
import com.am.simpleaddressbook.service.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class GroupDetailsControllerTest {

    @Mock
    GroupService groupService;

    GroupDetailsController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller= new GroupDetailsController(groupService);

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findGroupById() {
//        Given
        Group group= new Group();
        group.setId(1L);

        Mockito.when(groupService.findById(Mockito.anyLong())).thenReturn(group);

//        When
        Group theGroup= groupService.findById(1L);

//        Then
        Mockito.verify(groupService, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(groupService,Mockito.never()).save(Mockito.any());
        Assertions.assertEquals(1L,theGroup.getId());
    }

    @Test
    void showGroupDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/groups/1/view"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("groups/details/index"));
    }
}