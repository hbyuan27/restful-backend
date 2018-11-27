package com.hbyuan.demo.admin.controller;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import com.hbyuan.demo.admin.service.DataImportService;
import com.hbyuan.demo.admin.service.DemoImportService;
import com.hbyuan.demo.common.JsonResponse;

/**
 * 从 Excel 导入相关数据.<br>
 * Excel 模板在 src/test/resources=>import_file_example 文件夹里. <br>
 * 如果对 Excel 模板做修改, 需要对代码进行相应修改, 反之亦然.<br>
 * 支持导入新数据, 更新已有数据. 暂时不支持删除操作.
 */
@RestController
@RequestMapping("/admin/upload")
public class DataImportController {

	@Autowired
	private DemoImportService demoImportService;

	@PostMapping("/demo")
	public JsonResponse importDemoExcel(@RequestParam("file") MultipartFile file)
			throws IOException {
		return processExcelFile(file, demoImportService);
	}

	// @PostMapping("/user")
	// public JsonResponse importUsers(@RequestParam("file") MultipartFile file)
	// throws IOException {
	// return processExcelFile(file, userImportService);
	// }

	/**
	 * 把 Excel 转化成对应的 Java Bean, 并存储到数据库
	 * 
	 * @param file
	 *            - Excel 文件
	 * @param DataImportService
	 *            - 与当前 Excel 内容对应的数据导入服务
	 * @return 基于 {@link JsonResponse} 的成功或者错误信息
	 * @throws IOException
	 */
	private JsonResponse processExcelFile(MultipartFile file,
			DataImportService dataImportService) throws IOException {
		String fileName = file.getOriginalFilename();
		if (file.isEmpty() || fileName == null || fileName.isEmpty()) {
			throw new MultipartException(
					"File is empty or invalid. Original file name: "
							+ fileName);
		}
		Workbook workbook = null;
		try {
			String fileExtension = StringUtils.getFilenameExtension(fileName);
			if ("xls".equalsIgnoreCase(fileExtension)) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else if ("xlsx".equalsIgnoreCase(fileExtension)) {
				workbook = new XSSFWorkbook(file.getInputStream());
			} else {
				throw new MultipartException(
						"Not an Excel file. Current file name extension: "
								+ fileExtension);
			}
			dataImportService.convertAndSave(workbook);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return JsonResponse.success();
	}

}