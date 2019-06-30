package com.xsis.batch197.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;

import com.xsis.batch197.model.XBiodataAttachmentModel;
import com.xsis.batch197.model.XBiodataModel;
import com.xsis.batch197.model.XKeahlianModel;
import com.xsis.batch197.model.XRiwayatPekerjaanModel;
import com.xsis.batch197.model.XRiwayatPelatihanModel;
import com.xsis.batch197.model.XRiwayatPendidikanModel;
import com.xsis.batch197.model.XSertifikasiModel;
import com.xsis.batch197.repository.XBiodataAttachmentRepo;
import com.xsis.batch197.repository.XBiodataRepo;
import com.xsis.batch197.repository.XKeahlianRepo;
import com.xsis.batch197.repository.XRiwayatPekerjaanRepo;
import com.xsis.batch197.repository.XRiwayatPelatihanRepo;
import com.xsis.batch197.repository.XRiwayatPendidikanRepo;
import com.xsis.batch197.repository.XSertifikasiRepo;

@Controller
public class ProfilController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfilController.class);
	
	@Autowired
	private XBiodataRepo repoBio;
	
	@Autowired
	private XRiwayatPendidikanRepo repoRiwpend;
	
	@Autowired 
	private XRiwayatPekerjaanRepo repoRiwpek;
	
	@Autowired
	private XKeahlianRepo repoKeahlian;
	
	@Autowired
	private XRiwayatPelatihanRepo repoRiwpel;
	
	@Autowired
	private XSertifikasiRepo repoSer;
	
	@Autowired
	private XBiodataAttachmentRepo repoBioaat;

	@GetMapping(value = "/pelamar/profile/{bid}")
	private ModelAndView index(@PathVariable("bid") Long biodataId) {
		// view profile
		ModelAndView view = new ModelAndView("profile/index");
		// get biodata Id
		XBiodataModel biodata = this.repoBio.findById(biodataId).orElse(null);
		view.addObject("biodata", biodata);
		
		List<XRiwayatPendidikanModel> listPend = repoRiwpend.findAll();
		view.addObject("listPend", listPend);
		List<XRiwayatPekerjaanModel> listPek = repoRiwpek.findAll();
		view.addObject("listPek", listPek);
		List<XKeahlianModel> listKeahlian = repoKeahlian.findAll();
		view.addObject("listKeahlian", listKeahlian);
		List<XRiwayatPelatihanModel> listPel = repoRiwpel.findAll();
		view.addObject("listPel", listPel);
		List<XSertifikasiModel> listSer = repoSer.findAll();
		view.addObject("listSer", listSer);
		List<XBiodataAttachmentModel> listBioatt = repoBioaat.findAll();
		view.addObject("listBioatt", listBioatt);

		return view;
	}
}
