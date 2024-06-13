package comUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {
	
	// 파일 업로드 
	public static String uploadFile(String originalName, byte[] bytes, String uploadPath) throws IOException {
		log.info("----- FileUtil uploadFile method Start -----");
		log.info("uploadPath : {}", uploadPath);
		
		UUID uid = UUID.randomUUID();	
		
		// 신규 폴더 (directory) 생성
		File fileDirectory = new File(uploadPath);
		if(!fileDirectory.exists()) {
			fileDirectory.mkdirs();
			log.info("업로드용 폴더 생성 : {}", uploadPath);
		}
		
		// 파일 이름 생성 
		int index = originalName.lastIndexOf(".");
		String extension = originalName.substring(index + 1);
		String nameWithoutExtension = originalName.substring(0, index);
		String savedName = nameWithoutExtension + "_" + uid.toString() + "." + extension;
		log.info("savedName : {}", savedName);
		
		File target = new File(uploadPath, savedName);
		Files.write(target.toPath(), bytes);
		
		return savedName;
	}
	
	// UUID 제거 
	public static String removeUUIDFromFileName(String fileName) { 
		log.info("------ FileUtil removeUUIDFromFileName start --------");
		int lastIndex = fileName.lastIndexOf("_"); 				// "_" 의 인덱스 찾음, 없을 경우 -1 반환 
		int extensionIndex = fileName.lastIndexOf(".");
				  
		if(lastIndex != -1 && extensionIndex != -1 && lastIndex < extensionIndex ) {
			return fileName.substring(0, lastIndex) + fileName.substring(extensionIndex); 
		}else { 
			return fileName; 
		} 
	}
	
	// 파일 삭제  
	public static void deleteFile(String uploadPath, String fileName) {
		log.info("------ FileUtil deleteFile1 start --------");
		
		File file = new File(uploadPath, fileName);
		if(file.exists()) {
			file.delete();
			log.info("파일 수정하기 위해 기존 파일: {} 삭제 완료!", fileName);
		}
	}

}
