<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<!-- chart.js CDN -->
	<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
</head>
<body>
	<h2>Chart !</h2>
	
	<div style="width:600px; height:400px; border:2px solid black"> <!-- 차트를 그릴 위치를 지정하기 위해 캔버스 요소를 만든다 -->
        <canvas id="myChart"></canvas>								<!-- 차트 그릴 위치 지정 canvas webGL(그래픽엔진) 사용 -->
    </div>
    
    <script>
	    const ctx = document.querySelector('#myChart');
		
	    new Chart(ctx, {
		     type: 'bar',
		     data: {
		       	 labels	 : ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],	// X 축의  레이블 값은 배열로 제공돼야 함  
		      	 datasets: [{
				         label		: 'amount',					// 데이터셋의 라벨을 'amount'로 지정
				         data		: [12, 19, 3, 5, 2, 3],		// Y 축의 데이터 값들을 배열로 제공돼야 함 
				         borderWidth: 1							// 막대의 테두리 두께
		       }]
		     },
		     options: {
		       scales: {
		         y: {
		           beginAtZero: true		 // Y축이 0부터 시작하도록 설정
		         }
		       }
		     }
		   });
    </script>
</body>
</html>