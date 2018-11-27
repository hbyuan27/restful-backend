package com.hbyuan.demo.admin.service;

import org.apache.poi.ss.usermodel.Workbook;

public interface DataImportService {

	/**
	 * <b>把 Excel Workbook 中的每一行数据, 转换成一个或多个对应的 JPA Entity 存入数据库.</b><br>
	 * 一行数据对应多个 Entity 的情况: 例如 Excel 中的一行 User 数据, 需要分拆导入到 UserEntity 和
	 * UserAuthenticationEntity
	 * 
	 * @param workbook
	 *            - Excel工作薄
	 */
	void convertAndSave(Workbook workbook);

}
