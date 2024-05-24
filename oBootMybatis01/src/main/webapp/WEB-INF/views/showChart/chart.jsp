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
	<h2>Bar Chart !</h2>
	
	<div style="width:600px; height:400px; border:2px solid black"> <!-- 차트를 그릴 위치를 지정하기 위해 캔버스 요소를 만든다 -->
        <canvas id="myChart"></canvas>								<!-- 차트 그릴 위치 지정 canvas webGL(그래픽엔진) 사용 -->
    </div>
    
    <script> 
        fetch('getSalesData') 								<!-- 'getSalesData' 엔드포인트(=url)로부터 데이터를 가져옴 -->
            .then(response => response.json()) 				<!-- 응답을 JSON 형식으로 변환 -->
            .then(data => { 								<!-- 데이터를 받아와서 처리 -->
                const labels = data.map(item => item.month); <!-- data 배열의 각 요소에 대해 month 속성을 추출하여 새로운 배열을 만들어냄 -->
                const sales = data.map(item => item.sales);  <!-- data 배열의 각 요소에 대해 sales 속성을 추출하여 새로운 배열을 만들어냄 -->

                const ctx = document.getElementById('myChart').getContext('2d'); 
                new Chart(ctx, { 												<!-- Chart.js를 사용하여 차트를 생성 -->
                    type: 'bar', 												
                    data: { 													<!-- 차트에 표시할 데이터를 설정 -->
                        labels: labels, 										<!-- 레이블 설정 -->
                        datasets: [{ 											<!-- 데이터셋 설정 -->
                            label: '월간 판매', 									<!-- 가로 축(X 축) -->
                            data: sales, 										<!-- 세로 축(Y 축) -->
                            backgroundColor: 'rgba(75, 192, 192, 0.2)', 		<!-- 바의 배경색 -->
                            borderColor: 'rgba(75, 192, 192, 1)', 				<!-- 바의 테두리 색 -->
                            borderWidth: 1 										<!-- 바의 테두리 두께 -->
                        }]
                    },
                    options: { 											<!-- 차트의 옵션을 설정 -->
                        scales: { 										<!-- 축 설정 -->
                            y: { 										<!-- y 축 설정 -->
                                beginAtZero: true 						<!-- 0부터 시작하도록 설정 -->
                            }
                        }
                    }
                });
            });
    </script>
</body>
</html>