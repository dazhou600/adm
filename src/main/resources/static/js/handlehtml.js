var sock = new SockJS("/mesgs");
var stomp = Stomp.over(sock);
var tongzhi ;//通知
stomp.connect('guest', 'guest', function(frame) {
	console.log('*****  Connected  *****');
	stomp.subscribe("/topic/msg", handlemessage);
	stomp.subscribe("/user/queue/notifications", handleNotification);
	requestMessage("def", "n");
});

function handlemessage(message) {
	// console.log('message:', message);
	console.log('*****  返回消息  *****');
	// $('#output').append("<b>Received message: " + JSON.parse(message.body)+
	// "</b><br/>");
	var mess = JSON.parse(message.body);
	tongzhi =mess ;
	var count = Object.keys(mess).length;
	// var source= $("#myList").html();
	var source = $("#msgList").html();
	var template = Handlebars.compile(source);
	// for(m in mess){
	var messagehtml = template(mess);
	$('li.dropdown.messages-menu').prepend(messagehtml);
	//$('#output').prepend(messagehtml);

}

function handleNotification(message) {
	console.log('Notification: ', message);
	var mess = JSON.parse(message.body);
	for (m in mess) {
		$('#output').append(
				"<b>返回: " + +mess[m].id + ":" + mess[m].message + "</b><br/>")
	}
	// $('#output').append("<b>返回: " +
	// JSON.parse(message.body).message + "</b><br/>")
}

function requestMessage(type, text) {
	console.log('发送消息 到服务器');
	stomp.send("/app/msg", {}, JSON.stringify({
		"type" : type,
		"text" : text
	}));
}
//消息提醒管理页面
function getMess(){
	console.log("msgggggg",tongzhi);
	var source = $("#msgList2").html();
	var template = Handlebars.compile(source);
	// for(m in mess){
	var messagehtml = template(tongzhi);
	$('#activity').prepend(messagehtml);
	
}

$("#stop").click(function() {
	sock.close()
});
Handlebars.registerHelper('list', function(items, options) {
	var out = "<ul>";

	for (var i = 0, l = items.length; i < l; i++) {
		out = out + "<li>" + options.fn(items[i]) + "</li>";
	}

	return out + "</ul>";
});
$('#messageForm').submit(function(e) {
	e.preventDefault();
	var text = $('#messageForm').find('textarea[name="text"]').val();
	requestMessage("messager", text);
});

$(function () {
var areaChartData = {
    labels  : ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
    datasets: [
      {
        label               : 'Electronics',
        fillColor           : 'rgba(210, 214, 222, 1)',
        strokeColor         : 'rgba(210, 214, 222, 1)',
        pointColor          : 'rgba(210, 214, 222, 1)',
        pointStrokeColor    : '#c1c7d1',
        pointHighlightFill  : '#fff',
        pointHighlightStroke: 'rgba(220,220,220,1)',
        data                : [65, 59, 80, 81, 56, 55, 40]
      },
      {
        label               : 'Digital Goods',
        fillColor           : 'rgba(60,141,188,0.9)',
        strokeColor         : 'rgba(60,141,188,0.8)',
        pointColor          : '#3b8bba',
        pointStrokeColor    : 'rgba(60,141,188,1)',
        pointHighlightFill  : '#fff',
        pointHighlightStroke: 'rgba(60,141,188,1)',
        data                : [28, 48, 40, 19, 86, 27, 90]
      }
    ]
  }
  
  var areaChartOptions = {
    //Boolean - If we should show the scale at all
    showScale               : true,
    //Boolean - Whether grid lines are shown across the chart
    scaleShowGridLines      : false,
    //String - Colour of the grid lines
    scaleGridLineColor      : 'rgba(0,0,0,.05)',
    //Number - Width of the grid lines
    scaleGridLineWidth      : 1,
    //Boolean - Whether to show horizontal lines (except X axis)
    scaleShowHorizontalLines: true,
    //Boolean - Whether to show vertical lines (except Y axis)
    scaleShowVerticalLines  : true,
    //Boolean - Whether the line is curved between points
    bezierCurve             : true,
    //Number - Tension of the bezier curve between points
    bezierCurveTension      : 0.3,
    //Boolean - Whether to show a dot for each point
    pointDot                : false,
    //Number - Radius of each point dot in pixels
    pointDotRadius          : 4,
    //Number - Pixel width of point dot stroke
    pointDotStrokeWidth     : 1,
    //Number - amount extra to add to the radius to cater for hit detection outside the drawn point
    pointHitDetectionRadius : 20,
    //Boolean - Whether to show a stroke for datasets
    datasetStroke           : true,
    //Number - Pixel width of dataset stroke
    datasetStrokeWidth      : 2,
    //Boolean - Whether to fill the dataset with a color
    datasetFill             : true,
    //String - A legend template
    legendTemplate          : '<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].lineColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
    //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
    maintainAspectRatio     : true,
    //Boolean - whether to make the chart responsive to window resizing
    responsive              : true
  }
  
   //-------------
  //- LINE CHART -
  //--------------
  var lineChartCanvas          = $('#lineChart').get(0).getContext('2d')
  var lineChart                = new Chart(lineChartCanvas)
  var lineChartOptions         = areaChartOptions
  lineChartOptions.datasetFill = false
  lineChart.Line(areaChartData, lineChartOptions)
  
 //-------------
  //- BAR CHART -
  //-------------
  var barChartCanvas                   = $('#barChart').get(0).getContext('2d')
  var barChart                         = new Chart(barChartCanvas)
  var barChartData                     = areaChartData
  barChartData.datasets[1].fillColor   = '#00a65a'
  barChartData.datasets[1].strokeColor = '#00a65a'
  barChartData.datasets[1].pointColor  = '#00a65a'
  var barChartOptions                  = {
    //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
    scaleBeginAtZero        : true,
    //Boolean - Whether grid lines are shown across the chart
    scaleShowGridLines      : true,
    //String - Colour of the grid lines
    scaleGridLineColor      : 'rgba(0,0,0,.05)',
    //Number - Width of the grid lines
    scaleGridLineWidth      : 1,
    //Boolean - Whether to show horizontal lines (except X axis)
    scaleShowHorizontalLines: true,
    //Boolean - Whether to show vertical lines (except Y axis)
    scaleShowVerticalLines  : true,
    //Boolean - If there is a stroke on each bar
    barShowStroke           : true,
    //Number - Pixel width of the bar stroke
    barStrokeWidth          : 2,
    //Number - Spacing between each of the X value sets
    barValueSpacing         : 5,
    //Number - Spacing between data sets within X values
    barDatasetSpacing       : 1,
    //String - A legend template
    legendTemplate          : '<ul class="<%=name.toLowerCase()%>-legend"><% for (var i=0; i<datasets.length; i++){%><li><span style="background-color:<%=datasets[i].fillColor%>"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>',
    //Boolean - whether to make the chart responsive
    responsive              : true,
    maintainAspectRatio     : true
  }

  barChartOptions.datasetFill = false
  barChart.Bar(barChartData, barChartOptions)
})