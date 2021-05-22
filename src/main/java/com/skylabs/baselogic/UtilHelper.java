package com.skylabs.baselogic;

public interface UtilHelper {
	//Готовые строки для файлов JSON, если их нет в ФС
		static final String DEFAULT_REGIONS = "{\"entries\":[{\"name\":\"Республика Башкортостан\",\"price\":1.15},{\"name\":\"Ростовская область\",\"price\":1.1},{\"name\":\"Республика Дагестан\",\"price\":1.12},{\"name\":\"Республика Калмыкия\",\"price\":1.3},{\"name\":\"Ставропольский край\",\"price\":1.15}]}";
		static final String DEFAULT_PRODUCTS = "{\"entries\":[{\"name\":\"Компрессор\",\"price\":1000},{\"name\":\"Вентилятор\",\"price\":300},{\"name\":\"Турбина\",\"price\":\"400\"},{\"name\":\"Сопло\",\"price\":100},{\"name\":\"Смеситель\",\"price\":200}]}";
		static final String DEFAULT_USERS = "{\"users\":[{\"password\":\"admin\",\"admin\":\"true\",\"username\":\"admin\"}]}";

}
