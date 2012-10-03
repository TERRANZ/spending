function userLogin(user, pass) {
	if (user == null || user.length == 0) {
		alert('Не указан логин');
	} else if (pass == null || pass.length == 0) {
		alert('Не введен пароль');
	} else {

		$
				.ajax({
					url : '/spending/login/do.login.json',
					async : false,
					type : 'post',
					dataType : 'jsonp',
					data : {
						user : user,
						pass : pass
					},
					success : function(data) {
						if (data.logged == 'true') {
							window.location.reload();
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