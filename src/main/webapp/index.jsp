<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Авторизация</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="css/auth.css">
	<link rel="icon" type="image/ico" href="picture/icon.ico">
</head>
<body>
	<div class="vanta-background">
	    
	 </div>
	 
	<div class="container content" id="authRoot">
	        <h2>Калькулятор</h2>
	        <div class="buttons">
	        	<button class="btn btn-primary btn-present" id="btnRegister">Регистрация</button>
	        	<button class="btn btn-primary btn-present" id="btnAuth">Авторизация</button>
	        </div>
	</div>
	
    <!-- Modals -->
    <div class="modal fade" id="modalAuth" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="modalShowTitle">Авторизация</h5> 
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            </div>
            <div class="modal-body">
                <form action="#" method="POST">
                    <div class="mb-3">
                      <label for="emailInput" class="form-label">Ваш Логин:</label>
                      <input required name="username" type="text" class="form-control" id="loginInput" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                      <label for="PasswordLoginInput" class="form-label">Пароль:</label>
                      <input required name="password" type="password" class="form-control" id="PasswordLoginInput">
                    </div>
                    <div class="mb-3 form-check">
                      <input name="password" type="checkbox" class="form-check-input" id="exampleCheck1">
                      <label class="form-check-label" for="exampleCheck1">Запомнить</label>
                    </div>
                    <button type="submit" class="btn btn-primary" id="btnAuthAction">Авторизоваться</button>
                  </form>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
            <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
        </div>
    </div>

    <div class="modal fade" id="modalRegister" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="modalRegister">Регистрация</h5> 
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            </div>
            <div class="modal-body">
                <form action="#" method="POST">
                    <div class="mb-3">
                      <label for="emailInput" class="form-label">Ваш Логин:</label>
                      <input required name="username" type="text" class="form-control" id="registerInput" aria-describedby="emailHelp">
                    </div>
                    <div class="mb-3">
                      <label for="passwordInput" class="form-label">Пароль:</label>
                      <input required name="password" type="password" class="form-control" id="passwordInput">
                    </div>
                    <div class="mb-3">
                      <label for="passwordInputRepeat" class="form-label">Повторить пароль:</label>
                      <input required name="password1" type="password" class="form-control" id="passwordInputRepeat">
                    </div>
                    <button type="submit" class="btn btn-primary" id="btnRegisterAction">Создать аккаунт</button>
                  </form>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
            <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
        </div>
    </div>
    
    <!-- Modals -->
    <div class="modal fade" id="modalShow" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="modalShowTitle">Информация</h5> 
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            </div>
            <div class="modal-body">
                <p id="modalShowText"></p>
            </div>
            <div class="modal-footer">
            	<button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="js/auth.js"></script>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r121/three.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vanta@latest/dist/vanta.globe.min.js"></script>
	<script>
	VANTA.GLOBE({
	  el: ".vanta-background",
	  mouseControls: true,
	  touchControls: true,
	  gyroControls: false,
	  minHeight: 200.00,
	  minWidth: 200.00,
	  scale: 1.00,
	  scaleMobile: 1.00
	})
	</script>
</body>
</html>