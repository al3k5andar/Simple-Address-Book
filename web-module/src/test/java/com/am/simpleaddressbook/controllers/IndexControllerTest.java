package com.am.simpleaddressbook.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class IndexControllerTest {

    IndexController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller= new IndexController();
        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void redirectMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/groups/index"));
    }
}