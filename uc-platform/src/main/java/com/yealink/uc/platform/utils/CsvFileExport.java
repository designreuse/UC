/**
 * 
 */
package com.yealink.uc.platform.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author yl0167
 * 
 */
public class CsvFileExport implements IFileExport {

	private final OutputStream outputStream;

	public CsvFileExport(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yealink.vcCloud.common.export.IFileExport#needExport(java.util.List)
	 */
	@Override
	public boolean needExport(List<List<String>> dataList) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yealink.vcCloud.common.export.IFileExport#export(java.lang.String,
	 * java.util.List, java.util.List)
	 */
	@Override
	public void export(String sheetName, List<String> headNameList,
			List<List<String>> dataList) throws IOException {
		StringBuffer fileBuf = new StringBuffer("");
		int rowCount = dataList.size();
		int columnCount = headNameList.size();
		for (int col = 0; col < columnCount; col++) {
			fileBuf.append(headNameList.get(col));
			fileBuf.append(",");
		}
		fileBuf.append("\n");
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < columnCount; col++) {
				Object value = dataList.get(row).get(col);
				if (null == value)
					value = "";
				fileBuf.append(value);
				if (col != columnCount - 1) {
					fileBuf.append(",");
				}
			}
			if (row != rowCount - 1) {
				fileBuf.append(",");
			}
			fileBuf.append("\n");
		}
		//加上UTF-8文件的标识字符 
		outputStream.write(new   byte []{( byte ) 0xEF ,( byte ) 0xBB ,( byte ) 0xBF });
		outputStream.write(fileBuf.toString().getBytes());
	}

}
