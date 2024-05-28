<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<!-- chart.js CDN -->
	<script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
	
	<!-- jquery 사용하기 위해 head 안에 CDN 추가 -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> 
	<script type="text/javascript">
	
		$(function(){
			$.ajax({
				type 		: 'get'
				,url 		: 'getSalesData'
				,async 		: 'true'
				,contentType: 'application/json'
				,dataType   : 'json'  				// 서버에서 반환하는 데이터 타입을 JSON으로 지정
				,success 	: function(data){
					console.log("data-> ", data); 
					console.log(JSON.stringify(data, null, 2));  // JSON 문자열로 변환하여 읽기 쉽게 출력
					
					// 월과 매출 데이터를 배열로 분리 
					var months = data.map(function(item){
						return item.month;
					});
					var sales = data.map(function(item){
						return item.sales;
					})
					
					// div와 canvas 생성 
					var div = $('<div>', {
						 css: {
							 width	: '600px',
			                 height	: '400px',
			                 border	: '2px solid black'
						 }
					});
					var canvas = $('<canvas>', {
						id 	: 'myChart'
					}) ;
					
					div.append(canvas);
					$('body').append(div);
					
					// Chart.js 막대 차트 생성
					var ctx = document.getElementById('myChart').getContext('2d');
					var myChart = new Chart(ctx, {
						type : 'bar',
						data: {
		                    labels: months,
		                    datasets: [{
		                        label: 'Monthly Sales',
		                        data: sales,
		                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
		                        borderColor: 'rgba(75, 192, 192, 1)',
		                        borderWidth: 1
		                    }]
		                },
		                options: {
		                    scales: {
		                        y: {
		                            beginAtZero: true
		                        }
		                    }
		                }
		            });
		        },
				error: function(xhr, status, error) {
					console.error("AJAX Error: ", status, error); 
				}
			});
		});
	
	</script>
</head>
<body>
	<h2>Monthly Sales Chart !</h2>
</body>
</html>