var state = {
    "login": "",
    "auth": false,
    "key": ""
}

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


function dataToJson() {
    var data = {
        fio: document.getElementById("fio").value,
        state: document.getElementById("state").value,
        val: document.getElementById("val").selectedIndex,
        production: document.getElementById("production").value,
        prize: document.getElementById("prize").value,
        count: document.getElementById("count").value,
        ndfl: document.getElementById("ndfl").value,
        normal: document.getElementById("normal").value,
        location: document.getElementById("location").selectedIndex,
        mrot: document.getElementById("mrot").innerText == "Учитывать" ? true : false
    }

    return JSON.stringify(data);
}

function calculate(event) {
    event.preventDefault();

    var data = {
        fio: document.getElementById("fio").value,
        state: document.getElementById("state").value,
        val: document.getElementById("val").selectedIndex,
        production: document.getElementById("production").value,
        prize: document.getElementById("prize").value,
        count: document.getElementById("count").value,
        ndfl: document.getElementById("ndfl").value,
        normal: document.getElementById("normal").value,
        location: document.getElementById("location").selectedIndex,
        mrot: document.getElementById("mrot").innerText == "Учитывать" ? true : false
    }

    //Validate
    // for(val in data) {
    //     if (data[val] == undefined)
    //         console.log("1");
    //}
    
    console.log(dataToJson());

    $.post(serverUrl, dataToJson(), function(response) {
        showHelp(null, "Результат вычисления", response);
        // document.getElementById("modalShowText").innerHTML = response;
        // showHelp(null, "Результат вычисления");
    });
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
    var data = {
        fio: document.getElementById("fio").value,
        state: document.getElementById("state").value,
        val: document.getElementById("val").selectedIndex,
        production: document.getElementById("production").value,
        prize: document.getElementById("prize").value,
        count: document.getElementById("count").value,
        ndfl: document.getElementById("ndfl").value,
        normal: document.getElementById("normal").value,
        location: document.getElementById("location").selectedIndex,
        mrot: document.getElementById("mrot").innerText == "Учитывать" ? true : false
    }
    str = "?fio="+data.fio+"&state="+data.state+"&val="+data.val+"&production="+data.production+"&prize="+data.prize+"&count="+data.count+"&ndfl="+data.ndfl+"&normal="+data.normal+"&location="+data.location+"&mrot="+data.mrot;
    console.log(str);
    downloadFile(exportUrl+str);
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