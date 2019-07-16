package com.xsis.batch197.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.xsis.batch197.model.XAddressBookModel;
import com.xsis.batch197.repository.XAddressBookRepo;

@Service
public class UserLockService {
	@Autowired
	private XAddressBookRepo userRepo;

	public Integer fpCount(String username) {
		Integer count = 0;
		
		// get data lewat repo
		XAddressBookModel user = null;
	
		// check dulu user berdasarkan username yang login kalau ada
		if (this.userRepo.findByAbuidAndEmail(username) != null) {
			user = this.userRepo.findByAbuidAndEmail(username);
		
			// dapatkan counter dari database
			count = user.getFpCounter();
			
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
			}
			this.userRepo.save(user);
		}
		return count;
	}
}
