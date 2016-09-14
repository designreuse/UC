/**
 * 
 */
package com.yealink.uc.platform.utils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.yealink.uc.platform.exception.FileExportException;

/**
 * @author yl0167
 * 
 */
public class ExcelFileExport implements IFileExport {

	private final OutputStream outputStream;

	public ExcelFileExport(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	@Override
	public boolean needExport(List<List<String>> dataList) {
		return false;
	}

	@Override
	public void export(String sheetName, List<String> headNameList,
			List<List<String>> dataList) throws IOException,
		FileExportException {
		WritableWorkbook workbook = null;
		try {
			int rowCount = dataList.size();
			int columnCount = headNameList.size();

			workbook = Workbook.createWorkbook(outputStream);
			String name = sheetName != null ? sheetName : "sheet1";
			WritableSheet sheet = workbook.createSheet(name, 0);

			// header format.
			WritableFont arial10ptBold = new WritableFont(WritableFont.ARIAL,
					10, WritableFont.BOLD);
			WritableCellFormat headerFormat = new WritableCellFormat(
					arial10ptBold);
			WritableCellFormat wrappedformat = new WritableCellFormat(
					WritableWorkbook.ARIAL_10_PT);
			wrappedformat.setWrap(true);

			// write the table header
			for (int col = 0; col < columnCount; col++) {
				sheet.addCell(new Label(col, 0, headNameList.get(col),
						headerFormat));
			}

			// write the data.
			for (int row = 0; row < rowCount; row++) {
				for (int col = 0; col < columnCount; col++) {
					Object value = dataList.get(row).get(col);
					if (null == value)
						value = "";
					sheet.setColumnView(col, 40);
					sheet.addCell(new Label(col, row + 1, value.toString(),
							wrappedformat));
				}
			}
			workbook.write();
		} catch (WriteException e) {
			throw new FileExportException(e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (WriteException e) {
					throw new FileExportException(e);
				}
			}
		}
	}

}
