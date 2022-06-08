package com.vampa.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.vampa.mapper.AdminMapper;
import com.vampa.model.AttachImageVO;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class TaskTest {
	@Autowired
	private AdminMapper mapper;
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
	
	@Test
	public void taskTests() {
		List<AttachImageVO> fileList = mapper.checkFileList();
		
		System.out.println("fileList : ");
		fileList.forEach(list->System.out.println(list));
		System.out.println("=====================================");
		/* DB에 저장된 리스트와 디렉토리에 저장된 리스트를 비교하여 누락된 파일 찾기 */
		//비교를 위해 fileList를 Path객체로 변환(DB에 저장된 리스트임)
		List<Path> checkFilePath = new ArrayList<Path>();
		fileList.forEach(vo->{
			Path path = Paths.get("D:/dev/vamupload",vo.getUploadPath(),vo.getUuid()+"_"+vo.getFileName());
			checkFilePath.add(path);
		});
		System.out.println("checkFilePath : ");
		checkFilePath.forEach(list->System.out.println(list));
		System.out.println("===============================");
		
		//썸네일 이미지 Path객체가 checkFilePath 요소에 추가 되도록 해주기
		fileList.forEach(vo->{
			Path path = Paths.get("D:/dev/vamupload",vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName());
			checkFilePath.add(path);
		});
		
		System.out.println("checkFilePath(썸네일 이미지 정보 추가 후) : ");
		checkFilePath.forEach(list -> System.out.println(list));
		System.out.println("========================================");
		
		//체크할 대상의 이미지 파일이 저장된 디렉토리를 File객체로 생성
		File targetDir = Paths.get("D:/dev/vamupload",getFolderYesterDay()).toFile();
		File[] targetFile = targetDir.listFiles();
		System.out.println("targetFile : ");
		for(File file:targetFile) {
			System.out.println(file);
		}

		System.out.println("==============================");
		List<File> removeFileList = new ArrayList<File>(Arrays.asList(targetFile));
		System.out.println("removeFileList(필터 전) : ");	
		removeFileList.forEach(file -> {
			System.out.println(file);
		});		
		System.out.println("========================================");		
		/*targetFile과 checkFilePath와의 비교를 통해, 동일한 데이터를 가지고 있는 File객체 요소를 제거하여
		 * DB에 정보가 저장되지 않은 이미지 File객체만 남김 */
		for(File file : targetFile) {
			checkFilePath.forEach(checkFile->{
				if(file.toPath().equals(checkFile)) {
					removeFileList.remove(file);
				}
			});
		}
		System.out.println("removeFileList(필터 후 ) : DB에 저장안된 File만 남아야함");
		removeFileList.forEach(file->{
			System.out.println(file);
		});
		System.out.println("===================================");
		//삭제되어야할 File객체를 얻었음, File클래스의 delete()메서드 호출하여 해당 파일이 삭제되도록 하기
		/* 파일 삭제 */
		for(File file : removeFileList) {
			System.out.println("삭제 : " + file);
			file.delete();
		}
	}
}
