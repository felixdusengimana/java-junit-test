package com.example.classbjunit.service;

import java.util.List;
import java.util.Optional;

import com.example.classbjunit.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.classbjunit.model.Item;
import com.example.classbjunit.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> getAll() {
		
		List<Item> items = itemRepository.findAll();
		
		for(Item item:items) {
			item.setValue(item.getQuantity()*item.getPrice());
		}
		return items;
	}

	public Item getById(int id) {
		Optional<Item> item = itemRepository.findById(id);

		if(item.isPresent()) {
			return item.get();
		}
		return null;
	}

	public Item save(ItemDTO itemDTO){

		Item item = new Item();
		item.setId(itemDTO.getId());
		item.setName(itemDTO.getName());
		item.setQuantity(itemDTO.getQuantity());
		item.setPrice(itemDTO.getPrice());

		if(itemRepository.existsByName()){
			return null;
		}
		return itemRepository.save(item);
	}
}
