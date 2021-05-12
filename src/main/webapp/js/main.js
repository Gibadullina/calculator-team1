var secret_key = "";

var manual = "Данный калькулятор предназначен для расчёта сдельно-премиальной зарплаты.<br/>"
+ "Сдельно-премиальная оплата труда – это форма, которая предполагает не только исчисление прямого заработка с учетом фактических результатов труда, но и установление надбавок (премий) за выполнение и перевыполнение плановых показателей.</br>"
+ "Для расчёта необходимо ввести все значения.<br/>"
+ "Расчёт осуществляется с учётом <strong>МРОТ, НДФЛ.</strong>";
var developers = "Калькулятор был разработан студентами группы <strong>ПИ-221</strong>: " + "<br/>Рафиков Данил" +"<br/>Катасонов Серафим" +"<br/>Гибадуллина Элина" +"<br/>Газин Даниэль";

var serverUrl = "/webcalculator1/CalculatorController";
var exportUrl = "/webcalculator1/ExportController";

function linkEvents() {
    document.getElementById("submitButton").addEventListener("click", calculate);
    document.getElementById("actionNewFile").addEventListener("click", newFile);
    document.getElementById("actionSaveFile").addEventListener("click", calculate);
    document.getElementById("actionExit").addEventListener("click", calculate);
    document.getElementById("actionToExcel").addEventListener("click", toExcel);
    document.getElementById("actionHelp").addEventListener("click", (event) => showHelp(event, "Справка", manual));
    document.getElementById("actionDevelopers").addEventListener("click", (event) => showHelp(event, "Разработчики", developers));
}


function getData() {
    var data = {
        fio: document.getElementById("fio").value,
        state: document.getElementById("state").value,
        val: document.getElementById("val").selectedIndex,
        production: parseInt(document.getElementById("production").value),
        prize: parseFloat(document.getElementById("prize").value),
        count: parseInt(document.getElementById("count").value),
        ndfl: parseFloat(document.getElementById("ndfl").value),
        normal: parseInt(document.getElementById("normal").value),
        location: document.getElementById("location").selectedIndex,
        mrot: document.getElementById("mrot").innerText == "Учитывать" ? true : false
    }

    return data;
}

function validateData(data) {
	if (!data.fio || !data.state)
		return false;
	if (isNaN(data.val) || isNaN(data.production)|| isNaN(data.normal) || isNaN(data.count))
		return false;
	if (isNaN(data.ndfl) || isNaN(data.prize))
		return false;
	return true;
}

function calculate(event) {
    event.preventDefault();

	var data = getData();
	console.log(JSON.stringify(data));	

	if (validateData(data)) {
	    $.post(serverUrl, JSON.stringify(data), function(response) {
	        showHelp(null, "Результат вычисления", response);
	    });
	} else {
		showHelp(null, "Ошибка", "Пожалуйста, заполните все поля корректными данными.")
	}
}

function newFile(event) {
    document.getElementById("fio").value = "";
    document.getElementById("state").value = "";
    document.getElementById("production").value = "";
    document.getElementById("prize").value = "";
    document.getElementById("count").value = "";
    document.getElementById("ndfl").value = "";
    document.getElementById("normal").value = "";
}

function toExcel(event) {
	var data = getData();
	if (validateData(data)){
    	str = "?fio="+data.fio+"&state="+data.state+"&val="+data.val+"&production="+data.production+"&prize="+data.prize+"&count="+data.count+"&ndfl="+data.ndfl+"&normal="+data.normal+"&location="+data.location+"&mrot="+data.mrot;
    	console.log(str)
		downloadFile(exportUrl+str);
	} else {
		showHelp(null, "Ошибка", "Пожалуйста, заполните все поля корректными данными.")
	}
}

function downloadFile(urlToSend) {
    var req = new XMLHttpRequest();
    req.open("GET", urlToSend, true);
    req.responseType = "blob";
    req.onload = function (event) {
        var blob = req.response;
        var link=document.createElement('a');
        link.href=window.URL.createObjectURL(blob);
        link.download="result.xls";
        link.click();
    };

    req.send();
}

function showHelp(event, arg0, arg1 = "") {
    document.getElementById("modalShowText").innerHTML = arg1
    document.getElementById("modalShowTitle").innerHTML = arg0;
    $("#modalShow").modal("toggle");
}

linkEvents()