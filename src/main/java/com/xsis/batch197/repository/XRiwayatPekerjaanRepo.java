package com.xsis.batch197.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xsis.batch197.model.XRiwayatPekerjaanModel;

@Repository
public interface XRiwayatPekerjaanRepo extends JpaRepository<XRiwayatPekerjaanModel, Long>{
	// yang aktif
			@Query(value="SELECT x FROM XRiwayatPekerjaanModel x WHERE x.isDelete=0 and x.biodataId=:biodataId")
			public List<XRiwayatPekerjaanModel> findActiveByBiodataId(@Param("biodataId") Long biodataId);
			
			@Query(value="SELECT x FROM XRiwayatPekerjaanModel x WHERE x.isDelete=1 and x.biodataId=:biodataId")
			public List<XRiwayatPekerjaanModel> findNonActiveByBiodataId(@Param("biodataId") Long biodataId);
			
}
