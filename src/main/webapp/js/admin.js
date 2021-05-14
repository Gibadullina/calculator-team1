var adminUrl = "/WebCalc/EditController"
var admin_entries = []
var default_name = ""
var default_price = 0
var edit_type = ""

function adminEvents() {
	document.getElementById("actionToEditProductions").addEventListener("click", (event) => adminEdit(event, 0));
	document.getElementById("actionToEditRegions").addEventListener("click", (event) => adminEdit(event, 1));
	document.getElementById("adminAddNew").addEventListener("click", adminAddNewElement);
	document.getElementById("adminSave").addEventListener("click", adminSave);
}

function adminAddNewElement() {
	admin_entries.push({name: default_name, price: default_price});
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
	
	var isOk = true
	
	var root = {
		"entries": [
			
		]
	}
	admin_entries.forEach((entry) => {
		if (!entry.name || isNaN(parseFloat(entry.price))) {
			isOk = false;
		}
		root.entries.push({"name": entry.name, "price": entry.price});
	});
	
	if (isOk) {
	
		$.post(adminUrl+"?type="+edit_type+"&secret_key="+secret_key+"&login"+login, JSON.stringify(root), function(response) {
	        
	       showHelp(null, "Информация", "Запрос обработан!");
	    });

	} else {
		showHelp(null, "Ошибка", "Пожалуйста, заполните все поля корректными данными.");
	}
}

function adminEdit(event, type) {
	var get_params = type == 0 ? "?type=products" : "?type=regions";
	if (type == 0) {
		default_name = "Новый";
		default_price = 100;
		edit_type = "products";
	} else {
		default_name = "Республика X";
		default_price = 1;
		edit_type = "regions";
	}
	$.get(adminUrl+get_params, function(response) {
		admin_entries = []
        var root = JSON.parse(response);
		root.entries.forEach(entry => {
			admin_entries.push(entry);	
		});
		adminRender();
        $("#modalAdmin").modal("toggle");
    });

}

adminEvents();