var login_url = "/AuthController";
var register_url = "/RegisterController";


$("#btnRegister").click(function(event) {
    $("#modalRegister").modal("toggle");
});
$("#btnAuth").click(function(event) {
    $("#modalAuth").modal("toggle");
});

$("#btnRegisterAction").click(function(event) {
	event.preventDefault();
	if ($("#passwordInput").val() == $("#passwordInputRepeat").val())
	{
		var get_parameters = "?username="+$("#registerInput").val()+"&password="+$("#passwordInput").val();
	    $.get(register_url+get_parameters, function(response) {
			showInfo(response);
			if (response.substring(0,7) == "Аккаунт") 
				$("#modalRegister").modal("toggle");
	    });
	}
	else {
		showInfo("Пароли не совпадают!");
	}
});

$("#btnAuthAction").click(function(event) {
	event.preventDefault();
	var get_parameters = "?username="+$("#loginInput").val()+"&password="+$("#PasswordLoginInput").val();
    $.get(login_url+get_parameters, function(response) {
		if (response == 1) {
			window.location = "engine.jsp";
		} else showInfo(response);
    });
});

function showInfo(info) {
	$("#modalShowText").text(info);
	$("#modalShow").modal("toggle");
}

$( document ).ready(function() {
    $("#authRoot").addClass("animation");
});