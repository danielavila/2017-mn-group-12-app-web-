<style>
 table th a {
 	text-transform: capitalize;
 }
 </style>

   <div class="starter-template">
    	<h2> Empresas </h2>
    	<div class="tablaEmpresa"> </div>
		<div class="paginationContainer "></div>
    </div>	
 	<script src="js/awesomeTable.js" type="text/javascript"></script>
 	<script>
 		$( document ).ready(function() {
 			$.getJSON('/getempresas',function(json){
    			if ( json.length == 0 ) {
        			console.log("NO DATA!");
        			$(".tablaEmpresa").text("No se encuentran empresas");
    			}
    			else {
    				var tbl = new awesomeTableJs({
			
						data:json,					
						tableWrapper:".tablaEmpresa",
						paginationWrapper:".paginationContainer",
						buildPageSize: false,
						buildSearch: false,
					});
					tbl.createTable();	
    			}
			});
 			
		});
	
	</script>