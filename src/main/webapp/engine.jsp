<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Калькулятор</title>
   	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<c:if test="${login}">
	<div id="root">
    <header>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <a class="navbar-brand" href="#">Калькулятор</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
            
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Файл
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#" id="actionNewFile">Новый</a>
                            <a class="dropdown-item" href="#" id="actionSaveFile">Сохранить</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" id="actionExit">Выйти</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Экспорт
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#" id="actionToExcel">В Excel</a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            О приложении
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="#" id="actionHelp">Справка</a>
                            <!-- <a class="dropdown-item" href="#" id="actionAbout">О калькуляторе</a> -->
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#" id="actionDevelopers">Разработчики</a>
                        </div>
                    </li>
                    <c:if test="${admin}">
                    	<li class="nav-item dropdown">
	                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                            Редактировать
	                        </a>
	                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
	                            <a class="dropdown-item" href="#" id="actionToEdit">Показатели</a>
	                        </div>
                    	</li>
                    </c:if>
                </ul>
                <form class="form-inline my-2 my-lg-0">
                	<c:if test="${admin}">
                    	<button class="btn btn-outline-success my-2 my-sm-0 btn-admin" type="submit" disabled>Администратор</button>
                    </c:if>
                    <c:if test="${!admin}">
                    	<button class="btn btn-outline-success my-2 my-sm-0 btn-user" type="submit" disabled>Пользователь</button>
                    </c:if>
                </form>
                </div>
            </div>
        </nav>   
    </header>
    <div class="container root">
        <form>
            <div class="row">
                <div class="col">
                    <div class="form-group">
                        <label for="fio">ФИО работника:</label>
                        <input required class="form-control" type="text" id="fio" placeholder="Иванов Петр Князевич">
                    </div>
                    <div class="form-group">
                        <label for="state">Должность работника:</label>
                        <input required class="form-control" type="text" id="state" placeholder="Слесарь">
                    </div>
                    <div class="form-group">
                        <label for="val">Расценка за единицу продукции:</label>
                        <select id="val" class="custom-select custom-select-sm">
                            <c:forEach var="entry" items="${entries}" >
						        <option>${entry}</option>
						    </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="production">Изготовленная продукция (ед.):</label>
                        <input required class="form-control" type="text" id="production" placeholder="0">
                    </div>
                    <div class="form-group">
                        <label for="prize">Премия (руб.):</label>
                        <input required class="form-control" type="text" id="prize" placeholder="1000">
                    </div>                   
                </div>
                <div class="col">
                    <div class="form-group">
                        <label for="count">Единица товара для премии (ед.):</label>
                        <input required class="form-control" type="text" id="count" placeholder="5">
                    </div>
                    <div class="form-group">
                        <label for="ndfl">НФДЛ (%):</label>
                        <input required class="form-control" type="text" id="ndfl" placeholder="13">
                    </div>
                    <div class="form-group">
                        <label for="normal">Норма (ед.):</label>
                        <input required class="form-control" type="text" id="normal" placeholder="1">
                    </div>
                    <div class="form-group">
                        <label for="location">Районный коэффициент:</label>
                        <select id="location" class="custom-select custom-select-sm">
                            <option selected>Республика Башкортостан</option>
                            <option selected>Ростовская область</option>
                            <option selected>Республика Дагестан</option>
                            <option selected>Республика Калмыкия</option>
                            <option selected>Ставропольский край</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="mrot">Учет МРОТа</label>
                        <select id="mrot" class="custom-select custom-select-sm">
                            <option selected>Не учитывать</option>
                            <option selected>Учитывать</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-lg btn-primary" style="float:right; margin-top: 10px;" id="submitButton">Рассчитать</button>   
                </div>
            </div>
        </form>
    </div>

    <div class="container">
        <footer>
            УГАТУ 2021 - Курсовая работа - Вариант 1
        </footer>
    </div>

    <!-- Modals -->
    <div class="modal fade" id="modalShow" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="modalShowTitle">Справка</h5> 
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            </div>
            <div class="modal-body">
                <p id="modalShowText">
                   Lorem ipsum dolor sit amet consectetur, adipisicing elit. Aut quod quo obcaecati perferendis soluta amet, deleniti earum velit culpa numquam laudantium ullam mollitia in, iusto repellendus commodi ipsum pariatur quaerat?
                </p>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
            <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
        </div>
    </div>
    </c:if>
    <c:if test="${!isLogin}">
    	<h2>Доступ запрещён!</h2>
    </c:if>
    <c:if test="${admin}">
    	<div class="modal fade" id="modalAdmin" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title" id="modalShowTitle">Редактирование</h5> 
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            </div>
            <div class="modal-body" id="adminContent">
                <div class="adminEntry form-group">
                	<input required class="form-control" type="text" value="Компрессор" id="val_name_0">
                	<input required class="form-control input-price" type="text" value="100" id="val_price_0">
                	<button type="button" class="btn btn-danger">Удалить</button>
                </div>
            </div>
            <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="adminAddNew">Добавить новый</button>
            <button type="button" class="btn btn-secondary" id="adminSave" data-dismiss="modal">Закрыть и сохранить</button>
            <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
            </div>
        </div>
        </div>
    	</div>
    </c:if>

	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="js/main.js"></script>
</div>
</body>
</html>