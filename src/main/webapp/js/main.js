var state = {
    "login": "",
    "auth": false,
    "key": ""
}

var admin_entries = [
]

var manual = "Данный калькулятор предназначен для расчёта сдельно-премиальной зарплаты.<br/>"
+ "Сдельно-премиальная оплата труда – это форма, которая предполагает не только исчисление прямого заработка с учетом фактических результатов труда, но и установление надбавок (премий) за выполнение и перевыполнение плановых показателей.</br>"
+ "Для расчёта необходимо ввести все значения.<br/>"
+ "Расчёт осуществляется с учётом <strong>МРОТ, НДФЛ.</strong>";
var developers = "Калькулятор был разработан студентами группы <strong>ПИ-221</strong>: " + "<br/>Рафиков Данил" +"<br/>Катасонов Серафим" +"<br/>Гибадуллина Элина" +"<br/>Газин Даниэль";

var serverUrl = "/webcalculator1/CalculatorController";
var exportUrl = "/webcalculator1/ExportController";
var adminUrl = "/webcalculator1/EditController"

function linkEvents() {
    document.getElementById("submitButton").addEventListener("click", calculate);
    document.getElementById("actionNewFile").addEventListener("click", newFile);
    document.getElementById("actionSaveFile").addEventListener("click", calculate);
    document.getElementById("actionExit").addEventListener("click", calculate);
    document.getElementById("actionToExcel").addEventListener("click", toExcel);
    document.getElementById("actionHelp").addEventListener("click", (event) => showHelp(event, "Справка", manual));
    document.getElementById("actionDevelopers").addEventListener("click", (event) => showHelp(event, "Разработчики", developers));
    document.getElementById("actionToEdit").addEventListener("click", toEdit);
	document.getElementById("adminAddNew").addEventListener("click", addNewElement);
	document.getElementById("adminSave").addEventListener("click", adminSave);
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

function addNewElement() {
	admin_entries.push({name: "Новый", price: "100"});
	adminRender();
}

function adminRender() {
	$("#adminContent").empty();
	var i = 0;
	admin_entries.forEach((entry) => {
		var new_entry = document.createElement("div");
		var input_name = document.createElement("input");
		var input_price = document.createElement("input");
		var remove_button = document.createElement("button");
		new_entry.className = "adminEntry form-group";
		input_name.className = "form-control";
		input_name.value = entry.name;
		input_name.type = "text";
		input_name.onchange = adminNameChange
		input_name.id = "iname"+i
		input_price.className = "form-control input-price";
		input_price.value = entry.price;
		input_price.type = "text";
		input_price.onchange = adminPriceChange;
		input_price.id = "iprice"+i;
		remove_button.className = "btn btn-danger";
		remove_button.innerText = "Удалить";
		remove_button.type = "button";
		remove_button.id = "rbutton"+i;
		remove_button.addEventListener("click", (event) => adminRemove(event));
		new_entry.appendChild(input_name);
		new_entry.appendChild(input_price);
		new_entry.appendChild(remove_button);
		$("#adminContent").append(new_entry);
		i++;
	});
}

function adminNameChange(event) {
	var str = String(event.srcElement.id);
	var id = parseInt(str.substr(5,str.length - 5));
	admin_entries[id].name = event.srcElement.value;
}

function adminPriceChange(event) {
	var str = String(event.srcElement.id);
	var id = parseInt(str.substr(6,str.length - 6));
	admin_entries[id].price = event.srcElement.value;
}

function adminRemove(event) {
	var str = String(event.srcElement.id);
	var id = parseInt(str.substr(7,str.length - 7));
	admin_entries.splice(id, 1);
	adminRender();
}

function adminSave() {
	
	var root = {
		"entries": [
			
		]
	}
	admin_entries.forEach((entry) => {
		root.entries.push({"name": entry.name, "price": entry.price});
	});
	
	$.post(adminUrl, JSON.stringify(root), function(response) {
        
       showHelp(null, "Информация", "Запрос обработан!");
    });

	admin_entries.empty();
}

function toEdit(event) {
	$.get(adminUrl, function(response) {
		admin_entries = []
        var root = JSON.parse(response);
		root.entries.forEach(entry => {
			admin_entries.push(entry);	
		});
		adminRender();
        $("#modalAdmin").modal("toggle");
    });

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