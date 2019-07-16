package com.xsis.batch197.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xsis.batch197.model.XAddressBookModel;
import com.xsis.batch197.repository.XAddressBookRepo;

@RestController
@RequestMapping("/api")
public class ApiUserController extends BaseController {
	@Autowired
	private XAddressBookRepo userRepo;

	@GetMapping(value = "user")
	public List<XAddressBookModel> listUser() {
		return userRepo.findAll();
	}

	@GetMapping(value = "user/get/{id}")
	public XAddressBookModel getUser(@PathVariable("id") Long id) {
		return userRepo.findById(id).orElse(new XAddressBookModel());
	}

	@PostMapping(value = "/user/fp-count")
	public ResponseEntity<XAddressBookModel> lockUser(@RequestParam("username") String username) {
		ResponseEntity<XAddressBookModel> result = new ResponseEntity<XAddressBookModel>(HttpStatus.INTERNAL_SERVER_ERROR);
		// get data lewat repo
		System.out.println("masuk");
		XAddressBookModel user = null;
		// check dulu user berdasarkan username yang login kalau ada
		
		if (this.userRepo.findByAbuidAndEmail(username)!= null) {
			System.out.println("masuk kondisi");
			user = this.userRepo.findByAbuidAndEmail(username);
			// dapatkan counter dari database
			Integer count = user.getFpCounter();
			// jika counter sudah 3 maka
			if (count >= 3) {
				// user loked menjadi 1 => user di lock
				user.setIsLocked(1);
			} else {
				// jika masih kurang dari 3, maka counter ditambah
				count++;
				// update counter nya di databse
				user.setFpCounter(count);
				// masih belun di lock user nya
				user.setIsLocked(0);
				this.userRepo.save(user);
			}
			// kemudian disimpan ke database
			try {
				this.userRepo.save(user);
				result =new ResponseEntity<XAddressBookModel>(user, HttpStatus.OK);
			} catch (Exception e) {
				result =new ResponseEntity<XAddressBookModel>(user, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return result;
	}

}
