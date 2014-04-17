$("#transactionstable").ready(load_transactions());

function load_transactions(){
    $.ajax({
    		url : '/spending/mobiletransaction/do.list.json',
    		async : true,
    		type : 'get',
    		data : {},
    		success : function(data) {
    			var htmlRet = "<table>";
    			if (data.errorCode == 0) {
    				$.each(data.data, function(i, transaction) {
    					htmlRet += processTransaction(transaction);
    				});
    			}
    			htmlRet += "</table>"
    			$("#transactionstable").html(htmlRet);
    		}
    	});
}


function processTransaction(transaction){
    var html="";
    html +="<tr>";
    html +="<td>"+transaction.id+"</td>";
    html +="<td>"+transaction.date+"</td>";
    html +="<td>"+transaction.type+"</td>";
    html +="<td>"+transaction.value+"</td>";
    html +="</tr>";
    return html;
}