package com.xsis.batch197.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsis.batch197.model.XRiwayatPelatihanModel;

@Repository
public interface XRiwayatPelatihanRepo extends JpaRepository<XRiwayatPelatihanModel, Long>{
	public List<XRiwayatPelatihanModel> findByBiodataId(Long biodataId);
}
