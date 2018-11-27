package com.hbyuan.demo.admin.service;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbyuan.demo.admin.entity.DemoExcelEntity;
import com.hbyuan.demo.admin.repo.DemoExcelRepo;
import com.hbyuan.demo.common.CustomizedException;

@Service
public class DemoImportService implements DataImportService {

	@Autowired
	private DemoExcelRepo demoRepo;

	@Override
	@Transactional
	public void convertAndSave(Workbook workbook) {
		DataFormatter formatter = new DataFormatter();
		// 获取 workbook 中的第一张表
		Sheet sheet = workbook.getSheetAt(0);
		// 从第二行开始处理 (第一行是表头, 不处理)
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			// name为业务主键. row, cell的计数从0开始
			String name = formatter.formatCellValue(row.getCell(0));
			// 如果数据已经存在, 则进行更新操作
			try {
				DemoExcelEntity entity = demoRepo.findByName(name);
				if (entity == null) {
					entity = new DemoExcelEntity();
					entity.setName(name);
				}
				// 注意, 无论cell类型是什么, 一律转成String处理
				String code = formatter.formatCellValue(row.getCell(1));
				String content = formatter.formatCellValue(row.getCell(2));
				entity.setCode(Integer.valueOf(code));
				entity.setContent(content);
				demoRepo.save(entity);
			} catch (RuntimeException e) {
				throw new CustomizedException(1002,
						"Import demo data failed. Name: " + name, e.getCause());
			}
		}
	}

}
