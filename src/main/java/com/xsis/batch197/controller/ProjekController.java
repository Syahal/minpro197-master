package com.xsis.batch197.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.xsis.batch197.model.XRiwayatProyekModel;
import com.xsis.batch197.repository.XBiodataRepo;
import com.xsis.batch197.repository.XRiwayatProyekRepo;

@Controller
public class ProjekController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ProjekController.class);
	
	@Autowired
	private XBiodataRepo bioRepo;
	
	@Autowired
	private XRiwayatProyekRepo proRepo;
	
	@GetMapping(value = "/pengalaman/tambah/{bid}") // bid sebagai vaiable biodataId
	public ModelAndView tambah(@PathVariable("bid") Long biodataId) {
		// menampilkan view dari folder pengalaman file _form.html
		ModelAndView view = new ModelAndView("pengalaman/formprojek");
		// membuat object pengalaman model
		XRiwayatProyekModel projek = new XRiwayatProyekModel();
		// set biodata id
		projek.setBiodataId(biodataId);

		Calendar date = new GregorianCalendar();
		Integer currentYear = date.get(Calendar.YEAR);
		List<Integer> listBulan = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			listBulan.add(i);
		}

		List<Integer> listStartYear = new ArrayList<Integer>();
		for (int i = currentYear - 20; i <= currentYear; i++) {
			listStartYear.add(i);
		}

		List<Integer> listValidYear = new ArrayList<Integer>();
		for (int i = currentYear; i <= currentYear + 10; i++) {
			listValidYear.add(i);
		}
		// add object pengalaman
		view.addObject("projek", projek);
		// add object list mothn
		view.addObject("listBulan", listBulan);
		// add object tahun start
		view.addObject("listStartYear", listStartYear);
		// add object tahun valid
		view.addObject("listValidYear", listValidYear);
		return view;
	}
	
	@PostMapping(value = "/pengalaman/simpan")
	public ModelAndView simpan(@Valid @ModelAttribute("projek") XRiwayatProyekModel projek,
			BindingResult result) {
		// menampilkan view dari folder pengalaman file _form.html
		ModelAndView view = new ModelAndView("pengalaman/formprojek");

		Calendar date = new GregorianCalendar();
		Integer currentYear = date.get(Calendar.YEAR);
		List<Integer> listBulan = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			listBulan.add(i);
		}

		List<Integer> listStartYear = new ArrayList<Integer>();
		for (int i = currentYear - 20; i <= currentYear; i++) {
			listStartYear.add(i);
		}

		List<Integer> listValidYear = new ArrayList<Integer>();
		for (int i = currentYear; i <= currentYear + 10; i++) {
			listValidYear.add(i);
		}
		// add object list mothn
		view.addObject("listBulan", listBulan);
		// add object tahun start
		view.addObject("listStartYear", listStartYear);
		// add object tahun valid
		view.addObject("listValidYear", listValidYear);
		if (result.hasErrors()) {
			logger.info("save biodata error");

			// add object pengalaman dengan error nya ke view
			view.addObject("projek", projek);
		} else {
			// set user id
			projek.setCreatedBy(this.getAbuid());
			projek.setCreatedOn(new Date());
			// simpan ke repo
			proRepo.save(projek);
			// add object baru tanpa error
			//view.addObject("pengalaman", new XpengalamanModel());
			view = new ModelAndView("redirect:/pengalaman/tambah/"+ projek.getBiodataId());
		}
		return view;
	}
	
	@GetMapping(value = "/pengalaman/edit/{sid}") // bid sebagai vaiable biodataId
	public ModelAndView edit(@PathVariable("sid") Long sid) {
		// menampilkan view dari folder pengalaman file _form.html
		ModelAndView view = new ModelAndView("pengalaman/formprojek");
		// membuat object pengalaman model
		XRiwayatProyekModel projek = this.proRepo.findById(sid).orElse(null);

		Calendar date = new GregorianCalendar();
		Integer currentYear = date.get(Calendar.YEAR);
		List<Integer> listBulan = new ArrayList<Integer>();
		for (int i = 1; i <= 12; i++) {
			listBulan.add(i);
		}

		List<Integer> listStartYear = new ArrayList<Integer>();
		for (int i = currentYear - 20; i <= currentYear; i++) {
			listStartYear.add(i);
		}

		List<Integer> listValidYear = new ArrayList<Integer>();
		for (int i = currentYear; i <= currentYear + 10; i++) {
			listValidYear.add(i);
		}
		// add object pengalaman
		view.addObject("pengalaman", projek);
		// add object list mothn
		view.addObject("listBulan", listBulan);
		// add object tahun start
		view.addObject("listStartYear", listStartYear);
		// add object tahun valid
		view.addObject("listValidYear", listValidYear);
		return view;
	}
	
//	@GetMapping(value = "/pengalaman/delete/{sid}")
//	private ModelAndView hapus(@PathVariable("sid") Long sid) {
//		// view sertifkasi
//		ModelAndView view = new ModelAndView("pengalaman/hapusprojek");
//		// get pengalaman
//		XRiwayatProyekModel projek = this.proRepo.findById(sid).orElse(null);
//		view.addObject("projek", projek);
//		return view;
//	}
//
//	@PostMapping(value = "/pengalaman/remove")
//	private ModelAndView remove(@ModelAttribute("pengalaman") XRiwayatProyekModel projek) {
//		// get pengalaman
//		XRiwayatProyekModel item = this.proRepo.findById(projek.getId()).orElse(null);
//
//		// set delete
//		item.setDeletedOn(new Date());
//		item.setDeletedBy(this.getAbuid());
//		item.setIsDelete(1);
//		this.proRepo.save(item);
//
//		// view sertifkasi
//		ModelAndView view = new ModelAndView("pengalaman/_hapus");
//		view.addObject("pengalaman", item);
//		return view;
//	}
}
