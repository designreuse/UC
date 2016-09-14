/**
 * 
 */
package com.yealink.uc.platform.utils;

import java.io.IOException;
import java.util.List;

import com.yealink.uc.platform.exception.FileExportException;

/**
 * The Interface IFileExport.
 * 
 * @author lbt
 */
public interface IFileExport {

	/**
	 * Need export.
	 * 
	 * @param dataList
	 *            the data list
	 * @return true, if successful
	 */
	boolean needExport(List<List<String>> dataList);

	/**
	 * Export.
	 * 
	 * @param sheetName
	 *            the sheet name
	 * @param headNameList
	 *            the head name list
	 * @param dataList
	 *            the data list
	 * @throws IOException
	 * @throws FileExportException
	 */
	void export(String sheetName, List<String> headNameList,
				List<List<String>> dataList) throws IOException,
			FileExportException;
}
