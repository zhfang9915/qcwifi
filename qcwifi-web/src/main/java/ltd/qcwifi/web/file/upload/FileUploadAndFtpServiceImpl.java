package ltd.qcwifi.web.file.upload;

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

import ltd.qcwifi.comm.utils.sftp.SFTPChannel;

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
