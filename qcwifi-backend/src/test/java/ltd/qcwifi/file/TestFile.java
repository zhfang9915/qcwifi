package ltd.qcwifi.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.math3.analysis.integration.gauss.SymmetricGaussIntegrator;
import org.junit.Test;

import ltd.qcwifi.comm.utils.FileMD5Util;

public class TestFile {

	@Test
	public void stream() {
		FileInputStream fin = null;
		FileOutputStream fout = null;
		BufferedInputStream bufferin = null;
		BufferedOutputStream bufferout= null;
		try {
			File sourceFile = new File("d:\\ai-br100-v0.0.1-os.bin");
			File descFile = new File("d:\\fm\\" + sourceFile.getName());
			
			fin = new FileInputStream(sourceFile);
			fout = new FileOutputStream(descFile);
			bufferin = new BufferedInputStream(fin);
			bufferout = new BufferedOutputStream(fout);
			
            int tempbyte;
            int line = 0;
            StringBuffer sb = new StringBuffer();
            while ((tempbyte = bufferin.read()) != -1) {
            	if (line == 0 && tempbyte == '\n') {
            		line ++;
				}else if (line > 0) {
					bufferout.write(tempbyte);
				}else {
					sb.append((char)tempbyte);
				}
            }
            bufferout.flush();
			String temp = sb.toString();
			/**
			 * ###!!!;;;-hc5761-sysupgrade.bin;v0.0.1;ksldjlkfj;ac3873d4135ff47d4bc8672fb4023b51;;;
			 * 开始标志(###!!!;;;) + 固件ID + ; + 固件版本 + ; + gcc版本 + ; + 原始固件md5 + 三个分号(;;;)
			 * fid---->-hc5761-sysupgrade.bin
				fv----->v0.0.1
				gcc---->ksldjlkfj
				omd5--->ac3873d4135ff47d4bc8672fb4023b51
				nmd5--->82ab5dcaa8b51b8ab4dcd9aa7983bc62
			 */
			if (temp.startsWith("###!!!;;;") && temp.endsWith(";;;")) {
				String arr[] = temp.substring(9, temp.length()-3).split(";");
				String fid = arr[0];
				String fv = arr[1];
				String gcc = arr[2];
				String omd5 = arr[3];
				System.out.println("fid---->" + fid);
				System.out.println("fv----->" + fv);
				System.out.println("gcc---->" + gcc);
				System.out.println("omd5--->" + omd5);
				String nmd5 = FileMD5Util.getMd5ByFile(descFile);
				System.out.println("nmd5--->" + nmd5);
				System.out.println(nmd5.equals(omd5));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
				fout.close();
				bufferin.close();
				bufferout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public OutputStream getOutPutStream(InputStream in, OutputStream out, String dest) {
		FileOutputStream fout = null;
		BufferedInputStream bufferin = null;
		BufferedOutputStream bufferout= null;
		try {
			File descFile = new File(dest);
			
			fout = new FileOutputStream(descFile);
			bufferin = new BufferedInputStream(in);
			bufferout = new BufferedOutputStream(fout);
			
            int tempbyte;
            int line = 0;
            StringBuffer sb = new StringBuffer();
            while ((tempbyte = bufferin.read()) != -1) {
            	if (line == 0 && tempbyte == '\n') {
            		line ++;
				}else if (line > 0) {
					bufferout.write(tempbyte);
				}else {
					sb.append((char)tempbyte);
				}
            }
            bufferout.flush();
			return fout;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fout.close();
				bufferin.close();
				bufferout.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	
}
