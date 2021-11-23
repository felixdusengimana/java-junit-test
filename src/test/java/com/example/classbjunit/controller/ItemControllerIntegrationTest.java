package com.example.classbjunit.controller;


import com.example.classbjunit.dto.ItemDTO;
import com.example.classbjunit.model.Item;
import com.example.classbjunit.util.APIResponse;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/all-items", String.class);
        System.out.println(response);
        JSONAssert.assertEquals("[{id: 101},{id: 102},{id: 103},{id: 104}]",response,false);
    }

    @Test
    public void getById_success(){
        ResponseEntity<Item> item = this.restTemplate.getForEntity("/all-items/101",Item.class,false);
        assertTrue(item.getStatusCode().is2xxSuccessful());
        assertEquals("Item1",item.getBody().getName());
        assertEquals(100,item.getBody().getQuantity());
    }

    @Test
    public void getById_failure(){
        ResponseEntity<APIResponse> item = this.restTemplate.getForEntity("/all-items/1",APIResponse.class,false);
        assertTrue(item.getStatusCodeValue()==404);
        assertEquals("Item not found", item.getBody().getMessage());
    }

    @Test
    public void saveItem_success(){
        ItemDTO itemDTO = new ItemDTO(106,"Nike Shoes", 100,1000);
        ResponseEntity<Item> item  = this.restTemplate.postForEntity("/add-item",itemDTO,Item.class,false);
        assertTrue(item.getStatusCodeValue() == 201);
        assertEquals(106,item.getBody().getId());
    }

    @Test
    public void saveItem_failure(){
        ItemDTO itemDTO = new ItemDTO(107,"Item1", 100,1000);
        ResponseEntity<APIResponse> item  = this.restTemplate.postForEntity("/add-item",itemDTO,APIResponse.class,false);
        assertTrue(item.getStatusCodeValue() == 401);
    }
}
