package com.example.classbjunit.repository;

import com.example.classbjunit.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemRepositoryTest {
   //Item item = new Item(21,"Item1",40,400);

    @Autowired
    private ItemRepository itemRepository;


    @Test
    public void findAll_success(){
        List<Item> items = itemRepository.findAll();
        assertTrue(items.size()==4);

    }
}
