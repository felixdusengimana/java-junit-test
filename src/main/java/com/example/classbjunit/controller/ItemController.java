package com.example.classbjunit.controller;

import java.util.List;
import java.util.Optional;

import com.example.classbjunit.dto.ItemDTO;
import com.example.classbjunit.util.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.classbjunit.model.Item;
import com.example.classbjunit.service.ItemService;

import javax.validation.Valid;

@RestController
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@GetMapping("/all-items")
	public List<Item> getAll(){
		
		return itemService.getAll();
	}

	@GetMapping("/all-items/{id}")
	public ResponseEntity<?> getById(@PathVariable(name="id") int id){
		Item item = itemService.getById(id);

		if(item != null)  {
			return ResponseEntity.status(HttpStatus.OK).body(item);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "Item not found"));
	}

	@PostMapping("/add-item")
	public ResponseEntity<?> saveItem(@Valid @RequestBody ItemDTO itemDTO){
      Item itemSaved = itemService.save(itemDTO);
		System.out.println(itemDTO.getName());
      if(itemSaved!=null){
      	return ResponseEntity.status(HttpStatus.CREATED).body(itemSaved);
	  }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new APIResponse(false, "Item not created"));
	}
}
