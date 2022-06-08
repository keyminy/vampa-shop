package com.vampa.task;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vampa.mapper.AdminMapper;
import com.vampa.model.AttachImageVO;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AttachFileCheckTask {

	@Autowired
	private AdminMapper mapper;
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		return str.replace("-",File.separator);
	}
	
	@Scheduled(cron="0 * * * * *")
	public void checkFiles() throws Exception{
		log.warn("File Check Task Run.........");
		log.warn(new Date());
		log.warn("============================");
		
List<AttachImageVO> fileList = mapper.checkFileList();
		
		/* DB에 저장된 리스트와 디렉토리에 저장된 리스트를 비교하여 누락된 파일 찾기 */
		//비교를 위해 fileList를 Path객체로 변환(DB에 저장된 리스트임)
		List<Path> checkFilePath = new ArrayList<Path>();
		fileList.forEach(vo->{
			Path path = Paths.get("D:/dev/vamupload",vo.getUploadPath(),vo.getUuid()+"_"+vo.getFileName());
			checkFilePath.add(path);
		});
		
		//썸네일 이미지 Path객체가 checkFilePath 요소에 추가 되도록 해주기
		fileList.forEach(vo->{
			Path path = Paths.get("D:/dev/vamupload",vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName());
			checkFilePath.add(path);
		});
		
		
		//체크할 대상의 이미지 파일이 저장된 디렉토리를 File객체로 생성
		File targetDir = Paths.get("D:/dev/vamupload",getFolderYesterDay()).toFile();
		File[] targetFile = targetDir.listFiles();

		List<File> removeFileList = new ArrayList<File>(Arrays.asList(targetFile));
		/*targetFile과 checkFilePath와의 비교를 통해, 동일한 데이터를 가지고 있는 File객체 요소를 제거하여
		 * DB에 정보가 저장되지 않은 이미지 File객체만 남김 */
		for(File file : targetFile) {
			checkFilePath.forEach(checkFile->{
				if(file.toPath().equals(checkFile)) {
					removeFileList.remove(file);
				}
			});
		}
		//삭제되어야할 File객체를 얻었음, File클래스의 delete()메서드 호출하여 해당 파일이 삭제되도록 하기
		/* 파일 삭제 */
		for(File file : removeFileList) {
			log.warn("삭제 : " + file);
			file.delete();
		}
		log.warn("===========================");
	}
}
