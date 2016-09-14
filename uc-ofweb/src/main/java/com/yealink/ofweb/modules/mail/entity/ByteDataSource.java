package com.yealink.ofweb.modules.mail.entity;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 类描述：字节数据源
 * 
 * @author: pengzhiyuan
 * @version $Id: Exp$
 * 
 */
public class ByteDataSource implements DataSource {
	private byte[] filebyte = null;
	private String filetype = "application/octet-stream";
	private String filename = "";
	private InputStream inputstream = null;

	public ByteDataSource() {
	}

	public ByteDataSource(String fileName) {
		File f = new File(fileName);
		filename = f.getName();
		try {
			inputstream = new FileInputStream(f);
			inputstream.read(filebyte);
		} catch (Exception e) {
		}
	}
	
	public ByteDataSource(byte[] filebyte, String displayfilename) {
        this.filebyte=filebyte;
        this.filename=displayfilename;
    }

	public String getContentType() {
		return filetype;
	}

	public InputStream getInputStream() throws IOException {
		InputStream input=new ByteArrayInputStream(filebyte);
        return input;
	}

	public String getName() {
		return filename;
	}

	public OutputStream getOutputStream() throws IOException {
		throw new IOException("cannot do this");
	}

}
