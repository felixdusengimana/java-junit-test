package com.example.classbjunit.controller;

import com.example.classbjunit.controller.ItemController;
import com.example.classbjunit.model.Item;
import com.example.classbjunit.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @MockBean
    private ItemService itemServiceMock;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getAllItem_success() throws Exception {
        when(itemServiceMock.getAll())
                .thenReturn(Arrays.asList(new Item(1, "Samuel", 1, 10), new Item(2, "Blessing", 4, 100)));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
        .get("/all-items")
        .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\":1,\"name\":\"Samuel\",\"price\":1,\"quantity\":10},{\"id\":2,\"name\":\"Blessing\",\"price\":4}]"))
                .andReturn();
    }

    @Test
    public void getOne_success() throws Exception {

        when(itemServiceMock.getById(1))
                .thenReturn(new Item(1, "Samuel", 1, 10));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/all-items/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":1,\"name\":\"Samuel\",\"price\":1,\"quantity\":10}"))
                .andReturn();
    }

    @Test
    public void getOne_failure() throws Exception {

        when(itemServiceMock.getById(1))
                .thenReturn(new Item(100, "Samuel", 1, 10));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/all-items/102")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
