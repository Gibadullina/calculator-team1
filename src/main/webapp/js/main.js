/*
Данный JS файл необходим для работы основного приложения. Здесь происходит
основная работа по обеспечению интерактивности и получению необходимых
результатов для пользователя.
*/

//Переменные для работы
var secret_key = "";
var login = "";

var manual = "Данный калькулятор предназначен для расчёта сдельно-премиальной зарплаты.<br/>"
+ "Сдельно-премиальная оплата труда – это форма, которая предполагает не только исчисление прямого заработка с учетом фактических результатов труда, но и установление надбавок (премий) за выполнение и перевыполнение плановых показателей.</br>"
+ "Для расчёта необходимо ввести все значения.<br/>"
+ "Расчёт осуществляется с учётом <strong>МРОТ, НДФЛ.</strong>";
var developers = "Калькулятор был разработан студентами группы <strong>ПИ-221</strong>: " + "<br/>Рафиков Данил" +"<br/>Катасонов Серафим" +"<br/>Гибадуллина Элина" +"<br/>Газин Даниэль";

//URL адреса сервлетов сервера
var serverUrl = "/CalculatorController";
var exportUrl = "/ExportController";
var exitUrl = "/ExitController";

//Линковка событий с обработчиками
function linkEvents() {
    document.getElementById("submitButton").addEventListener("click", calculate);
    document.getElementById("actionNewFile").addEventListener("click", newFile);
    document.getElementById("actionExit").addEventListener("click", exit);
    document.getElementById("actionToExcel").addEventListener("click", toExcel);
    document.getElementById("actionHelp").addEventListener("click", (event) => showHelp(event, "Справка", manual));
    document.getElementById("actionDevelopers").addEventListener("click", (event) => showHelp(event, "Разработчики", developers));
}

//Получаем данные из форм
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

//Валидация данных из форм. Чтобы не было пустых строк и неправильных данных для будущих вычислений на стороне сервера
function validateData(data) {
	if (!data.fio || !data.state)
		return false;
	if (isNaN(data.val) || isNaN(data.production)|| isNaN(data.normal) || isNaN(data.count))
		return false;
	if (isNaN(data.ndfl) || isNaN(data.prize))
		return false;
	return true;
}

//Запрос на вычисление заработной платы на основе введенных данных
function calculate(event) {
    event.preventDefault();

	var data = getData();

	if (validateData(data)) {
	    $.post(serverUrl+"?secret_key="+secret_key+"&login"+login, JSON.stringify(data), function(response) {
	        showHelp(null, "Результат вычисления", response);
	    });
	} else {
		showHelp(null, "Ошибка", "Пожалуйста, заполните все поля корректными данными.")
	}
}

//Очистка формы
function newFile(event) {
    document.getElementById("fio").value = "";
    document.getElementById("state").value = "";
    document.getElementById("production").value = "";
    document.getElementById("prize").value = "";
    document.getElementById("count").value = "";
    document.getElementById("ndfl").value = "";
    document.getElementById("normal").value = "";
}

//Запрос на преобразование данных формы в Excel файл
function toExcel(event) {
	var data = getData();
	if (validateData(data)){
    	str = "?fio="+data.fio+"&state="+data.state+"&val="+data.val+"&production="+data.production+"&prize="+data.prize+"&count="+data.count+"&ndfl="+data.ndfl+"&normal="+data.normal+"&location="+data.location+"&mrot="+data.mrot;
		downloadFile(exportUrl+str+"&secret_key="+secret_key+"&login"+login);
	} else {
		showHelp(null, "Ошибка", "Пожалуйста, заполните все поля корректными данными.")
	}
}

//Дополнительная функция для скачивания файлов с сервера
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

//Вызов служебного модального окна с информацией
function showHelp(event, arg0, arg1 = "") {
    document.getElementById("modalShowText").innerHTML = arg1
    document.getElementById("modalShowTitle").innerHTML = arg0;
    $("#modalShow").modal("toggle");
}

//Запрос на выход из сессии
function exit() {
	$.get(exitUrl, function(response) {
		if (response == 1) {
			window.location = "index.jsp";
		} else showInfo("Что-то пошло не так...");
    });
}

//Линковка событий с обработчиками
linkEvents()