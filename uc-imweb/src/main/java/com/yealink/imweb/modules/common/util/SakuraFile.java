package com.yealink.imweb.modules.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

/**
 * 继承JAVA原始的FILE特性进行升级版的文件创建器
 * 以后在系统中要创建文件使用这个类
 *
 */
public class SakuraFile extends File implements FilenameFilter {
	
	/**
	 * 文件路径
	 */
	private String path;
	
	/**
	 * 构造器
	 * @param path
	 */
	public SakuraFile(String path){
		super(path);
		this.path = path;
	}
	
	/**
	 * 在文件不存在的情况下生成文件，自从判断文件是否存在进行创建
	 * 也会创建上层文件
	 * @param deleteSign
	 * @return
	 * @throws Exception
	 */
	public boolean createNewFile(boolean deleteSign) throws Exception{
		try {
			// 判断文件是否存在
			if (this.exists()) {
				// 在删除标志存在情况下，先删除文件
				if (deleteSign) {
					this.delete();
				} else {
					return true;
				}
			}

			// 先取得文件的文件夹
			String dir = this.getFileDirectory();
			// 创建文件夹
			File myDir = new File(dir);
			myDir.mkdirs();
			// 创建文件
			this.createNewFile();

			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 取得文件输入流（在文件已经创建的情况下）
	 * @return
	 * @throws Exception
	 */
	public FileInputStream getInputStream() throws Exception{
		FileInputStream stream = new FileInputStream(this);
		return stream;
	}
	
	/**
	 * 取得文件输入流，自动创建文件
	 * @return
	 * @throws Exception
	 */
	public FileInputStream getInputStreamC() throws Exception{
		//先创建文件
		this.createNewFile();
		
		FileInputStream stream = new FileInputStream(this);
		return stream;
	}
	
	/**
	 * 取得这个文件的文件夹路径
	 * @throws Exception
	 */
	public String getFileDirectory() throws Exception{
		// 文件夹路径
		String dir = "";
		// 如果已经是路径了，直接返回
		if (this.isDirectory())
			dir = this.getPath();
		else 
			dir = this.getParent();
		//取得最后一位看看是不是/或者\\
		char lastChar = dir.charAt(dir.length() - 1);
		//判断是不是/或者\\
		if (lastChar == '/' || lastChar == '\\'){
			return dir;
		} else {
			return dir + '/';
		}
	}
	
	/**
	 * 得到其它文件的输入流后，用这个方法写入到当前文件（附带创建文件的功能）
	 * @param stream
	 * @param deleteSign
	 * @throws Exception
	 */
	public void writeFileWithInputStream(InputStream stream, boolean deleteSign)
			throws Exception {
		this.writeFileWithInputStream(stream, deleteSign, 2048);
	}
	
	/**
	 * 得到其它文件的输入流后，用这个方法写入到当前文件（附带创建文件的功能）
	 * @param stream
	 * @throws Exception
	 */
	public void writeFileWithInputStream(InputStream stream,
			boolean deleteSign, int readCount) throws Exception {
		// 创建文件
		this.createNewFile(deleteSign);
		// 建立一个写文件的类
		BufferedOutputStream writer = new BufferedOutputStream(
				new FileOutputStream(this));
		// 从输入流里读出字节写到文件输出流里
		boolean writerFlag = true;
		while (writerFlag) {
			// 读取一次取1024个字节
			byte[] data = new byte[readCount];
			// 从输出流里读取字节
			int dataCount = stream.read(data, 0, readCount);
			// 取得读到的字节数
			if (dataCount == -1) {
				// 如果为-1表示读取完成，结束循环
				writerFlag = false;
				break;
			} else {
				// 未读取完则写入文件里
				writer.write(data, 0, readCount);
			}
		}

		writer.close();
	}
	
	/**
	 * 如果文件是文本类型文件，可以通过此方式读出文件的所有内容并生成一个字符串
	 * @return
	 * @throws Exception
	 */
	public String getFileContent() throws Exception {
		// 如果文件不存在，则返回null
		if (!this.exists())
			return null;
		// 声明一个STRINGBUFFER来存放结果字符串
		StringBuffer sb = new StringBuffer();
		// 声明BUFFERREADER来读取文件
		BufferedReader br = new BufferedReader(new FileReader(this));
		// 一行一行读取文本
		while (br.ready()) {
			// 读取一行
			String lineContent = br.readLine();
			// 加入到字符串里，顺便加上换行符
			sb.append(lineContent);
			sb.append('\n');
		}
		// 去掉最后一个换行符
		String result = sb.toString();
		if (result != null && !"".equals(result))
			result = result.substring(0, result.length() - 1);

		return result;
	}

	/**
	 * 实现文件过滤的接口
	 * 第二个参数要检查的文件名
	 */
	public boolean accept(File dir, String fileName) {
		//取得这个文件夹下的所有的文件名
		String[] fileNames = dir.list();
		//判断文件名数组是否存在
		if (fileNames == null || fileNames.length == 0)
			return false;
		//循环检查文件
		for (int i = 0; i < fileNames.length; i++){
			if (fileNames[i] !=null && fileNames[i].equals(fileName)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 自动往文本文件末尾追加内容
	 * @param content
	 * @throws Exception
	 */
	public boolean addTextContent(String content) throws Exception {
		try {
			// 判断文件是否存在，不存在则创建
			if (!this.exists())
				this.createNewFile(false);
			// 取得原来文件里的内容
			String text = this.getFileContent();
			// 生成新的文件内容
			text = text + content;
			// 建立文本文件读取器
			BufferedWriter out = new BufferedWriter(new FileWriter(this
					.getAbsolutePath(), true));
			// 写入追加内容
			out.write(content);
			// 关闭文本文件读取器
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
//-------------------------- add by izumo ren 20091023 v1.1 start -----------------------------
	/**
	 * 取得当前文件的扩展名
	 */
	public String getFileExtentionName() throws Exception {
		//找到最后一个.
		int lastDian = this.path.lastIndexOf(".");
		//找到最后一个\
		int lastFanXG = this.path.lastIndexOf("\\");
		//找到最后一个/
		int lastXG = this.path.lastIndexOf("/");
		//判断最后是哪个符号
		int lastGan = lastFanXG;
		if (lastXG > lastFanXG)
			lastGan = lastXG;
		//取得扩展名
		if (lastDian == -1) {
			return "";
		} else if (lastDian != -1 && lastDian < lastGan) {
			return "";
		} else {
			return this.path.substring(lastDian + 1, this.path.length());
		}
	}
	
	/**
	 * 取得不带扩展名的文件名
	 * @return
	 * @throws Exception
	 */
	public String getFileNameWithoutExtentionName() throws Exception {
		//找到最后一个.
		int lastDian = this.path.lastIndexOf(".");
		//找到最后一个\
		int lastFanXG = this.path.lastIndexOf("\\");
		//找到最后一个/
		int lastXG = this.path.lastIndexOf("/");
		//判断最后是哪个符号
		int lastGan = lastFanXG;
		if (lastXG > lastFanXG)
			lastGan = lastXG;
		//取得扩展名
		if (lastDian == -1) {
			return this.path;
		} else if (lastDian != -1 && lastDian < lastGan) {
			return this.path;
		} else {
			return this.path.substring(0, this.path.length() - lastDian);
		}
	}
	
	/**
	 * 刪除整个文件夹
	 * 传参如：f = new File("c:\\wp");刪除c:\\wp文件夹。
	 * desc:add by wp
	 * @return
	 * @throws IOException
	 */
	public boolean delTree(File file) throws IOException {
		int filecnt = 0;
	    int dircnt = 0;
        if (file.isDirectory()) {
            File[] f = file.listFiles();
            for (int i = f.length; --i >= 0;) {
                boolean ret = delTree(f[i]);
                if (!ret) return false;
            }
            if (!file.delete()) return false;
            dircnt++;
        } else {
            if (!file.delete()) return false;
            filecnt++;
        }
        return true;
    }
	
	
//-------------------------- add by izumo ren 20091023 v1.1  end  -----------------------------
}
