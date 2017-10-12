</script>
 	
 	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>

 		$( document ).ready(function() {
 			$.getJSON('/getcuentas',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".tablaCuenta").text("No se encuentran cuentas");
    			}
    			else {
    				var tbl = new awesomeTableJs({
						data:json,
						tableWrapper:".tablaCuenta",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
	</script>