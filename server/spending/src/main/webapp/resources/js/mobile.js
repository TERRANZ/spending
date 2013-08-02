$("#feeds").ready(load_feeds());
function load_feeds() {
	$.ajax({
		url : '/jbrss/rss/do.list.json',
		async : false,
		type : 'post',
		dataType : 'jsonp',
		data : {},
		success : function(data) {
			var htmlRet = "";
			if (data.errorCode == 0) {
				$.each(data.data, function(i, feed) {
					htmlRet += '<li data-theme="c"><a href="#" onClick=load_feed(' + feed.id + '); data-transition="slide">' + feed.feedname
							+ '</a></li>';
				});
				$("#feeds").html(htmlRet);
			}
		}
	});
}

function load_feed(feedId) {
	$("#feeds_collapsable").trigger('collapse');
	$("#messages_collapsable").trigger('expand');
	$.ajax({
		url : '/jbrss/rss/do.get.feed.json',
		async : false,
		type : 'post',
		dataType : 'jsonp',
		data : {
			id : feedId,
			count : 10
		},
		success : function(data) {
			var htmlRet = "";
			if (data.errorCode == 0) {
				$.each(data.data, function(i, post) {
					htmlRet += create_main_post(post.posttitle, post.posttext, post.postdate, post.postlink);
				});
				$("#messages").html(htmlRet);
			}
		}
	});
}

function create_main_post(title, text, date, link) {
	var ts = new Date(date);
	// hours part from the timestamp
	var year = ts.getFullYear();
	var month = ts.getMonth();
	var day = ts.getDay();
	var hours = ts.getHours();
	// minutes part from the timestamp
	var minutes = ts.getMinutes();
	// seconds part from the timestamp
	var seconds = ts.getSeconds();

	// will display time in 10:30:23 format
	var formattedTime = year + '.' + month + '.' + day + ' ' + hours + ':' + minutes + ':' + seconds;
	var ret = "";
	ret += '<div class="column1-unit"> <h1>' + title + '</h1>';
	ret += '<h3>' + formattedTime + '</h3>';
	ret += '<p>' + text + '</p>';
	ret += '<a href=' + link + '>Ссылка</a>';
	ret += '</div> <hr class="clear-contentunit" />';
	return ret;
}

function userLogin() {
	var user = $("#j_username").val();
	var pass = $("#j_password").val();
	if (user == null || user.length == 0) {
		alert('Не указан логин');
	} else if (pass == null || pass.length == 0) {
		alert('Не введен пароль');
	} else {

		$
				.ajax({
					url : '/jbrss/login/do.login.json',
					async : false,
					type : 'post',
					dataType : 'jsonp',
					data : {
						user : user,
						pass : pass
					},
					success : function(data) {
						if (data.logged == true) {
							window.location.assign("/jbrss/mobile");
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