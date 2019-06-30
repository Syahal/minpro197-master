package com.xsis.batch197.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xsis.batch197.model.XBiodataModel;
import com.xsis.batch197.model.XRiwayatPelatihanModel;
import com.xsis.batch197.repository.XBiodataRepo;
import com.xsis.batch197.repository.XRiwayatPelatihanRepo;

@Controller
public class PelatihanController {
	private static final Logger logger = LoggerFactory.getLogger(PelatihanController.class);
	
	@Autowired
	private XBiodataRepo bioRepo;
	
	@Autowired
	private XRiwayatPelatihanRepo pelatihanRepo;
	
	@GetMapping(value = "/pelamar/pelatihan/{bid}")
	private ModelAndView index(@PathVariable("bid") Long biodataId) {
		// view keluarga
		ModelAndView view = new ModelAndView("pelatihan/index");
		// get biodata Id
		XBiodataModel biodata = this.bioRepo.findById(biodataId).orElse(null);
		view.addObject("biodata", biodata);

		// get Keluarga
		List<XRiwayatPelatihanModel> listPelatihan = this.pelatihanRepo.findByBiodataId(biodataId);
		view.addObject("listPelatihan", listPelatihan);
		return view;
	}
	
	// Method Button Add
		@GetMapping(value = "/pelatihan/add/{bid}") // bid ini sebagai variable dari biodataId
		public ModelAndView create(@PathVariable("bid") Long biodataId) {
			// menampilkan view dari folder pelatihan file _create.html
			ModelAndView view = new ModelAndView("pelatihan/_create");
			// membuat object keahlian model
			XRiwayatPelatihanModel pelatihan = new XRiwayatPelatihanModel();
			// set biodata id nya
			pelatihan.setBiodataId(biodataId);
			// add object keahlian
			view.addObject("pelatihan", pelatihan);
			return view;
		}
		
		// Method simpan data
		@PostMapping(value="/pelatihan/save")
		public ModelAndView save(@Valid @ModelAttribute("pelatihan") XRiwayatPelatihanModel pelatihan, BindingResult result) {
			// menampilkan view dari folder keahlian _create.html
			ModelAndView view = new ModelAndView("pelatihan/_create");
			
			if(result.hasErrors()) {
				logger.info("Save Keahlian Error!");
				// add object keahlian beserta errornya ke view
				view.addObject("pelatihan", pelatihan);
			} else {
				// simpan ke repo
				pelatihanRepo.save(pelatihan);
				// add object baru tanpa error
				view.addObject("pelatihan", new XRiwayatPelatihanModel());
			}
			return view;
		}
}
