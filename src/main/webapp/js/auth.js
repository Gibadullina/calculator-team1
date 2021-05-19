/*
Данный JS файл необходим для обеспечения возможности авторизации и регистрации пользователя.
Здесь имеется 2 функция-обработчика событий - для регистрации и авторизации. В обоих используется
AJAX для обеспечения лучшей интерактивности с пользователем.
*/


//URL адреса сервлетов сервера
var login_url = "/WebCalc/AuthController";
var register_url = "/WebCalc/RegisterController";

//Линковка кнопки регистрации для вызова модального окна регистрации
$("#btnRegister").click(function(event) {
    $("#modalRegister").modal("toggle");
});
//Линковка кнопки авторизации для вызова модального окна авторизации
$("#btnAuth").click(function(event) {
    $("#modalAuth").modal("toggle");
});

//При нажатии на кнопку регистрации
$("#btnRegisterAction").click(function(event) {
	event.preventDefault(); //Отменяем дефолтное событие формы
	if ($("#passwordInput").val() == $("#passwordInputRepeat").val()) //Проверяем, чтобы пароли совпадали (пароль = повторить пароль)
	{
		var get_parameters = "?username="+$("#registerInput").val()+"&password="+$("#passwordInput").val();
	    $.get(register_url+get_parameters, function(response) { //Делаем GET AJAX запрос на сервер
			showInfo(response);
			if (response.substring(0,7) == "Аккаунт") 
				$("#modalRegister").modal("toggle");
	    });
	}
	else {
		showInfo("Пароли не совпадают!");
	}
});

//Обработка авторизации
$("#btnAuthAction").click(function(event) {
	event.preventDefault();
	var get_parameters = "?username="+$("#loginInput").val()+"&password="+$("#PasswordLoginInput").val();
    $.get(login_url+get_parameters, function(response) {
		if (response == 1) { //Если авторизовались
			window.location = "engine.jsp"; //Переходим на страницу работы с калькулятором
		} else showInfo(response); //Иначе показываем модальное окно с информацией об ошибке
    });
});

//Функция для работы с модальным окном для вывода определенной служебной информации пользователю
function showInfo(info) {
	$("#modalShowText").text(info);
	$("#modalShow").modal("toggle");
}

//Добавляем анимации
$( document ).ready(function() {
    $("#authRoot").addClass("animation");
});