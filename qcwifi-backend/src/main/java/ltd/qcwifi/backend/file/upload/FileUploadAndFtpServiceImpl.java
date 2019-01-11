package ltd.qcwifi.backend.file.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jcraft.jsch.ChannelSftp;

import ltd.qcwifi.comm.utils.FileMD5Util;
import ltd.qcwifi.comm.utils.sftp.SFTPChannel;
import ltd.qcwifi.model.entity.cloud.platform.Firmware;

@Service
public class FileUploadAndFtpServiceImpl implements FileUploadAndFtpService {
	
	@Autowired
	SFTPChannel channel;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
    /**
     * 文件上传
     * 	这些标记了过时的方法就不要在使用了
     * @param request
     * @param folderPath
     * @param fileName
     * @return
     */
	@Override
	@Deprecated
    public boolean uploadByName(HttpServletRequest request, String folderPath, String fileName){
    	boolean flag = false;
    	try {
    		ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;  
    	    CustomCommonsMultipartResolver commonsMultipartResolver = new CustomCommonsMultipartResolver();  
    	    MultipartHttpServletRequest mRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest());  
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();       
            Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                if(mFile.getSize() != 0 && !"".equals(mFile.getName())){
                    write(mFile.getInputStream(), new FileOutputStream(initFilePath(fileName, folderPath)));
                }
                break;
            }
            flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
    	return flag;
    }
    
    /**
     * 上传
     * @param request
     * @throws IOException
     */
    @Override
    @Deprecated
    public boolean upload(MultipartHttpServletRequest request, String folderPath){
    	boolean flag = false;
    	try {
            Map<String, MultipartFile> fileMap = request.getFileMap();       
            Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();
                if(mFile.getSize() != 0 && !"".equals(mFile.getName())){
                    write(mFile.getInputStream(), new FileOutputStream(initFilePath(mFile.getOriginalFilename(), folderPath)));
                }
            }
            flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
    	return flag;
    }
    
    /**
     * 计算文件存放具体路径
     * @param name
     * @return
     */
    @Override
    @Deprecated
    public String initFilePath(String name, String folderPath) {
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return (file.getPath() + "/" + name).replaceAll(" ", "-");
    }
    
    /**
     * 下载
     * @param downloadfFilePath
     * @param out
     */
    @Override
    @Deprecated
    public void download(String downloadfFilePath, ServletOutputStream out) {
        try {
            FileInputStream in = new FileInputStream(new File(downloadfFilePath));
            write(in, out);
        } catch (FileNotFoundException e) {
            try {
                FileInputStream in = new FileInputStream(new File(new String(downloadfFilePath.getBytes("iso-8859-1"),"utf-8")));
                write(in, out);
            } catch (IOException e1) {              
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    
    
    /**
     * 上传文件流至FTP
     * @param is
     * @param dst	文件路径(带具体文件名)
     * @param timeout
     * @return
     */
    @Override
    public boolean uploadFTPByInputStream(InputStream is, String dst, int timeout) {
        ChannelSftp chSftp = null;
        try {
        	chSftp = channel.getChannel(timeout);
        	OutputStream out = chSftp.put(dst, ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
            byte[] buff = new byte[1024 * 256]; // 设定每次传输的数据块大小为256KB
            int read;
            if (out != null) {
                do {
                    read = is.read(buff, 0, buff.length);
                    if (read > 0) {
                        out.write(buff, 0, read);
                    }
                    out.flush();
                } while (read >= 0);
            }
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}  finally {
			if (chSftp != null) {
				chSftp.quit();
			}
			if (channel != null) {
				channel.closeChannel();
			}
		}
	}
    
//    @Override
//    public Firmware uploadFirmware(InputStream in, String dst, int timeout) {
//    	ChannelSftp chSftp = null;
//    	OutputStream out = null;
//    	BufferedInputStream bufferin = null;
//		BufferedOutputStream bufferout= null;
//        try {
//        	chSftp = channel.getChannel(timeout);
//        	out = chSftp.put(dst, ChannelSftp.OVERWRITE); // 使用OVERWRITE模式
//        	bufferin = new BufferedInputStream(in);
//			bufferout = new BufferedOutputStream(out);
//            if (out != null) {
//            	int tempbyte;
//                int line = 0;
//                StringBuffer sb = new StringBuffer();
//                while ((tempbyte = bufferin.read()) != -1) {
//                	if (line == 0 && tempbyte == '\n') {
//                		line ++;
//    				}else if (line > 0) {
//    					bufferout.write(tempbyte);
//    				}else {
//    					sb.append((char)tempbyte);
//    				}
//                }
//                out.flush();
//                
//                /**
//    			 * ###!!!;;;-hc5761-sysupgrade.bin;v0.0.1;ksldjlkfj;ac3873d4135ff47d4bc8672fb4023b51;;;
//    			 * 开始标志(###!!!;;;) + 固件ID + ; + 固件版本 + ; + gcc版本 + ; + 原始固件md5 + 三个分号(;;;)
//    			 * fid---->-hc5761-sysupgrade.bin
//    				fv----->v0.0.1
//    				gcc---->ksldjlkfj
//    				omd5--->ac3873d4135ff47d4bc8672fb4023b51
//    				nmd5--->82ab5dcaa8b51b8ab4dcd9aa7983bc62
//    			 */
//                String header = sb.toString();
//    			if (header.startsWith("###!!!;;;") && header.endsWith(";;;")) {
//    				String arr[] = header.substring(9, header.length()-3).split(";");
//    				String fid = arr[0];
//    				String fv = arr[1];
//    				String gcc = arr[2];
//    				String omd5 = arr[3];
//    				System.out.println("fid---->" + fid);
//    				System.out.println("fv----->" + fv);
//    				System.out.println("gcc---->" + gcc);
//    				System.out.println("omd5--->" + omd5);
//    				String nmd5 = FileMD5Util.getMd5ByInputStream(chSftp.getInputStream());
//    				System.out.println("nmd5--->" + nmd5);
//    				System.out.println(nmd5.equals(omd5));
//    				if (nmd5.equals(omd5)) {
//    					Firmware fm = new Firmware();
//        				fm.setId(fid);
//        				fm.setVersion(fv);
//        				fm.setCrosstool(gcc);
//        				fm.setMd5(nmd5);
//        				return fm;
//					}
//    			}
//            }
//			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}  finally {
//			if (bufferin != null) {
//				try {
//					bufferin.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (bufferout != null) {
//				try {
//					bufferout.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if (chSftp != null) {
//				chSftp.quit();
//			}
//			if (channel != null) {
//				channel.closeChannel();
//			}
//		}
//	}
    
    @Override
    public void downloadFromFTP(ServletOutputStream out, String src, int timeout) {
    	ChannelSftp chSftp = null;
    	try {
    		chSftp = channel.getChannel(timeout);
    		InputStream in = chSftp.get(src);
            write(in, out);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}  finally {
    		if (chSftp != null) {
    			chSftp.quit();
    		}
    		if (channel != null) {
    			channel.closeChannel();
    		}
    	}
    }
    
    /**
     * 删除FTP上指定的文件
     * @param src
     * @param timeout
     * @return
     */
    @Override
    public boolean deleteFromFTP(String dir, String fileName, int timeout) {
    	ChannelSftp chSftp = null;
    	try {
    		chSftp = channel.getChannel(timeout);
    		chSftp.cd(dir);
    		chSftp.rm(fileName);
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}  finally {
    		if (chSftp != null) {
    			chSftp.quit();
    		}
    		if (channel != null) {
    			channel.closeChannel();
    		}
    	}
    }
    
    /**
     * 写入数据
     * @param in
     * @param out
     * @throws IOException
     */
    private void write(InputStream in, OutputStream out) throws IOException{
        try{
            byte[] buffer = new byte[1024];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        } finally {
            try {
                in.close();
            }
            catch (IOException ex) {
            }
            try {
                out.close();
            }
            catch (IOException ex) {
            }
        }
    }
    
}
