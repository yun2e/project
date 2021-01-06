package org.zerock.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//128p
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
	//613
	@GetMapping("/all")
	public void doAll() {
		log.info("do all can access everybody");
	}
	@GetMapping("/member")
	public void doMember() {
		log.info("logined member");
	}
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("admin only");
	}
	
	
	
	//135
	//방법1
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
//	}
	
//1.void : return이 없으므로 매핑 주소값을 찾아간다
	//http://localhost:8080/sample/ => /WEB-INF/views/sample.jsp
	//어디로 가라고 안써놓으면 url.jsp에 해당하는 부분을 자동으로 찾으러 간다
	@RequestMapping("")
	public void basic() {
		log.info("basic................................");
	}
	
	//http://localhost:8080/sample/basic => /WEB-INF/views/sample/basic.jsp
	@RequestMapping(value="/basic", method = {RequestMethod.GET,RequestMethod.POST})
	public void basicGet() {
		log.info("basic...get.............................");
	}
	
	//http://localhost:8080/sample/basicOnlyGet => /WEB-INF/views/sample/basicOnlyGet.jsp
	@RequestMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get.............................");
	}

	
//2.String : return 문자열의 주소를 찾아간다
	//http://localhost:8080/sample/ex01 => /WEB-INF/views/ex01.jsp
	//http://localhost:8080/sample/ex01?name=hong&age=18 => ex01.jsp ${(s!)ampleDTO.name}
	//Parameter type : 소문자시작(sampleDTO)
	@RequestMapping("/ex01")
	public String ex01(SampleDTO dto) { //dto: local variable
		log.info("SampleDTO" + dto);
		return "ex01";
	}
	
	@RequestMapping("/ex012")
	public String ex012(SampleDTO dto, Model model) { //dto: local variable
		log.info("SampleDTO" + dto);
		model.addAttribute(dto);
		return "ex012";
	}
	
	//http://localhost:8080/sample/ex011 => /WEB-INF/views/sample/ex011.jsp
	@RequestMapping("/ex011")
	public String ex011(SampleDTO dto) {
		log.info("SampleDTO" + dto);
		return "sample/ex011";
	}
	
//131p
	//http://localhost:8080/sample/ex02?name=hong&age=20
	@RequestMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name : "+name);
		log.info("age : "+age);
		return "ex02";
	}
	
//132p	
	//http://localhost:8080/sample/ex02List?ids=1111&ids=2222
	@RequestMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids : "+ids);
		return "ex02List";
	}
	
//133p	
	//http://localhost:8080/sample/ex02Array?ids=3333&ids=4444
	@RequestMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("ids : "+Arrays.toString(ids));
		return "ex02Array";
	}
	
//133p	
	//http://localhost:8080/sample/ex02Bean?list[0].name=aaaa&list[2].name=bbbb : ERROR발생(존재하지 않는 기호)
	//http://localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaaa&list%5B1%5D.name=bbbb
	@RequestMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dto : "+list);
		return "ex02Bean";
	}

	
	//135p	
//방법1(날짜 파라미터를 넘기는 방법)
	//@InitBinder
	//http://localhost:8080/sample/ex03?title=abcd&dueDate=2020-12-10
	@RequestMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("TodoDTO todo : "+todo);
		return "ex03";
	}
	
	//138p	
//방법2(날짜 파라미터를 넘기는 방법)
	//@DateTypeFormat
	//http://localhost:8080/sample/ex033?title=abcd&dueDate2=2020/12/10
	@RequestMapping("/ex033")
	public String ex033(TodoDTO todo) {
		log.info("TodoDTO todo : "+todo);
		return "ex033";
	}
	
	
	//139p
	//http://localhost:8080/sample/ex04?name=hong&age=20&page=17
	//객체안에 들어있는 값들은 전달이 가능하지만 일반 변수들은 전달 불가
	//page는 전달 불가 : int => @ModelAttribute를 사용해야한다
	@RequestMapping("/ex04")
	public String ex04(SampleDTO dto, int page) {
		log.info("dto : "+ dto);
		log.info("page : "+ page);
		return "sample/ex04";//view에 SampleDTO만 전달
	}
	
	@RequestMapping("/ex044")
	public String ex044(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto : "+ dto);
		log.info("page : "+ page);
		return "sample/ex044"; //view에 SampleDTO, page 모두 전달
	}
	
	//143p
	//http://localhost:8080/sample/redirect
	//redirectAttribute :: jsp에서 sendRedirect와 같은 역할, 일회성 전송
	@RequestMapping("/redirect")
	public String redirect(RedirectAttributes rttr) {
		log.info("RedirectAttribute.....");
		rttr.addFlashAttribute("msg", "success");
		rttr.addAttribute("attr", "hong");
		return "redirect:/sample/redirectAttribute";
	}
//	@RequestMapping("/redirect2")
//	public String redirect2(RedirectAttributes rttr) {
//		log.info("RedirectAttribute2.....");
//		rttr.addFlashAttribute("msg", "success");
//		rttr.addAttribute("attr", "hong");
//		return "redirect:/redirectAttribute2";
//	}
	
	//144p
	@RequestMapping("/ex05")
	public void ex05() {
		log.info("/ex05.......");
	}
	
	//146p
	//http://localhost:8080/sample/ex06
	//@ResponseBody : data를 view에 전달
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06.......");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		return dto; //ex06.jsp를 만들지 않고 화면에 직접 출력
	}
	
	//148p
	//http://localhost:8080/sample/ex07
	//ResponseEntity : header+data를 view에 전달
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07.......");
		String msg = "{\"name\" : \"홍길동\"}";
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(msg,header,HttpStatus.OK);
	}
	
	//150p
	@GetMapping("/exUpload")
	public void exUpload() {
		log.info("/exUpload.......");
	}
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> { //Lambda Expression
		log.info("/exUploadPost.......");
		log.info("name: " + file.getOriginalFilename());
		log.info("size : " + file.getSize());
		});
	}
	
	//154p
	//Exception : 고의적으로 에러 발생시키기
	//http://localhost:8080/sample/ex04?name=hong&age=20&page=17
	// => http://localhost:8080/sample/ex04?name=hong&age=aaaa
	
	//158p :: 404에러
	//http://localhost:8080/abc
}
