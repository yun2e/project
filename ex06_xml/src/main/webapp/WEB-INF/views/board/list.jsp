<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="../includes/header.jsp" %>
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Tables</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board List Page
			<button type="button" id='regBtn' class="btn btn-xs pull-right">Register New Board</button>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th>#번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>수정일</th>
					</tr>
					</thead>
					
					<c:forEach items="${list}" var="board"> <!-- 35줄 -->
					<tr>
						<td><c:out value="${board.bno }" /></td>
						
						<%-- <td><a href="/board/get?bno=<c:out value='${board.bno }'/>">
						<c:out value='${board.title }'/></a></td> --%>
						
						<!-- 314p -->
						<td><a class='move' href='<c:out value="${board.bno }"/>'>
						<c:out value="${board.title }"/>
						<!-- 486 -->
						<b>[ <c:out value="${board.replyCnt }" /> ]</b>
						</a></td>
						
						<td><c:out value="${board.writer }" /></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate }" /></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate }" /></td>
					</tr>
					</c:forEach>	
				</table>
				
				<!-- 340 검색부분-->
				<div class="row">
					<div class="col-lg-12">
					
					<form id='searchForm' action="/board/list" method="get">
					<select name='type'>
						<option value=""
							<c:out value="${pageMaker.cri.type == null? 'selected' : '' }"/>>
						--</option>
						<option value="T"
							<c:out value="${pageMaker.cri.type eq 'T'? 'selected' : '' }"/>>
						제목</option>
						<option value="C"
							<c:out value="${pageMaker.cri.type eq 'C'? 'selected' : '' }"/>>
						내용</option>
						<option value="W"
							<c:out value="${pageMaker.cri.type eq 'W'? 'selected' : '' }"/>>
						작성자</option>
						<option value="TC"
							<c:out value="${pageMaker.cri.type eq 'TC'? 'selected' : '' }"/>>
						제목 or 내용</option>
						<option value="TW"
							<c:out value="${pageMaker.cri.type eq 'TW'? 'selected' : '' }"/>>
						제목 or 작성자</option>
						<option value="TCW"
							<c:out value="${pageMaker.cri.type eq 'TCW'? 'selected' : '' }"/>>
						제목 or 내용 or 작성자</option>
					</select>
					<input type="text" name='keyword' 
					value='<c:out value="${pageMaker.cri.keyword}"/>' />
					<!-- cout된 값을 value로 갖게된다. keyword를 가지게 된다면 -->
					<input type="hidden" name='pageNum' 
					value='<c:out value="${pageMaker.cri.pageNum}"/>' />
					<input type="hidden" name='amount' 
					value='<c:out value="${pageMaker.cri.amount}"/>' />
					<button class='btn btn-default'>Search</button>
					</form>	
					</div>
				</div>
				
				<!-- 308,310p -->
				<div class='pull-right'>
					<ul class="pagination">
						<c:if test="${pageMaker.prev }">
							<li class="paginate_button previous">
							<a href="${pageMaker.startPage -1}">Previous</a></li>
						</c:if>
						<c:forEach var="num" begin="${pageMaker.startPage}"
						end="${pageMaker.endPage}">
						<li class='paginate_button ${pageMaker.cri.pageNum==num ? "active":""}'>
						<a href="${num}">${num}</a></li>
						</c:forEach>
						<c:if test="${pageMaker.next }">
							<li class="paginate_button next">
							<a href="${pageMaker.endPage +1 }">Next</a></li>
						</c:if>
					</ul>
				</div>
				
				<!-- 311p -->
				<form id="actionForm" action="/board/list" method="get">
					<input type="hidden" name='pageNum' value="${pageMaker.cri.pageNum}">
					<input type="hidden" name='amount' value="${pageMaker.cri.amount}">
				<!-- 344p -->
					<input type="hidden" name='type' 
					value='<c:out value="${pageMaker.cri.type}"/>'>
					<input type="hidden" name='keyword' 
					value='<c:out value="${pageMaker.cri.keyword}"/>'>
				</form>
				
				<!-- 248p -->
			 	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
				aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="myModalLabel">Modal Title</h4>
						</div>
						<div class="modal-body">처리가 완료되었습니다.</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary">Save changes</button>
						</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>
<!-- 246,250 -->
<script type="text/javascript">
	$(document).ready(function(){
		var result = '<c:out value="${result}"/>'; //register했을 때 등장
		checkModal(result);
		
		history.replaceState({},null,null);
		
		function checkModal(result){
			if(result==='' || history.state){
				return;
			}
			if(parseInt(result) > 0){
				$(".modal-body").html("게시글 : "+parseInt(result)+"번이 등록되었습니다.");
			}
			$("#myModal").modal("show");
		}
		
		$("#regBtn").on("click",function(){
			self.location = "/board/register";
		});
		
		//312p
		var actionForm = $("#actionForm");
		
		//페이지 이동
		$(".paginate_button a").on("click",function(e){
			e.preventDefault();//a태그를 클릭해도 페이지 이동이 없도록 하는 부분
			
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit();
		});
		
		//게시글 이동
		$(".move").on("click",function(e){
			e.preventDefault();//a태그를 클릭해도 페이지 이동이 없도록 하는 부분
			actionForm.append("<input type='hidden' name='bno' value='"+
					$(this).attr("href")+"'>");
			actionForm.attr("action","/board/get");
			actionForm.submit();
		});
		
		//342p
		var searchForm = $("#searchForm");
		
		$("#searchForm button").on("click", function(e){
			if(!searchForm.find("option:selected").val()){
				alert("검색종류를 선택하세요");
				return false;
			}
			if(!searchForm.find("input[name='keyword']").val()){
				alert("키워드를 입력하세요");
				return false;
			}
			//pageNum을 id로 갖는 것을 찾아서 그 값을 1로 만들기 -> 1페이지로 이동
			searchForm.find("input[name='pageNum']").val("1");
			e.preventDefault();//a태그를 클릭해도 페이지 이동이 없도록 하는 부분
			
			searchForm.submit();
		});
	});
</script>
<%@ include file="../includes/footer.jsp" %>