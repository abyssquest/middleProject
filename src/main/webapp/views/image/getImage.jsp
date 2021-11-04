<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>getImage</title>
	
	<style type="text/css">
		td { font-style:italic; }
		a { text-decoration:none; }
		a { color: #151719; }
		p { color: #151719; }
		
	</style>
</head>
<body>
	<jsp:include page="/module/log.jsp"/>
	
	<p>getImage 페이지</p>
	
	<p>이미지 번호 : ${ image.seq }</p>
	<p>이미지 타이틀 : ${ image.title }</p>
	<p>이미지 컨텐츠 : ${ image.content }</p>
	<p>이미지 주인 id : ${ image.masterId }</p>
	<p>이미지 업로드날짜 : ${ image.uploadDate }</p>
	<img src="/biz/save_file/${ image.uploadFileName }">
	
		 <div class="comment-post comment_box" id="div_21314623" c_idx="21314623" c_type="goods_estimate">
                                    <div class="comment_recomment comment_wrap">
                         <div class="recomment_reply">
                            <p class="recomment_profile">
                                                                <span><b class="txt-info">댓글쓴이</b></span>
					        </p>
                        </div>
                        <div class="recomment_right">
                        
                                        <p class="recomment_txt">
                                        <span class="cmt_summary"
                                              more_flag="Y"
                                              style='cursor:pointer' cmt_no="5952494"
                                              cmt_type="goods_estimate">
											댓글 본문</span>
                                         </p>
                        </div>
                    </div>
                 </div>
	
	<table cellpadding="0" cellspacing="0">
	<tr align="center" valign="middle">
		<td colspan="5">댓글</td>
	<tr>
		<td style="font-family:돋음; font-size:12">
			<div align="center">내 용</div>
		</td>
		<td>
			<textarea name="boardContent" cols="100" rows="1"></textarea>
		</td>
	</tr>
	<tr bgcolor="cccccc">
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" valign="middle">
		<td colspan="5">
		<a href="javascript:replyboard()">[등록]</a>&nbsp;&nbsp;
		<a href="javascript:history.go(-1)">[뒤로]</a>
		</td>
	</tr>
	</table>
	
	<p><a href="getImageList.do">이미지 목록</a></p>
	
	<p><a href="deleteImage.do?seq=${ image.seq }">이미지 삭제</a></p>
	
	<p>
		스프링 콘솔 창을 보세요
		<a href="index.do">인덱스 창</a>
	</p>
	
	<jsp:include page="/module/foot.jsp"/>
	
	<script language="javascript">
		function replyboard(){
			boardform.submit();
		}
		
		window.onload = function(){	// onload는 html 바디영역에 있는 모든 영역을 읽어가는 시점에 발생하는 이벤트
			var submit = document.getElementById('submit');
			
			// 이벤트 연결
			function whenClick(){	//whenClick이라는 함수. alert라는 함수를 통해 뜨게끔.
				alert('댓글이 등록되었습니다.');
			}
			
			submit.onclick = whenClick;	// 본문 Click 누르면 click 팝업창 나옴.
		};
	</script>
</body>
</html>