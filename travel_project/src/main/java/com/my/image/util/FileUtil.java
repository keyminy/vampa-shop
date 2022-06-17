package com.my.image.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.FileNameMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;
import net.coobird.thumbnailator.Thumbnails;

@Log
public class FileUtil {

	// 파일이 존재하는지 확인하는 메서드
	public static boolean exist(File file) throws Exception{
		return file.exists();
	}
	
	// 파일이 존재하는지 확인하는 메서드
	public static boolean exist(String fileName) throws Exception{
		return toFile(fileName).exists();
	}
	
	// 문자열을 파일 객체로 만들어 주는 메서드
	public static File toFile(String fileName) throws Exception{
		return new File(fileName);
	}
	
	// 파일 지우기
	public static boolean delete(File file) throws Exception {
		return file.delete();
	}
	
	// 파일의 정보를 문자열로 넣어주면 지워주는 메서드
	// 파일은 realPath 정보의 파일명을 넘겨야 한다.
	public static boolean remove(String fileName) throws Exception {
		// 1. 문자열을 파일 객체로 만든다. 2. 있는지 확인한다. 3. 삭제한다. 4. 결과 리턴
		File file = toFile(fileName);
		// 파일이 존재하지 않는 경우 예외 발생
		if(!exist(file)) throw new Exception("삭제하려는 파일이 존재하지 않습니다.");
		// 파일이 존재하는 경우 처리
		else if(!delete(file)) throw new Exception("삭제하려는 파일이 삭제되지 않았습니다.");
		else System.out.println("FileUtil.remove() - 파일이 삭제 되었습니다.");
		return true;
	}
	
	//서버의 상대주소를 넣으면 절대주소로 바꾸어 주는 메소드 만들기 리턴 : 스트링
	public static String getRealPath(String path,String fileName,HttpServletRequest request) {
		String filePath = path+File.separator+fileName;
		return request.getServletContext().getRealPath(filePath);
	}
	// 파일의 절대주소를 받아서 중복되지 않는 File객체를 리턴
	// 중복된 파일에 대한 처리 - 중복이 되지 않는 File 객체를 리턴해 준다.
	public static File noDuplicate(String fileFullName) throws Exception {
		System.out.println("FileUtil.noDuplicate().fileFullName = "+fileFullName);
		
		File file = null;
		
		int dotPos = fileFullName.lastIndexOf(".");
		// image.jpg - fileName : image, ext : .jpg
		String fileName = fileFullName.substring(0, dotPos); // D:\sts-4.11.1.RELEASE\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\travel_project\\upload\image\externalFile
		String ext = fileFullName.substring(dotPos); // .jpg
		
		// 파일 정보확인
		System.out.println("FileUtil.noDuplicate().fileName = " + fileName + ", ext = " + ext);
				
		/* 파일 중복 시, uuid붙이기*/
		if(toFile(fileFullName).exists()) {
			String uuid = UUID.randomUUID().toString();
			file = toFile(fileName+"_"+uuid+ext);
		}else {
			file = toFile(fileFullName);
		}
		
		return file;
	}
	// 파일 서버에 올리는 메서드 - FileUpload 라이브러리 사용
	public static Map<String,String> upload(final String PATH, MultipartFile multiFile, HttpServletRequest request) throws Exception {
		Map<String,String> fileNameMap = new HashMap<String, String>();
		String fileFullName = "";
		String th_fileFullName = "";
		log.info("[" + multiFile.getOriginalFilename() + "]");
		if(multiFile != null && !multiFile.getOriginalFilename().equals("")) {
			String fileName = multiFile.getOriginalFilename();
			// 서버의 파일명 중복을 배제한 File 객체
			File saveFile = noDuplicate(getRealPath(PATH, fileName, request));
			
			fileFullName = PATH + File.separator + saveFile.getName();
			fileNameMap.put("fileFullName", fileFullName);
			
			log.info("FileUtil.upload() : " + fileFullName);
			// 실제적인 업로드 되는 파일을 multiFile에 저장함
			multiFile.transferTo(saveFile);
			
			/* 원본 파일 저장 후, 썸네일 파일 저장 */
			File th_saveFile = new File(getRealPath(PATH,"th_"+fileName, request));
			th_fileFullName = PATH + File.separator + th_saveFile.getName();
			fileNameMap.put("th_fileFullName", th_fileFullName);
			
			BufferedImage bo_image = ImageIO.read(saveFile);
			//비율
			double ratio = 5;
			//넓이,높이
			int width = (int)(bo_image.getWidth()/ratio);
			int height = (int)(bo_image.getHeight()/ratio);
			Thumbnails.of(saveFile)
					  .size(width,height)
					  .toFile(th_saveFile);
			log.info("FileUtil.th_upload() : " + th_fileFullName);
		} else {
			//첨부파일이 없을때 noImage.jpg
			fileFullName = PATH + File.separator + "homepage_noimage.jpg";
		}
		return fileNameMap;
	}
	
}
