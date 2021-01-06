<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 501 -->

<h1>Upload with Ajax</h1>

<div class='uploadDiv'>
	<input type="file" name="uploadFile" multiple>
</div>

<!-- 542 -->
<div class='bigPictureWrapper'>
	<div class='bigPicture'>
	</div>
</div>
<!-- 524,542 -->
<style>
.uploadResult {
	width : 100%;
	background-color: pink;
}

.uploadResult ul {
	display: flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
}

.uploadResult ul li img {
	width: 20px;
}

.uploadResult ul li span{
	color: white;
}

.bigPictureWrapper {
	position: absolute;
	display: none;
	justify-content: center;
	align-items: center;
	top: 0%;
	width: 100%;
	height: 100%;
	background-color: purple;
	z-index: 100;
	background: rgba9255,255,255,0.5);
}
.bigPicture {
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}
.bigPicture img {
	width: 600px;
}
</style>

<!-- 522 -->
<div class="uploadResult">
	<ul>	
	
	</ul>
</div>

<button id="uploadBtn">Upload</button>
<script
  src="https://code.jquery.com/jquery-3.3.1.min.js"
  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  crossorigin="anonymous"></script>
 
<script>

	//540, 543
	function showImage(fileCallPath){
		//alert(fileCallPath);
		$(".bigPictureWrapper").css("display","flex").show();
		
		$(".bigPicture")
		.html("<img src='/display?fileName="+encodeURI(fileCallPath)+"'>")
		.animate({width:'100%', height:'100%'},1000);
		
		$(".bigPictureWrapper").on("click",function(e){
			$(".bigPicture").animate({width:'0%', height:'0%'},1000);
			setTimeout(function(){
				$(".bigPictureWrapper").hide();
			},1000);
		});
	}
	
	$(document).ready(function(){
		
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880; //5MB
		
		function checkExtension(fileName, fileSize){
			if(fileSize>=maxSize){
				alert("파일 사이즈 초과");
				return false;
			}	
			
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			return true;
		}
		
		//520
		var cloneObj = $(".uploadDiv").clone();
		
		$("#uploadBtn").on("click", function(e){
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log(files);
			
	//503
		//for(var i = 0; i<files.length; i++){
		//	formData.append("uploadFile", files[i]);
		//}
	//507
		for(var i = 0; i<files.length; i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			formData.append("uploadFile",files[i]);
		}
		
		//519, 521, 523수정
		$.ajax({
			url : '/uploadAjaxAction',
			processData : false,
			contentType : false,
			data : formData,
			type : 'POST',
			dataType : 'json',
			success : function(result){
				console.log(result);
				
				showUploadedFile(result);
				
				$(".uploadDiv").html(cloneObj.html());
			}
		});//$.ajax
		
		//522
		var uploadResult = $(".uploadResult ul");
		//547
		$(".uploadResult").on("click","span",function(e){
			var targetFile = $(this).data("file");
			var type = $(this).data("type");
			console.log(targetFile);
			$.ajax({
				url : '/deleteFile',
				data : {fileName : targetFile, type : type},
				dataType : 'text',
				type : 'POST',
				success : function(result){
					alert(result);
				}
			});// #.ajax
		});
		
		function showUploadedFile(uploadResultArr){
		var str ="";
		$(uploadResultArr).each(function(i,obj){
			//525    str += "<li>"+obj.fileName + "</li>";
			if(!obj.image){
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
				//str += "<li><img src='/resources/img/attach.png'>"+obj.fileName+"</li>";
				str += "<li><div><a href='download?fileName="+fileCallPath+"'>"+
						"<img src='/resources/img/attach.png'>"+obj.fileName+"</a>"+
						"<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span></div></li>";
			} else{
				//str += "<li>"+obj.fileName + "</li>";
				//528
				var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
				
				//541
				var originPath = obj.uploadPath+"\\"+obj.uuid+"_"+obj.fileName;
				originPath = originPath.replace(new RegExp(/\\/g),"/");
				
				str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src='/display?fileName="+fileCallPath+"'</a>"+
				"<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span></li>";
			}
		});
		uploadResult.append(str);
		}
	});
});
</script>

</body>
</html>