$("#settings").ready(load());
function load() {
	$.ajax({
		url : '/tawebgui/settings/do.list.json',
		async : false,
		type : 'post',
		dataType : 'jsonp',
		data : {},
		success : function(data) {
			var html = "";
			$("#count").val(data.size);
			$.each(data.data, function(i, setting) {
				html += createSettingRow(setting.key, setting.value, i);
			});
			$("#settings").html(html);
		}
	});
}

function save() {
	$(".settingDiv").each(function(index) {
		var key = "";
		var val = "";
		key = $(this).children("#key").val();
		val = $(this).children("#value").val();
		$.ajax({
			url : '/tawebgui/settings/do.set.json',
			async : false,
			type : 'post',
			dataType : 'jsonp',
			data : {
				key : key,
				value : val
			},
			success : function(data) {
				if (data.data)
					alert("Всё сохранено");
			}
		});
	});
}

function addNew() {
	var html = $("#settings").html();
	var count = $("#count").val() + 1;
	$("#count").val(count);
	html += createSettingRow("", "", count);
	$("#settings").html(html);
}

function createSettingRow(key, value, id) {
	var ret = "";
	ret += '<div id="setting' + id + '" class="settingDiv">';
	ret += '<div class="column1-unit">';
	ret += '<input type="text" id="key"' + id + ' value="' + key + '"/>';
	ret += '<input type="text" id="value"' + id + ' value="' + value + '"/>';
	ret += '</div> <hr class="clear-contentunit" />';
	ret += '<a href="#" onClick="removeSetting(' + id + ')">Удалить</a>';
	ret += '</div>';
	return ret;
}

function removeSetting(id) {
	$("#setting" + id).remove();
}