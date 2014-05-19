$("#transactionstable").ready(load_transactions());
var spTypes = {};

function load_transactions(){
    $.ajax({
    		url : '/spending/mobiletransaction/do.list.json',
    		async : true,
    		type : 'get',
    		data : {},
    		success : function(transactions) {
    			var htmlRet = "<table>";
    			if (transactions.errorCode == 0) {
                        $.ajax({
                        		url : '/spending/types/do.list.json',
                        		async : false,
                        		type : 'get',
                        		data : {},
                        		success : function(types) {
                        		    if (types.errorCode == 0) {
                                    	$.each(types.data, function(i, type) {
                                            spTypes[type.id] = type.name;
                        				});
                        		    }
                        		    $.each(transactions.data, function(i, transaction) {
                        				htmlRet += processTransaction(transaction);
                        			});
                                }
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
    html +="<td>"+new Date(transaction.date)+"</td>";
    html +="<td>"+spTypes[transaction.type]+"</td>";
    html +="<td>"+transaction.value+"</td>";
    html +="</tr>";
    return html;
}