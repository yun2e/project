<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ include file="../includes/header.jsp" %>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Read Page</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			<div class="panel-body">

			<div class="form-group">
			<label>Bno</label><input class="form-control" name='bno'
			value="<c:out value='${board.bno}'/>" readonly="readonly">
			</div>
			
			<div class="form-group">
			<label>Title</label><input class="form-control" name='title'
			value="<c:out value='${board.title}'/>" readonly="readonly">
			</div>
			
			<div class="form-group">
			<label>Text area</label>
			<textarea rows="3" class="form-control" name='content'
			readonly="readonly"><c:out value="${board.content}"/></textarea>
			</div>
			
			<div class="form-group">
			<label>Writer</label><input class="form-control" name='writer'
			value="<c:out value='${board.writer}'/>" readonly="readonly">
			</div>
			
			<!-- 717 -->
			<sec:authentication property="principal" var="pinfo"/>
				<sec:authorize access="isAuthenticated()">
					<c:if test="${pinfo.username eq board.writer }">
						<button data-oper='modify' class="btn btn-default">Modify</button>
					</c:if>
				</sec:authorize>
			
			<button data-oper='list' class="btn btn-info">List</button>
			
			<!-- 264p -->
			<form id='openForm' action="/board/modify" method="get">
				<input type="hidden" id='bno' name='bno' value='<c:out value="${board.bno}"/>'>
			<!-- 317p -->
				<input type="hidden" name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
				<input type="hidden" name='amount' value='<c:out value="${cri.amount}"/>'>
			<!-- 345 -->
				<input type="hidden" name='keyword' value='<c:out value="${cri.keyword}"/>'>
				<input type="hidden" name='type' value='<c:out value="${cri.type}"/>'>
			</form>
			
			</div>
		</div>
	</div>
</div>

<!-- 572 -->
<div class='bigPictureWrapper'>
	<div class='bigPicture'>
	</div>
</div>

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
	align-content: center;
	text-align: center;
}

.uploadResult ul li img {
	width: 100px;
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

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Files</div>
			<div class="panel-body">
				<div class="uploadResult">
					<ul>
					
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 414 -->
<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i>Reply
				
				<!-- 718 -->
				<sec:authorize access="isAuthenticated()">
				<!-- 419 -->
				<button id="addReplyBtn" class='btn btn-primary btn-xs pull-right'>New Reply</button>
				</sec:authorize>

				
			</div>
			<div class="panel-body">
			<ul class="chat">
				<li class="left clearfix" data-rno='12'>
					<div>
						<div class="header">
							<strong class="primary-font">user00</strong>
							<small class="pull-right text-muted">2018-01-01 13:13</small>
						</div>
						<p>Good job!</p>
					</div>
				</li>
			</ul>
		</div>
		
<!-- 439 -->
	<div class="panel-footer">
	
	</div>
</div>
</div>
</div>

<!-- 420 -->
<!-- Modal -->
<div class='modal fade' id='myModal' tabindex='-1' role='dialog' 
aria-labelledby='myModalLabel' aria-hidden='true'>
	<div class='modal-dialog'>
		<div class='modal-content'>
			<div class='modal-header'>
				<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
			</div>
			<div class='form-group'>
				<label>Reply</label>
				<input class='form-control' name='reply' value='New Reply!!!!!'>
			</div>
			<div class='form-group'>
				<label>Replyer</label>
				<input class='form-control' name='replyer' value='replyer'>
			</div>
			<div class='form-group'>
				<label>Reply Date</label>
				<input class='form-control' name='replyDate' value="">
			</div>
		<div class="modal-footer">
			<button type="button" id='modalModBtn' class="btn btn-warning">Modify</button>
			<button type="button" id='modalRemoveBtn' class="btn btn-danger">Remove</button>
			<button type="button" id='modalRegisterBtn' class="btn btn-primary">Register</button>
			<button type="button" id='modalCloseBtn' class="btn btn-default"
			data-dismiss="modal">Close</button>
		</div>
		</div> <!-- modal-content -->
	</div> <!-- modal-dialog -->
</div> <!-- modal -->

<!-- 400 -->
<script type="text/javascript" src="/resources/js/reply.js"></script>

<!-- 415 -->
<script>
	$(document).ready(function () {
		
		var bnoValue = '<c:out value="${board.bno}"/>';
		var replyUL = $(".chat");
		
		showList(1);
		
		function showList(page){
			//438
			console.log("show list : "+page);
																		//438
			replyService.getList({bno:bnoValue, page: page||1}, function(replyCnt, list){
				
				//438
				console.log("replyCnt : "+ replyCnt);
				console.log("list : "+ list);
				console.log(list);
				
				if(page==-1){
					pageNum = Math.ceil(replyCnt/10.0);
					showList(pageNum);
					return;
				}
				
				var str="";
				if(list == null || list.lenth == 0){
					replyUL.html("");
				
					return;
				}
				for(var i = 0, len = list.length || 0; i<len; i++){
					str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
					str += "	<div><div class='header'><strong class='primary-font'>["
						+list[i].rno+"]"+list[i].replyer+"</strong>";
					str += "	<small class='pull-right text-muted'>"
					+replyService.displayTime(list[i].replyDate)+"</small></div>";
					str += "	<p>"+list[i].reply+"</p></div></li>";
				}
				replyUL.html(str);
				//441
				showReplyPage(replyCnt);
			});//end function
		}//end showList
		
		//440
		var pageNum = 1;
		var replyPageFooter = $(".panel-footer");
		
		function showReplyPage(replyCnt){
			var endNum = Math.ceil(pageNum / 10.0) * 10;
			var startNum = endNum - 9;
			
			var prev = startNum != 1;
			var next = false;
			
			if(endNum * 10 >= replyCnt){
				endNum = Math.ceil(replyCnt/10.0);
			}
			
			if(endNum * 10 < replyCnt){
				next = true;
			}
			
			var str = "<ul class='pagination pull-right'>";
			
			if(prev){
				str += "<li class='page-item'><a class ='page-link' href='"
				+(startNum -1)+"'>Previous</a></li> ";
			}
			
			for(var i = startNum; i<=endNum; i++){
				var active = pageNum == i ? "active":"";
				
				str += "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>"+i+"</a></li>";
			}
			
			if(next){
				str += "<li class='page-item'><a class='page-link' href='"+(endNum + 1)+"'>Next</a></li> ";
			}
			
			str += "</ul></div>"
			
			console.log(str);
			replyPageFooter.html(str);
		}
		
		//422
		var modal = $(".modal");
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		
		//727
		var replyer = null;
		<sec:authorize access="isAuthenticated()">
		replyer = '<sec:authentication property="principal.username" />';
		</sec:authorize>
		
		var csrfHeaderName = "${_csrf.headerName}";
		var csrfTokenValue = "${_csrf.token}";
		
		//728
		$("#addReplyBtn").on("click",function(e){
			modal.find("input").val("");
			modal.find("input[name='replyer']").val(replyer);
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id != 'modalCloseBtn']").hide();
			
			modalRegisterBtn.show();
			
			$(".modal").modal("show");
		});
		
		//728
		//Ajax spring security header...
		$(document).ajaxSend(function(e,xhr, options){
			xhr.setRequestHeader(csrfHeaderName,csrfTokenValue);
		});
		
		//423, 439
		modalRegisterBtn.on("click", function(e){
			var reply = {
					reply : modalInputReply.val(),
					replyer : modalInputReplyer.val(),
					bno:bnoValue
			};
			replyService.add(reply, function(result){
				alert(result);
			
				modal.find("input").val("");
				modal.modal("hide");
			
			//showList(1);
			showList(-1);		
			});
		});
		//425
		$(".chat").on("click","li",function(e){
			var rno = $(this).data("rno");
			console.log(rno);
			
			replyService.get(rno, function(reply){
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly","readonly");
				modal.data("rno",reply.rno);
				
				modal.find("button[id != 'modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				
				$(".modal").modal("show");			
			});
		});
			//426(Modify)
			modalModBtn.on("click", function(e){
				var originalReplyer = modalInputReplyer.val();
				
				var reply = {rno:modal.data("rno"), reply:modalInputReply.val(), replyer: originalReplyer};
				
				if(!replyer){
					alert("로그인 후 수정이 가능합니다.");
					modal.modal("hide");
					return;
				}
				
				console.log("Original Replyer : "+originalReplyer);
				
				if(replyer != originalReplyer){
					alert("자신이 작성한 댓글만 삭제 가능합니다.");
					modal.modal("hide");
					return;
				}
				
				replyService.update(reply,function(result){
					alert(result);
					modal.modal("hide");
					showList(pageNum);
				});
			});

			//426(Remove), 730
			modalRemoveBtn.on("click", function(e){
				var rno = modal.data("rno");
				
				console.log("RNO : "+rno);
				console.log("REPLYER : "+replyer);
				
				if(!replyer){
					alert("로그인 후 삭제가 가능합니다.");
					modal.modal("hide");
					return;
				}
				
				var originalReplyer = modalInputReplyer.val();
				
				console.log("Original Replyer : "+originalReplyer);
				
				if(replyer != originalReplyer){
					alert("자신이 작성한 댓글만 삭제 가능합니다.");
					modal.modal("hide");
					return;
				}
				
				replyService.remove(rno,function(result){
					alert(result);
					modal.modal("hide");
					showList(pageNum);
				});
			});
			
			//441
			replyPageFooter.on("click","li a", function(e) {
				e.preventDefault();
				console.log("page click");
				
				var targetPageNum = $(this).attr("href");
				
				console.log("targetPageNum : " + targetPageNum);
				
				pageNum = targetPageNum;
				
				showList(pageNum);
			})
	
});
</script>

<!-- 404 -->
<script>
console.log("===========");
console.log("JS TEST");

var bnoValue = '<c:out value="${board.bno}"/>';

/* //404
//for replyService add test
replyService.add(
		{reply:"JS TEST", replyer:"tester", bno:bnoValue}
		,
		function(result){
			alert("RESULT : "+result);
		}
	);
	
//407
//reply List Test
replyService.getList({bno:bnoValue, page:1}, function(list){
	for(var i = 0, len = list.length||0; i<len; i++){
		console.log(list[i]);
	}
});

//409
//n번 댓글 삭제 테스트
replyService.remove(24, function(count){
	console.log(count);
	
	if(count === "success"){
		alert("REMOVED");
	}
}, function(err){
	alert('ERROR...');
});

//411
//n번 댓글 수정
replyService.update({
	rno : 17,
	bno : bnoValue,
	reply : "Modified Reply..."
	},function(result){
	alert("수정완료...");
});

//413
replyService.get(11, function(data){
	console.log(data);
}); */

</script>
<!-- 402 -->
<script type="text/javascript">
$(document).ready(function(){
	
	console.log(replyService);
});
</script>


<!-- 265 -->
<script type="text/javascript">
	$(document).ready(function(){
		
		var openForm = $("#openForm");
		
		$("button[data-oper='modify']").on("click", function(e){
			openForm.attr("action","/board/modify").submit();
		});
		
		$("button[data-oper='list']").on("click", function(e){
			openForm.find("#bno").remove(); //bno값만 지우고 나머지 값을 다 가진채로 list로 이동
			openForm.attr("action","/board/list");
			openForm.submit();
		});
	});
</script>

<!-- 570 -->
<script>


$(document).ready(function(){
  
  (function(){
  
    var bno = '<c:out value="${board.bno}"/>';
    
    /* $.getJSON("/board/getAttachList", {bno: bno}, function(arr){
    
      console.log(arr);
      
      
    }); *///end getjson
    $.getJSON("/board/getAttachList", {bno: bno}, function(arr){
        
       console.log(arr);
       
       var str = "";
       
       $(arr).each(function(i, attach){
       
         //image type
         if(attach.fileType){
           var fileCallPath =  encodeURIComponent( attach.uploadPath+ "/s_"+attach.uuid +"_"+attach.fileName); 
           str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
           str += "<img src='/display?fileName="+fileCallPath+"'>";
           str += "</div>";
           str +="</li>";
         }else{          
           str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' data-filename='"+attach.fileName+"' data-type='"+attach.fileType+"' ><div>";
           str += "<span> "+ attach.fileName+"</span><br/>";
           str += "<img src='/resources/img/attach.png'></a>";
           str += "</div>";
           str +="</li>";
         }
       });    
       $(".uploadResult ul").html(str);         
     });//end getjson   
  })();//end function
  
  //575
  $(".uploadResult").on("click","li",function(e){
	 console.log("view image");
	 var liObj = $(this);
	 
	 var path = encodeURIComponent(liObj.data("path")+"/"+liObj.data("uuid")+"_"+liObj.data("filename"));
	 
	 if(liObj.data("type")){
		 showImage(path.replace(new RegExp(/\\/g),"/"));
	 } else {
		 //download
		 self.location = "/download?fileName="+path;
	 }
  });
  
  function showImage(fileCallPath){
	  alert(fileCallPath);
	  $(".bigPictureWrapper").css("display","flex").show();
	  
	  $(".bigPicture")
	  .html("<img src = '/display?fileName="+fileCallPath+"'>")
	  .animate({width:'100%', height:'100%'},1000);
  }
  
  $(".bigPictureWrapper").on("click",function(e){
	  $(".bigPicture").animate({width:'0%', height:'0%'},1000);
	  setTimeout(function(){
		  $(".bigPictureWrapper").hide();},1000);
  });
});
</script>

<%@ include file="../includes/footer.jsp" %>