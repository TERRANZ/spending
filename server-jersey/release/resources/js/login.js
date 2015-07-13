function userLogin() {
    if (!$("#loginbtn").hasClass('ui-disabled'))
        $("#loginbtn").addClass('ui-disabled');
	var user = $("#j_username").val();
	var pass = $("#j_password").val();
	if (user == null || user.length == 0) {
		alert('Не указан логин');
	} else if (pass == null || pass.length == 0) {
		alert('Не введен пароль');
	} else {

		$
				.ajax({
					url : '/spending/login/do.login.json',
					async : false,
					type : 'get',
					data : {
						user : user,
						pass : pass
					},
					success : function(data) {
				    	$("#loginbtn").removeClass('ui-disabled');
						if (data.logged == true) {
						    setCookie("JSESSIONID",data.session);
							window.location.assign("/spending/ui/main");
						} else {
							if (parseInt(data.errorCode) == 1000) {
								alert('Вы сделали слишком много попыток зайти на ресурс. Аккаунт временно заблокирован. Попробуйте еще раз через некоторое время.');
							} else if (parseInt(data.errorCode) == 1001) {
								alert('Ваш аккаунт заблокирован, обратитесь в поддержку для получения полной информации.');
							} else {
								alert('Неверный логин или пароль');
								$('#j_password').val('');
							}
						}
					}
				});
	}
}

function setCookie(key, value) {
                var expires = new Date();
                expires.setTime(expires.getTime() + (1 * 24 * 60 * 60 * 1000));
                document.cookie = key + '=' + value +';path=/'+ ';expires=' + expires.toUTCString();
            }

            function learnRegExp(s) {
                  var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
                  return regexp.test(s);
             }

             $("#user").ready(loadCaptcha());

             var captcha = "";
             function loadCaptcha(){
                 if (!$("#loadcapthca").hasClass('ui-disabled'))
                         $("#loadcapthca").addClass('ui-disabled');
                   $.ajax({
                   				url : '/spending/captcha/do.get.json',
                   				async : true,
                   				type : 'get',
                   				data : {
                   				},
                   				success : function(data) {
                   				   	$("#loadcapthca").removeClass('ui-disabled');
                   					if (data.errorCode == 0) {
                                            captcha = data.cid;
                                            $("#captcha_image").attr("src",data.image);
                   					} else {
                                            alert("Не получилось загрузить капчу: "+data.errorMessage);
                   					}
                   				}
                   			});
             }

             function userReg() {
             	var user = $("#user").val();
             	var pass = $("#pass").val();
             	var capval = $("#captcha_val").val();
             	if (user == null || user.length == 0) {
             		alert('Не указан логин');
             	} else if (pass == null || pass.length == 0) {
             		alert('Не введен пароль');
             	} else {
             		$.ajax({
             					url : '/spending/login/do.register.json',
             					async : true,
             					type : 'get',
             					data : {
             						user : user,
             						pass : pass,
             						captcha : captcha,
             						capval : capval
             					},
             					success : function(data) {
             						if (data.logged == true) {
             						    setCookie("JSESSIONID",data.session);
             							window.location.assign("/spending/ui/main");
             						} else {
             							if (parseInt(data.errorCode) == 1000) {
             								alert('Вы сделали слишком много попыток зайти на ресурс. Аккаунт временно заблокирован. Попробуйте еще раз через некоторое время.');
             							} else if (parseInt(data.errorCode) == 1001) {
             								alert('Ваш аккаунт заблокирован, обратитесь в поддержку для получения полной информации.');
             							} else {
             								alert(data.message);
             								$('#j_password').val('');
             							}
             						}
             					}
             				});
             	}
             }