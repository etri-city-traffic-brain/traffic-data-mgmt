package net.etri.rest.api.schedule;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
  <p>클래스 설명 : 다운로드 및 업로드 처리를 위해 생성한 임시파일 삭제 스케줄</p>
  <p>TempFileDeleteSchedule</p>
  <pre>
   net.etri.rest.api.schedule
          └ TempFileDeleteSchedule.java
  </pre>
**/
@Component
public class TempFileDeleteSchedule {

	@Value("#{props['upload.store.path']}")
	private String storePath;
	@Value("#{props['download.store.path']}")
	private String downPath;
	
	/**
	  스케줄 실행 함수
	 @exception Exception 스케줄 실행간 발생하는 예외 반환	 
	*/
	public void executeJob() throws Exception {
		deleteDownloadTempFile(downPath);
    }
	/**
	  다운로드 처리를 위하여 전일에 생성한 임시파일을 삭제 한다.
	 @param path 삭제대상 경로
	*/
	private void deleteDownloadTempFile(String path){
		// TODO : 대상 경로상의 파일 및 폴더가 마지막 수정시간이 하루이상 경과되었을 경우 삭제처리
		File dir = new File(path);
		if(!dir.exists() || !dir.isDirectory())
			return;
		
		File[] files = dir.listFiles();
		for(int i = 0; i < files.length; i++){
			File dirTemp = files[i];
			if(!dirTemp.isDirectory())
				continue;
			
			Date mod = new Date(dirTemp.lastModified());
			Date now = new Date();
			long diff = now.getTime() - mod.getTime();
			
			if( (diff/(1000*60*60)) > 23 ){			
				File[] tempfiles = dirTemp.listFiles();
				for(int j = 0; j < tempfiles.length; j++){
					tempfiles[j].delete();
				}
				dirTemp.delete();
			}
		}
	}
}
