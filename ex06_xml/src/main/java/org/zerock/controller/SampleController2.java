package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

//@RestController : @Controller+@ResponseBody
@RestController
@Log4j
@RequestMapping("/sample2")
public class SampleController2 {

//358p
//1.String인 경우
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE : "+MediaType.TEXT_PLAIN_VALUE);
		return "안녕하세요";
	}
	
//2.객체
	//XML 형식(기본 default) :: http://localhost:8080/sample2/getSample
	//JSON 형식 :: http://localhost:8080/sample2/getSample.json
	@GetMapping(value = "/getSample",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112,"스타","로드");
	}

//3.Collection 타입의 객체변환
	//XML 형식(기본 default) :: http://localhost:8080/sample2/getList
	//JSON 형식 :: http://localhost:8080/sample2/getList.json
	@GetMapping(value = "/getList")
	public List<SampleVO> getList(){
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i+"First", i+"Last"))
				.collect(Collectors.toList());
	}
//364	
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111,"그루트","주니어"));
		return map;
	}
	
//365
//4. ResponseEntity<SampleVO> : data + HTTP Status Code8
	//http://localhost:8080/sample2/check?height=168&weight=56
	@GetMapping(value = "/check", params = {"height","weight"})
	public ResponseEntity<SampleVO> check(Double height, Double weight){
		SampleVO vo = new SampleVO(0, ""+height, ""+weight);
		ResponseEntity<SampleVO> result = null;
		
		if(height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		
		return result;
	}
	
//366
//5. @PathVariable : 경로 + 값
	//http://localhost:8080/sample2/product/wow/1
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		return new String[] {"category : "+cat, "productid : "+pid};
	}
	
//368
//6.@RequestBody
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert.............ticket"+ticket);
		return ticket;
	}
}
