package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

//494
@Controller
@Log4j
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload form");
	}
	
	//497
	@PostMapping("uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "C:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
		log.info("---------------------------------------------------------");
		log.info("Upload File Name : " + multipartFile.getOriginalFilename());
		log.info("Upload File size : " + multipartFile.getSize());
		
	//499
		File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
		
		try {
			multipartFile.transferTo(saveFile);
		} catch (Exception e) {
			log.error(e.getMessage());
		}//end catch
		}//end for
	}
	
	
	//500
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload ajax");
	}
	
	//513
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//508
		private String getFolder() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date date = new Date();
			
			String str = sdf.format(date);
			return str.replace("-", File.separator);
		}
		
	//504,508,517,724
	@PreAuthorize("isAuthenricated()")
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile){
		//log.info("update ajax post.............");
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "c:\\upload";
		
		String uploadFolderPath = getFolder();
		
		//make folder...508
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); //지정된 폴더가 존재하는 경우 여러개의 디렉토리 생성
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			//log.info("---------------------------------------------------------");
			//log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			//log.info("Upload File size : " + multipartFile.getSize());
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			log.info("only file name : "+uploadFileName);
			
			attachDTO.setFileName(uploadFileName);
			
			//511
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString()+"_"+uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				//518
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				//514,518
				if(checkImageType(saveFile)) {
					
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail,100,100);
					thumbnail.close();		
				}
				list.add(attachDTO);
			} catch (Exception e) {
				log.error(e.getMessage());
			}//end catch
		}//end for
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//526
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName : "+fileName);
		File file = new File("c:\\upload\\"+fileName);
		log.info("file : "+file);
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//531, 532, 533,539 수정
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		log.info("download file : "+fileName);
		
		//upload파일 자체에 있어야 한다!
		Resource resource = new FileSystemResource("c:\\upload\\"+fileName);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		//log.info("resource : "+resource);
		
		//532
		String resourceName = resource.getFilename();
		
		//539
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		
		HttpHeaders headers = new HttpHeaders();
		try {	
			//headers.add("Content-Disposition", "attachement; filename="+new String(resourceName.getBytes("UTF-8"),"ISO-8859-1"));
			String downloadName = null;
			if(userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
			}else if(userAgent.contains("Edge")){
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
			} else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			}
			log.info("downloadName : "+downloadName);
			headers.add("Content-Disposition", "attachment; filename="+downloadName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	//548,724
	@PreAuthorize("isAuthenricated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile : "+fileName);
		
		File file;
		
		try {
			file = new File("c:\\upload\\"+URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName : "+largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
}
