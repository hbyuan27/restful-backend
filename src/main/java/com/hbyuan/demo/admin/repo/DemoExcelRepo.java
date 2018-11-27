package com.hbyuan.demo.admin.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbyuan.demo.admin.entity.DemoExcelEntity;

public interface DemoExcelRepo extends JpaRepository<DemoExcelEntity, Long> {

	DemoExcelEntity findByName(String name);

}
